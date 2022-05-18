package eus.klimu.security.filter;

import eus.klimu.security.TokenManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        if (
                !request.getServletPath().startsWith("/css") &&
                !request.getServletPath().startsWith("/js") &&
                !request.getServletPath().startsWith("/media")
        ) {
            TokenManagement tokenManagement = new TokenManagement();

            // Check the access token.
            String accessToken = request.getHeader(TokenManagement.ACCESS_TOKEN_HEADER);

            if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
                try {
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception accessException) {
                    String refreshToken = request.getHeader(TokenManagement.REFRESH_TOKEN_HEADER);
                    try {
                        UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        // Generate a new access and refresh token for the user.
                        User user = tokenManagement.getUserFromToken(refreshToken);
                        tokenManagement.setTokenOnResponse(
                                tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.ACCESS_TIME),
                                tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.REFRESH_TIME),
                                response
                        );
                    } catch (Exception refreshException) {
                        response.setHeader("error", refreshException.getMessage());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
        }
        if (request.getParameter(TokenManagement.ACCESS_TOKEN_HEADER) != null &&
                request.getParameter(TokenManagement.REFRESH_TOKEN_HEADER) != null) {
            response.setHeader(TokenManagement.ACCESS_TOKEN_HEADER, request.getParameter(TokenManagement.ACCESS_TOKEN_HEADER));
            response.setHeader(TokenManagement.REFRESH_TOKEN_HEADER, request.getParameter(TokenManagement.REFRESH_TOKEN_HEADER));
        }
        filterChain.doFilter(request, response);
    }
}
