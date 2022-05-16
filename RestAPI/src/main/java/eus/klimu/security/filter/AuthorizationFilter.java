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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN_HEADER = "accessToken";
    private static final String REFRESH_TOKEN_HEADER = "refreshToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (
                !request.getServletPath().startsWith("/css") &&
                !request.getServletPath().startsWith("/js") &&
                !request.getServletPath().startsWith("/media") &&
                !request.getServletPath().startsWith("/login") &&
                !request.getServletPath().equals("/user/create")&&
                !request.getServletPath().equals("/login/sign-up")
        ) {
            HttpSession session = request.getSession();
            TokenManagement tokenManagement = new TokenManagement();

            // Check the access token.
            String accessToken = (String) session.getAttribute(ACCESS_TOKEN_HEADER);
            if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
                try {
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception accessException) {
                    String refreshToken = (String) session.getAttribute(REFRESH_TOKEN_HEADER);
                    try {
                        UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        // Generate a new access and refresh token for the user.
                        User user = tokenManagement.getUserFromToken(refreshToken);
                        tokenManagement.setTokenOnSession(
                                tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.ACCESS_TIME),
                                tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.REFRESH_TIME),
                                request.getSession()
                        );
                    } catch (Exception refreshException) {
                        response.setHeader("error", refreshException.getMessage());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
        }
        if (request.getParameter(ACCESS_TOKEN_HEADER) != null && request.getParameter(REFRESH_TOKEN_HEADER) != null) {
            response.setHeader(ACCESS_TOKEN_HEADER, request.getParameter(ACCESS_TOKEN_HEADER));
            response.setHeader(REFRESH_TOKEN_HEADER, request.getParameter(REFRESH_TOKEN_HEADER));
        }
        filterChain.doFilter(request, response);
    }
}
