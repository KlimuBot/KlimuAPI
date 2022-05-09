package eus.klimu.alerts.api;

import eus.klimu.alerts.domain.model.Alert;
import eus.klimu.alerts.domain.service.definition.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/{alertID}")
    public ResponseEntity<Alert> getAlert(@PathVariable Long alertID) {
        return ResponseEntity.ok().body(alertService.getAlertByID(alertID));
    }

    @PostMapping("/save")
    public ResponseEntity<Alert> saveAlert(@RequestBody Alert alert) {
        Alert newAlert = alertService.saveAlert(alert);
        if (alert != null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/alert/save").toUriString())
            ).body(newAlert);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
