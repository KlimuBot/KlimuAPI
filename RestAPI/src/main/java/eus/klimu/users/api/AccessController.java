package eus.klimu.users.api;

import com.google.gson.Gson;
import eus.klimu.security.TokenManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final Gson gson;

    @GetMapping(value = "/auth")
    public ResponseEntity<String> getUsernamePasswordToken(@RequestParam String token) {
        TokenManagement tokenManagement = new TokenManagement();
        JSONObject tokenJSON = new JSONObject(token);

        log.info("Authenticating token {}" + token);
        if (tokenJSON.has("token")) {
            return ResponseEntity.ok().body(gson.toJson(
                    tokenManagement.getUsernamePasswordToken(tokenJSON.getString("token"))));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<Map<String, String>> refreshTokens(
            HttpServletRequest request, @RequestBody String tokenJSON
    ) {
        log.info("Trying to refresh tokens");
        TokenManagement tokenManagement = new TokenManagement();
        Map<String, String> tokens = tokenManagement.getTokensFromJSON(tokenJSON);

        String accessToken = tokens.get(TokenManagement.ACCESS_TOKEN_HEADER);
        if (accessToken != null && accessToken.startsWith(TokenManagement.TOKEN_SIGNATURE_NAME)) {
            try {
                // Try accessing with the access token.
                UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Refreshing with the access token");
            } catch (Exception accessException) {
                log.warn("The access token has expired, trying to refresh with the refresh token");
                String refreshToken = tokens.get(TokenManagement.REFRESH_TOKEN_HEADER);
                try {
                    // Try accessing with the refresh token.
                    UsernamePasswordAuthenticationToken authToken = tokenManagement.getUsernamePasswordToken(refreshToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Refresh the tokens.
                    User user = tokenManagement.getUserFromToken(refreshToken);
                    tokens.put(
                            TokenManagement.ACCESS_TOKEN_HEADER,
                            tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.ACCESS_TIME)
                    );
                    tokens.put(
                            TokenManagement.REFRESH_TOKEN_HEADER,
                            tokenManagement.generateToken(user, request.getRequestURL().toString(), TokenManagement.REFRESH_TIME)
                    );
                    log.info("Refreshing with the refresh token");
                    return ResponseEntity.created(
                            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/access/refresh").toUriString())
                    ).body(tokens);
                } catch (Exception refreshException) {
                    tokens.remove(TokenManagement.ACCESS_TOKEN_HEADER);
                    tokens.remove(TokenManagement.REFRESH_TOKEN_HEADER);
                    tokens.put("errorMsg", "The access and refresh tokens have expired");

                    log.error("Both tokens have expired, sending an error");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(tokens);
                }
            }
        } else {
            tokens.remove(TokenManagement.ACCESS_TOKEN_HEADER);
            tokens.remove(TokenManagement.REFRESH_TOKEN_HEADER);
            tokens.put("errorMsg", "The access and refresh tokens have expired");
            log.error("Incorrect token structure");
        }
        return ResponseEntity.badRequest().body(tokens);
    }

    @GetMapping(value = "/denied")
    public ResponseEntity<String> permissionDenied() {
        log.error("Denying request");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have the authority to access this page!");
    }

}
