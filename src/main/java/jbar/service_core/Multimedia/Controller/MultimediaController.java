// src/main/java/jbar/service_core/Multimedia/Controller/MultimediaController.java
package jbar.service_core.Multimedia.Controller;

import jbar.service_core.Multimedia.Model.MultimediaDTO;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Controller.MultimediaService;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;

    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    /**
     * Endpoint para crear un nuevo registro de multimedia.
     */
    @PostMapping
    public ResponseEntity<Message> createMultimedia(@RequestBody MultimediaDTO multimediaDTO) {
        System.out.println(multimediaDTO.getUrl());
        return multimediaService.create(multimediaDTO);
    }

    /**
     * Endpoint para obtener todos los registros de multimedia.
     */
    @GetMapping
    public ResponseEntity<Message> getAllMultimedia() {
        return multimediaService.getAll();
    }
}
