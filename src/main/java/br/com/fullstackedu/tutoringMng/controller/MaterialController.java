package br.com.fullstackedu.tutoringMng.controller;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.MaterialRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.MaterialResponse;
import br.com.fullstackedu.tutoringMng.service.MaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/materiais")
@Validated
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @GetMapping
    public ResponseEntity<MaterialResponse> getAllMateriais() {
        log.info("GET /materiais");
        MaterialResponse response = materialService.getAll();
        if (response.success()){
            log.info("GET /materiais -> 200 ");
        } else {
            log.error("POST /materiais -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }


    @GetMapping("/{targetId}")
    public ResponseEntity<MaterialResponse> getMaterialById(@PathVariable long targetId) {
        log.info("GET /materiais/{targetId}");
        MaterialResponse response = materialService.getById(targetId);
        if (response.success()){
            log.info("GET /materiais/{targetId} -> 200 ");
        } else {
            log.error("POST /materiais/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PostMapping()
    public ResponseEntity<MaterialResponse> newMaterial(
            @Valid @RequestBody MaterialRequest materialRequest
    ) throws Exception {
        log.info("POST /materiais ");
        MaterialResponse response = materialService.insertMaterial(materialRequest);
        if (response.success()){
            log.info("POST /materiais -> Registro inserido com sucesso.");
        } else {
            log.error("POST /materiais -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<MaterialResponse> updateMaterial(
            @PathVariable Long targetId,
            @Valid @RequestBody MaterialRequest materialRequest)
    {
        log.info("PUT /materiais");
        MaterialResponse response = materialService.updateMaterial(targetId, materialRequest);
        if (response.success()) {
            log.info("PUT /materiais -> OK ");
        } else {
            log.error("PUT /materiais -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<MaterialResponse> deleteMaterial(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /materiais");
        MaterialResponse response = materialService.deleteMaterial(targetId);
        if (response.success()) {
            log.info("DELETE /materiais -> OK ");
        } else {
            log.error("DELETE /materiais -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
