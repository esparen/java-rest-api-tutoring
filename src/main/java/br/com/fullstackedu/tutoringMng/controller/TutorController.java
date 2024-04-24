package br.com.fullstackedu.tutoringMng.controller;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.TutorRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.TutorResponse;
import br.com.fullstackedu.tutoringMng.service.TutorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/tutores")
@Validated
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<TutorResponse> getAllTutores() {
        log.info("GET /tutores");
        TutorResponse response = tutorService.getAll();
        if (response.success()){
            log.info("GET /tutores -> 200 ");
        } else {
            log.error("POST /tutores -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    //GET by id
    @GetMapping("/{targetId}")
    public ResponseEntity<TutorResponse> getTutorById(@PathVariable long targetId) {
        log.info("GET /tutores/{targetId}");
        TutorResponse response = tutorService.getById(targetId);
        if (response.success()){
            log.info("GET /tutores/{targetId} -> 200 ");
        } else {
            log.error("POST /tutores/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
    //POST
    @PostMapping()
    public ResponseEntity<TutorResponse> newTutor(
            @Valid @RequestBody TutorRequest tutorRequest
    ) throws Exception {
        log.info("POST /tutores ");
        TutorResponse response = tutorService.insertTutor(tutorRequest);
        if (response.success()){
            log.info("POST /tutores -> Registro inserido com sucesso.");
        } else {
            log.error("POST /tutores -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<TutorResponse> updateTutor(
            @PathVariable Long targetId,
            @Valid @RequestBody TutorRequest tutorRequest)
    {
        log.info("PUT /tutores");
        TutorResponse response = tutorService.updateTutor(targetId, tutorRequest);
        if (response.success()) {
            log.info("PUT /tutores -> OK ");
        } else {
            log.error("PUT /tutores -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<TutorResponse> deleteTutor(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /tutores");
        TutorResponse response = tutorService.deleteTutor(targetId);
        if (response.success()) {
            log.info("DELETE /tutores -> OK ");
        } else {
            log.error("DELETE /tutores -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
