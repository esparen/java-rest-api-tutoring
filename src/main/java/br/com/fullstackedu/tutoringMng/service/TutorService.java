package br.com.fullstackedu.tutoringMng.service;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.TutorRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.TutorResponse;
import br.com.fullstackedu.tutoringMng.database.entity.TutorEntity;
import br.com.fullstackedu.tutoringMng.database.repository.TutorRepository;
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
public class TutorService {
    private final TutorRepository tutorRepository;

    public TutorResponse getAll() {
        try {
            List<TutorEntity> tutoresList = tutorRepository.findAll();
            if (tutoresList.isEmpty()) {
                return new TutorResponse(false, LocalDateTime.now() , "Nenhum tutor encontrado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new TutorResponse(true, LocalDateTime.now(), "Registros encontrados: "+ tutoresList.size(), tutoresList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Tutores cadastrados. Erro: {}", e.getMessage());
            return new TutorResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //getById
    public TutorResponse getById(Long targetId) {
        try {
            TutorEntity tutor = tutorRepository.findById(targetId).orElse(null);
            if (Objects.isNull(tutor)) {
                return new TutorResponse(true, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new TutorResponse(false, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(tutor),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Tutor cadastrado. Erro: {}", e.getMessage());
            return new TutorResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public TutorResponse insertTutor(TutorRequest tutorRequest) {
        try {
            TutorEntity newTutorEntity = new TutorEntity();
            newTutorEntity.setNome(tutorRequest.nome());
            if (Objects.nonNull(tutorRequest.especialidade())) newTutorEntity.setEspecialidade(tutorRequest.especialidade());
            TutorEntity savedTutorEntity = tutorRepository.save(newTutorEntity);
            log.info("Tutor inserido com sucesso: {}", newTutorEntity);
            return new TutorResponse(true, LocalDateTime.now(),"Tutor cadastrado com sucesso.", Collections.singletonList(newTutorEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Falha ao adicionar Tutor. Erro: {}", e.getMessage());
            return new TutorResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public TutorResponse updateTutor(Long targetId, TutorRequest tutorRequest) {
        try {
            TutorEntity foundTutorEntity = tutorRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundTutorEntity))
                return new TutorResponse(false, LocalDateTime.now() , "Tutor id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            foundTutorEntity.setNome(tutorRequest.nome());
            if (Objects.nonNull(tutorRequest.especialidade())) foundTutorEntity.setEspecialidade(tutorRequest.especialidade());
            TutorEntity savedDocenteEntity = tutorRepository.save(foundTutorEntity);
            log.info("Tutor atualizado com sucesso: {}", foundTutorEntity);
            return new TutorResponse(true, LocalDateTime.now(),"Tutor cadastrado com sucesso.", Collections.singletonList(foundTutorEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Tutor. Erro: {}", e.getMessage());
            return new TutorResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public TutorResponse deleteTutor(Long targetId) {
        try {
            TutorEntity foundTutorEntity = tutorRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundTutorEntity))
                return new TutorResponse(false, LocalDateTime.now(), "Tutor id [" + targetId + "] não encontrado", null, HttpStatus.NOT_FOUND);
            tutorRepository.delete(foundTutorEntity);
            log.info("Tutor removido com sucess: {}", foundTutorEntity);
            return new TutorResponse(true, LocalDateTime.now(),"Tutor cadastrado com sucesso.", Collections.singletonList(foundTutorEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Tutor. Erro: {}", e.getMessage());
            return new TutorResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }
}
