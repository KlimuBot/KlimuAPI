package eus.klimu.users.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import eus.klimu.security.TokenManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage the user access to the server, providing _Json Web Tokens_ to those users that have already been
 * registered on the database. It also provides different methods to manage the access to the services.
 */
@Slf4j
@Controller
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final Gson gson;

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Authenticate User Token</h2>
     *
     * <a href='https://klimu.eus/RestAPI/access/auth/{token}'>https://klimu.eus/RestAPI/access/auth/{token}</a>
     *
     * <p>Get a UsernamePasswordToken from a JWT token. Transforms the UsernamePasswordToken into a JSON string.</p>
     *
     * @param token The JWT token that is going to be used for getting the UsernamePasswordToken.
     * @return A 200 ok with the UsernamePasswordToken if everything went well. If not, returns a 400 bad request.
     */
    @GetMapping(value = "/auth/{token}")
    public ResponseEntity<String> getUsernamePasswordToken(@PathVariable String token) {
        TokenManagement tokenManagement = new TokenManagement();
        log.info("Authenticating token");

        if (token != null) {
            try {
                return ResponseEntity.ok().body(gson.toJson(tokenManagement.getUsernamePasswordToken(token)));
            } catch (JWTVerificationException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Refresh User Token</h2>
     *
     * <a href='https://klimu.eus/RestAPI/access/refresh'>https://klimu.eus/RestAPI/access/refresh</a>
     *
     * <p>Get a new pack of tokens (accessToken and refreshToken) from the server as a JSON. Requires a request body
     * with an access and a refresh token. One of the tokens need to not be expired for it to work.</p>
     *
     * @param request The HttpServletRequest made to the server.
     * @param session The user session.
     * @return A 200 ok with a json that contains the access and the refresh token. If not, returns a 400 bad request.
     */
    @GetMapping(value = "/refresh")
    public ResponseEntity<Map<String, String>> refreshTokens(HttpServletRequest request, HttpSession session) {
        log.info("Trying to refresh tokens");
        TokenManagement tokenManagement = new TokenManagement();
        Map<String, String> tokens = new HashMap<>();

        tokens.put(TokenManagement.ACCESS_TOKEN, request.getHeader(TokenManagement.ACCESS_TOKEN));
        tokens.put(TokenManagement.REFRESH_TOKEN, request.getHeader(TokenManagement.REFRESH_TOKEN));

        String accessToken = tokens.get(TokenManagement.ACCESS_TOKEN);
        if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
            try {
                // Try accessing with the access token.
                UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Refreshing with the access token");
            } catch (Exception accessException) {
                log.warn("The access token has expired, trying to refresh with the refresh token");
                String refreshToken = tokens.get(TokenManagement.REFRESH_TOKEN);
                try {
                    // Try accessing with the refresh token.
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Refresh the tokens.
                    User user = tokenManagement.getUserFromToken(refreshToken);
                    tokens.put(
                            TokenManagement.ACCESS_TOKEN,
                            tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.ACCESS_TIME)
                    );
                    tokens.put(
                            TokenManagement.REFRESH_TOKEN,
                            tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.REFRESH_TIME)
                    );
                    log.info("Refreshing with the refresh token");
                    return ResponseEntity.created(
                            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/access/refresh").toUriString())
                    ).body(tokens);
                } catch (Exception refreshException) {
                    tokens.remove(TokenManagement.ACCESS_TOKEN);
                    tokens.remove(TokenManagement.REFRESH_TOKEN);
                    tokens.put("errorMsg", "The access and refresh tokens have expired");

                    log.error("Both tokens have expired, sending an error");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(tokens);
                }
            }
        } else {
            tokens.remove(TokenManagement.ACCESS_TOKEN);
            tokens.remove(TokenManagement.REFRESH_TOKEN);
            tokens.put("errorMsg", "The access and refresh tokens have expired");
            log.error("Incorrect token structure");
        }
        return ResponseEntity.badRequest().body(tokens);
    }

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Deny Access</h2>
     *
     * <a href='https://klimu.eus/RestAPI/access/denied'>https://klimu.eus/RestAPI/access/denied</a>
     *
     * <p>Obtains an error message as a JSON with a DENIED type of response.</p>
     *
     * @return A 403 error with a message explaining that the user has no authorities.
     */
    @GetMapping(value = "/denied")
    public ResponseEntity<String> permissionDenied() {
        log.error("Denying request");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have the authority to access this page!");
    }

}
