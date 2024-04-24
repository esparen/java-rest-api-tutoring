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
@RequestMapping("/agendamentos")
@Validated
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaService agendaService;


    @GetMapping
    public ResponseEntity<AgendaResponse> getAllAgendas() {
        log.info("GET /agendamentos");
        AgendaResponse response = agendaService.getAll();
        if (response.success()){
            log.info("GET /agendamentos -> 200 ");
        } else {
            log.error("POST /agendamentos -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }


    @GetMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> getAgendaById(@PathVariable long targetId) {
        log.info("GET /agendamentos/{targetId}");
        AgendaResponse response = agendaService.getById(targetId);
        if (response.success()){
            log.info("GET /agendamentos/{targetId} -> 200 ");
        } else {
            log.error("POST /agendamentos/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @GetMapping("/aluno-id/{targetId}")
    public ResponseEntity<AgendaResponse> getAgendaByAlunoId(@PathVariable long targetId) {
        log.info("GET /agendamentos/aluno-id/{targetId}");
        AgendaResponse response = agendaService.getByAlunoId(targetId);
        if (response.success()){
            log.info("GET /agendamentos/aluno-id/{targetId} -> 200 ");
        } else {
            log.error("POST /agendamentos/aluno-id/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @GetMapping("/tutor-id/{targetId}")
    public ResponseEntity<AgendaResponse> getAgendaByTutorId(@PathVariable long targetId) {
        log.info("GET /agendamentos/tutor-id/{targetId}");
        AgendaResponse response = agendaService.getByTutorId(targetId);
        if (response.success()){
            log.info("GET /agendamentos/tutor-id/{targetId} -> 200 ");
        } else {
            log.error("POST /agendamentos/tutor-id/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }


    @PostMapping()
    public ResponseEntity<AgendaResponse> newAgenda(
            @Valid @RequestBody AgendaRequest agendaRequest
    ) throws Exception {
        log.info("POST /agendamentos ");
        AgendaResponse response = agendaService.insertAgenda(agendaRequest);
        if (response.success()){
            log.info("POST /agendamentos -> Registro inserido com sucesso.");
        } else {
            log.error("POST /agendamentos -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> updateAgenda(
            @PathVariable Long targetId,
            @Valid @RequestBody AgendaRequest agendaRequest)
    {
        log.info("PUT /agendamentos");
        AgendaResponse response = agendaService.updateAgenda(targetId, agendaRequest);
        if (response.success()) {
            log.info("PUT /agendamentos -> OK ");
        } else {
            log.error("PUT /agendamentos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<AgendaResponse> deleteAgenda(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /agendamentos");
        AgendaResponse response = agendaService.deleteAgenda(targetId);
        if (response.success()) {
            log.info("DELETE /agendamentos -> OK ");
        } else {
            log.error("DELETE /agendamentos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
