package eus.klimu.canals.api;

import eus.klimu.canals.domain.model.Canal;
import eus.klimu.canals.domain.service.definition.CanalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/canal")
@RequiredArgsConstructor
public class CanalController {

    private final CanalService canalService;

    @GetMapping("/{canalID}")
    public ResponseEntity<Canal> getCanal(@PathVariable Long canalID) {
        return ResponseEntity.ok().body(canalService.getCanalByID(canalID));
    }

    @PostMapping("/save")
    public ResponseEntity<Canal> saveCanal(@RequestBody Canal canal) {
        Canal newCanal = canalService.saveCanal(canal);
        if (canal != null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/canal/save").toUriString())
            ).body(newCanal);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
