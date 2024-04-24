package br.com.fullstackedu.tutoringMng.service;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.MaterialRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.MaterialResponse;
import br.com.fullstackedu.tutoringMng.database.entity.AgendaEntity;
import br.com.fullstackedu.tutoringMng.database.entity.MaterialEntity;
import br.com.fullstackedu.tutoringMng.database.repository.AgendaRepository;
import br.com.fullstackedu.tutoringMng.database.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final AgendaRepository  agendaRepository;

    public MaterialResponse getAll() {
        try {
            List<MaterialEntity> materiaisList = materialRepository.findAll();
            if (materiaisList.isEmpty()) {
                return new MaterialResponse(false, LocalDateTime.now() , "Nenhum material encontrado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new MaterialResponse(true, LocalDateTime.now(), "Registros encontrados: "+ materiaisList.size(), materiaisList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Materiais cadastrados. Erro: {}", e.getMessage());
            return new MaterialResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public MaterialResponse getById(Long targetId) {
        try {
            MaterialEntity material = materialRepository.findById(targetId).orElse(null);
            if (Objects.isNull(material)) {
                return new MaterialResponse(true, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new MaterialResponse(false, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(material),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Material cadastrado. Erro: {}", e.getMessage());
            return new MaterialResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public MaterialResponse insertMaterial(MaterialRequest materialRequest) {
        try {
            AgendaEntity foundAgendaEntity = agendaRepository.findById(materialRequest.id_agenda()).orElse(null);
            if (Objects.isNull(foundAgendaEntity))
                return new MaterialResponse(false, LocalDateTime.now() , "Agenda id [" + materialRequest.id_agenda() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            MaterialEntity newMaterialEntity = new MaterialEntity();
            MaterialEntity savedMaterialEntity = materialRepository.save(
                    _setMaterialEntity(newMaterialEntity, materialRequest, foundAgendaEntity)
            );
            log.info("Material inserido com sucesso: {}", savedMaterialEntity);
            return new MaterialResponse(
                    false, LocalDateTime.now() ,
                    "Registro salvo com sucesso" ,
                    Collections.singletonList(savedMaterialEntity),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            log.info("Falha ao adicionar Material. Erro: {}", e.getMessage());
            return new MaterialResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    private MaterialEntity _setMaterialEntity(MaterialEntity newMaterialEntity, MaterialRequest materialRequest, AgendaEntity agendaEntity) {
        newMaterialEntity.setAgenda(agendaEntity);
        if (Objects.nonNull(materialRequest.caminho_arquivos())) newMaterialEntity.setCaminhoArquivos(materialRequest.caminho_arquivos());
        if (Objects.nonNull(materialRequest.descricao())) newMaterialEntity.setDescricao(materialRequest.descricao());
        return newMaterialEntity;
    }

    public MaterialResponse updateMaterial(Long targetId, MaterialRequest materialRequest) {
        try {
            MaterialEntity foundMaterialEntity = materialRepository.findById(targetId).orElse(null);
            log.info("Material encontrado com sucesso: {}", foundMaterialEntity);
            if (Objects.isNull(foundMaterialEntity))
                return new MaterialResponse(false, LocalDateTime.now() , "Material id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            AgendaEntity foundAgendaEntity = agendaRepository.findById(materialRequest.id_agenda()).orElse(null);
            if (Objects.isNull(foundAgendaEntity))
                return new MaterialResponse(false, LocalDateTime.now() , "Agenda id [" + materialRequest.id_agenda() + "] não encontrada" , null, HttpStatus.NOT_FOUND);

            MaterialEntity savedMaterialEntity = materialRepository.save(
                    _setMaterialEntity(foundMaterialEntity, materialRequest, foundAgendaEntity)
            );

            log.info("Material atualizado com sucesso: {}", savedMaterialEntity);
            return new MaterialResponse(true, LocalDateTime.now(),"Material atualizada com sucesso.", Collections.singletonList(foundMaterialEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Material. Erro: {}", e.getMessage());
            return new MaterialResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public MaterialResponse deleteMaterial(Long targetId) {
        try {
            MaterialEntity foundMaterialEntity = materialRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundMaterialEntity))
                return new MaterialResponse(false, LocalDateTime.now(), "Material id [" + targetId + "] não encontrado", null, HttpStatus.NOT_FOUND);
            materialRepository.delete(foundMaterialEntity);
            log.info("Material removida com sucesso: {}", foundMaterialEntity);
            return new MaterialResponse(true, LocalDateTime.now(),"Material removida com sucesso.", Collections.singletonList(foundMaterialEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Material. Erro: {}", e.getMessage());
            return new MaterialResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }
}
