package eus.klimu.security.filter;

import eus.klimu.security.TokenManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manage the attempts to authenticate the user, the token generation when successfully authenticated and
 * the error generation when the authentication is unsuccessful.
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Manager of the authentication of the users.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Create a new instance of an AuthenticationFilter.
     * @param authenticationManager The manager of the authentication of the user.
     */
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Try to authenticate a user with username and password.
     * @param request The HttpServletRequest to the server.
     * @param response The HttpServletResponse from the server.
     * @return The authentication of the user.
     * @throws AuthenticationException Exception if the user could not be authenticated.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Get the user parameters.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if parameters are not null.
        if (username != null && password != null) {
            log.info("Trying to log user {}", username);
        } else {
            log.error("Username or password were null, authentication won't be possible");
        }

        // Create an authentication token for the user.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password
        );
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * The user was successfully authenticated, so their tokens must be generated.
     * @param request The HttpServletRequest to the server.
     * @param response The HttpServletResponse from the server.
     * @param chain The filtering chain of the requests and responses.
     * @param authentication The authentication service of the application.
     * @throws IOException Exception if an I/O operation was interrupted or failed.
     * @throws ServletException Ignored.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        TokenManagement tokenManagement = new TokenManagement();

        // Generate access and refresh tokens.
        String accessToken = tokenManagement.generateToken(
                user, request.getRequestURL().toString(), TokenManagement.ACCESS_TIME
        );
        String refreshToken = tokenManagement.generateToken(
                user, request.getRequestURL().toString(), TokenManagement.REFRESH_TIME
        );
        // Save the tokens on the body.
        tokenManagement.setTokenOnResponse(accessToken, refreshToken, response);
    }

    /**
     * The user was not authenticated so an error must be sent.
     * @param request The HttpServletRequest to the server.
     * @param response The HttpServletResponse from the server.
     * @param failed Exception that indicates why the authentication failed.
     * @throws IOException Exception if an I/O operation was interrupted or failed.
     * @throws ServletException Ignored.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        TokenManagement tokenManagement = new TokenManagement();
        tokenManagement.setErrorOnResponse("You don't have permission to access this path", response);
    }
}
