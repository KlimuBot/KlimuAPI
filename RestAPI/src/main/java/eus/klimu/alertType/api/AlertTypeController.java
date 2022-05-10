package eus.klimu.alertType.api;

import eus.klimu.alertType.domain.model.AlertType;
import eus.klimu.alertType.domain.service.definition.AlertTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/alertType")
@RequiredArgsConstructor
public class AlertTypeController {

    private final AlertTypeService alertService;

    @GetMapping("/{alertTypeID}")
    public ResponseEntity<AlertType> getAlertType(@PathVariable Long alertTypeID) {
        return ResponseEntity.ok().body(alertService.getAlertTypeByID(alertTypeID));
    }

    @PostMapping("/save")
    public ResponseEntity<AlertType> saveAlertType(@RequestBody AlertType alertType) {
        AlertType newAlertType = alertService.saveAlertType(alertType);
        if (alertType!= null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/alert/save").toUriString())
            ).body(newAlertType);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
