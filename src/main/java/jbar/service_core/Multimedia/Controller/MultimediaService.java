package jbar.service_core.Multimedia.Controller;

import jbar.service_core.Multimedia.Model.MultimediaDTO;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaRepository;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MultimediaService {

    private final MultimediaRepository multimediaRepository;

    public MultimediaService(MultimediaRepository multimediaRepository) {
        this.multimediaRepository = multimediaRepository;
    }
    /**
     * Crea un nuevo registro de multimedia utilizando el DTO.
     *
     * @param multimediaDTO El DTO con la URL del multimedia.
     * @return ResponseEntity con el mensaje y el objeto creado.
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(MultimediaDTO multimediaDTO) {
        System.out.println(multimediaDTO.getUrl());
        // Se crea el objeto multimedia
        Multimedia multimedia = new Multimedia();
        multimedia.setUrl(multimediaDTO.getUrl());
        System.out.println(multimedia.getUrl());
        // Se guarda en el repositorio
        multimediaRepository.save(multimedia);
        // Se devuelve la respuesta con el objeto multimedia creado
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(multimedia, "Multimedia created", TypesResponse.SUCCESS));
    }

    /**
     * Obtiene todos los registros de multimedia.
     *
     * @return ResponseEntity con la lista de multimedia.
     */
    public ResponseEntity<Message> getAll() {
        return ResponseEntity.ok(
                new Message(multimediaRepository.findAll(), "List of all multimedia", TypesResponse.SUCCESS)
        );
    }
}
