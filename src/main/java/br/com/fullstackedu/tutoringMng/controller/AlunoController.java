package br.com.fullstackedu.tutoringMng.controller;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.AlunoRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.AlunoResponse;
import br.com.fullstackedu.tutoringMng.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoController {

    private final AlunoService alunoService;

    //GET all
    @GetMapping
    public ResponseEntity<AlunoResponse> getAllAlunos() {
        log.info("GET /alunos");
        AlunoResponse response = alunoService.getAll();
        if (response.success()){
            log.info("GET /alunos -> 200 ");
        } else {
            log.error("POST /alunos -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    //GET by id
    @GetMapping("/{targetId}")
    public ResponseEntity<AlunoResponse> getAlunoById(@PathVariable long targetId) {
        log.info("GET /alunos/{targetId}");
        AlunoResponse response = alunoService.getById(targetId);
        if (response.success()){
            log.info("GET /alunos/{targetId} -> 200 ");
        } else {
            log.error("POST /alunos/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
    //POST
    @PostMapping()
    public ResponseEntity<AlunoResponse> newAluno(
            @Valid @RequestBody AlunoRequest alunoRequest
    ) throws Exception {
        log.info("POST /alunos ");
        AlunoResponse response = alunoService.insertAluno(alunoRequest);
        if (response.success()){
            log.info("POST /alunos -> Registro inserido com sucesso.");
        } else {
            log.error("POST /alunos -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<AlunoResponse> updateAluno(
            @PathVariable Long targetId,
            @Valid @RequestBody AlunoRequest alunoRequest)
    {
        log.info("PUT /alunos");
        AlunoResponse response = alunoService.updateAluno(targetId, alunoRequest);
        if (response.success()) {
            log.info("PUT /alunos -> OK ");
        } else {
            log.error("PUT /alunos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<AlunoResponse> deleteAluno(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /alunos");
        AlunoResponse response = alunoService.deleteAluno(targetId);
        if (response.success()) {
            log.info("DELETE /alunos -> OK ");
        } else {
            log.error("DELETE /alunos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
