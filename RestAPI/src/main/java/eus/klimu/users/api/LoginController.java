package eus.klimu.users.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/login/grant-access")
    public ResponseEntity<Object> getTokens(@RequestParam String username, @RequestParam String password) {
        String loginUrl = "http://localhost:8080/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        return restTemplate.postForEntity(loginUrl, request, Object.class);
    }

    @GetMapping(value = "/login/denied")
    public ResponseEntity<String> permissionDenied() {
        log.error("Denying request");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have the authority to access this page!");
    }
}
