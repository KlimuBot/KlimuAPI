package eus.klimu.ubications.api;

import eus.klimu.ubications.domain.service.definition.UbicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ubication")
@RequiredArgsConstructor
public class UbicationController {

    private final UbicationService ubicationService;

   /*
    @GetMapping("/{ubicationID}")
    public ResponseEntity<Ubication> getUbication(@PathVariable Long ubicationID) {
        return ResponseEntity.ok().body(ubicationService.getUbicationByID(ubicationID));
    }

    @PostMapping("/save")
    public ResponseEntity<Ubication> saveUbication(@RequestBody Ubication ubication) {
        Ubication newUbication = ubicationService.saveUbication(ubication);
        if (ubication != null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/ubication/save").toUriString())
            ).body(newUbication);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    */
}
