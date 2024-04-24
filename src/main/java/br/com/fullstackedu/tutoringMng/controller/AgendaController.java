package br.com.fullstackedu.tutoringMng.controller;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.AgendaRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.AgendaResponse;
import br.com.fullstackedu.tutoringMng.service.AgendaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/agendas")
@Validated
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaService agendaService;


    @GetMapping
    public ResponseEntity<AgendaResponse> getAllAgendas() {
        log.info("GET /agendas");
        AgendaResponse response = agendaService.getAll();
        if (response.success()){
            log.info("GET /agendas -> 200 ");
        } else {
            log.error("POST /agendas -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }


    @GetMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> getAgendaById(@PathVariable long targetId) {
        log.info("GET /agendas/{targetId}");
        AgendaResponse response = agendaService.getById(targetId);
        if (response.success()){
            log.info("GET /agendas/{targetId} -> 200 ");
        } else {
            log.error("POST /agendas/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PostMapping()
    public ResponseEntity<AgendaResponse> newAgenda(
            @Valid @RequestBody AgendaRequest agendaRequest
    ) throws Exception {
        log.info("POST /agendas ");
        AgendaResponse response = agendaService.insertAgenda(agendaRequest);
        if (response.success()){
            log.info("POST /agendas -> Registro inserido com sucesso.");
        } else {
            log.error("POST /agendas -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> updateAgenda(
            @PathVariable Long targetId,
            @Valid @RequestBody AgendaRequest agendaRequest)
    {
        log.info("PUT /agendas");
        AgendaResponse response = agendaService.updateAgenda(targetId, agendaRequest);
        if (response.success()) {
            log.info("PUT /agendas -> OK ");
        } else {
            log.error("PUT /agendas -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> deleteAgenda(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /agendas");
        AgendaResponse response = agendaService.deleteAgenda(targetId);
        if (response.success()) {
            log.info("DELETE /agendas -> OK ");
        } else {
            log.error("DELETE /agendas -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
