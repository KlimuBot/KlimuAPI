package eus.klimu.users.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    @GetMapping(value = "/denied")
    public ResponseEntity<String> permissionDenied() {
        log.error("Denying request");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have the authority to access this page!");
    }

}
