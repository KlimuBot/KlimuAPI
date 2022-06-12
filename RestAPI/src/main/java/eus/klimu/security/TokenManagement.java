package eus.klimu.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
public class TokenManagement {

    /**
     * Access token expiration time.
     * 1 hour in milliseconds.
     */
    public static final int ACCESS_TIME = 60 * 60 * 1000;
    /**
     * Refresh token expiration time.
     * 6 hours in milliseconds.
     */
    public static final int REFRESH_TIME = 6 * 60 * 60 * 1000;
    /**
     * The signature name of a token.
     */
    public static final String TOKEN_SIGNATURE_NAME = "Bearer ";
    /**
     * Roles.
     */
    private static final String ROLES = "roles";
    /**
     * The access token header name.
     */
    public static final String ACCESS_TOKEN = "accessToken";
    /**
     * The refresh token header name.
     */
    public static final String REFRESH_TOKEN = "refreshToken";
    /**
     * The algorithm of token encryption.
     */
    private final Algorithm algorithm;
    /**
     * The JSON Web Token Verifier.
     */
    private final JWTVerifier verifier;

    /**
     * Create a new instance of a token management.
     */
    public TokenManagement() {
        algorithm = Algorithm.HMAC256("klimu-secret".getBytes(StandardCharsets.UTF_8));
        verifier = JWT.require(algorithm).build();
    }

    /**
     * Generate a token from a user.
     * @param user The user the token is going to be generated from.
     * @param requestURL The URL that requested the token.
     * @param durationTime The duration of the token.
     * @return A new JWT token.
     */
    public String generateToken(User user, String requestURL, int durationTime) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + durationTime))
                .withIssuer(requestURL)
                .withClaim(ROLES, user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    /**
     * Set the access and refresh tokens as response headers.
     * @param accessToken The access token.
     * @param refreshToken The refresh token.
     * @param response The HttpServletResponse from the server.
     * @throws IOException Generated if any operation failed.
     */
    public void setTokenOnResponse(String accessToken, String refreshToken, HttpServletResponse response) throws IOException {
        Map<String, String> responseBody = new HashMap<>();

        responseBody.put(TokenManagement.ACCESS_TOKEN, TokenManagement.TOKEN_SIGNATURE_NAME + accessToken);
        responseBody.put(TokenManagement.REFRESH_TOKEN, TokenManagement.TOKEN_SIGNATURE_NAME + refreshToken);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        log.info("The user has been authenticated, their tokens have been generated");
        response.flushBuffer();
    }

    /**
     * Set an error message oon the response.
     * @param errorMsg The error message.
     * @param response The HttpServletResponse from the server.
     * @throws IOException Generated if any operation failed.
     */
    public void setErrorOnResponse(String errorMsg, HttpServletResponse response) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("errorMsg", errorMsg);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
        response.flushBuffer();
    }

    /**
     * Get the username and password token from a JWT token.
     * @param authToken The JWT token.
     * @return A username and password authentication token with the username and its authorities.
     * @throws JWTVerificationException Generated if the token could not be verified.
     */
    public UsernamePasswordAuthenticationToken getUsernamePasswordToken(String authToken) throws JWTVerificationException {
        String token = authToken.substring(TOKEN_SIGNATURE_NAME.length());
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(ROLES).asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
    }

    /**
     * Get a User from a token.
     * @param authToken The token the user is going to be fetched from.
     * @return A user with a username and its authorities.
     * @throws JWTVerificationException Generated if the token could not be verified.
     */
    public User getUserFromToken(String authToken) throws JWTVerificationException {
        String token = authToken.substring(TOKEN_SIGNATURE_NAME.length());
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        Collection<String> roles = decodedJWT.getClaim(ROLES).asList(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new User(username, "null", authorities);
    }

}
