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

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getServletPath().startsWith("/login")) {
            TokenManagement tokenManagement = new TokenManagement();

            // Check the access token.
            String accessToken = request.getHeader(TokenManagement.ACCESS_TOKEN_HEADER);
            log.info("Trying to authenticate with accessToken {}", accessToken);

            if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
                try {
                    // Try accessing with the access token.
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("The user was authenticated");
                } catch (Exception accessException) {
                    String refreshToken = request.getHeader(TokenManagement.REFRESH_TOKEN_HEADER);
                    log.warn("Couldn't authenticate with access token, trying refresh token");
                    try {
                        // Try accessing with the refresh token.
                        UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        log.info("The user was authenticated with the refresh token");
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
