package eus.klimu.security.filter;

import eus.klimu.security.TokenManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manage the authorization of the user.
 */
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    /**
     * Filter each request to the server, allowing or denying access if needed.
     * @param request The HttpServletRequest to the server.
     * @param response The HttpServletResponse from the server.
     * @param filterChain The filtering chain of the requests and responses.
     * @throws ServletException Exception generated if the access and refresh tokens are not valid or expired.
     * @throws IOException Exception if an I/O operation was interrupted or failed.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (
                !request.getServletPath().startsWith("/login") &&
                !request.getServletPath().startsWith("/role/name/") &&
                !request.getServletPath().equals("/channel/all") &&
                !request.getServletPath().equals("/user/create") &&
                !request.getServletPath().equals("/user-notification/create") &&
                !request.getServletPath().startsWith("/github-webhook")
        ) {
            TokenManagement tokenManagement = new TokenManagement();

            // Check the access token.
            String accessToken = request.getHeader(TokenManagement.ACCESS_TOKEN);

            if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
                try {
                    // Try accessing with the access token.
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception accessException) {
                    String refreshToken = request.getHeader(TokenManagement.REFRESH_TOKEN);
                    try {
                        // Try accessing with the refresh token.
                        UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } catch (Exception refreshException) {
                        log.error("Couldn't authenticate the user");
                        response.setHeader("errorMsg", refreshException.getMessage());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
