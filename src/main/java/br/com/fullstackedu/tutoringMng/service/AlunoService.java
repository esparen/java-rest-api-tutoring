package br.com.fullstackedu.tutoringMng.service;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.AlunoRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.AlunoResponse;
import br.com.fullstackedu.tutoringMng.database.entity.AlunoEntity;
import br.com.fullstackedu.tutoringMng.database.repository.AlunoRepository;
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
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoResponse getAll() {
        try {
            List<AlunoEntity> alunosList = alunoRepository.findAll();
            if (alunosList.isEmpty()) {
                return new AlunoResponse(false, LocalDateTime.now() , "Nenhum aluno encontrado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new AlunoResponse(true, LocalDateTime.now(), "Registros encontrados: "+ alunosList.size(), alunosList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Alunos cadastrados. Erro: {}", e.getMessage());
            return new AlunoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //getById
    public AlunoResponse getById(Long targetId) {
        try {
            AlunoEntity aluno = alunoRepository.findById(targetId).orElse(null);
            if (Objects.isNull(aluno)) {
                return new AlunoResponse(true, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new AlunoResponse(false, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(aluno),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Aluno cadastrado. Erro: {}", e.getMessage());
            return new AlunoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public AlunoResponse insertAluno(AlunoRequest alunoRequest) {
        try {
            AlunoEntity newAlunoEntity = new AlunoEntity();
            newAlunoEntity.setNome(alunoRequest.nome());
            AlunoEntity savedAlunoEntity = alunoRepository.save(newAlunoEntity);
            log.info("Aluno inserido com sucesso: {}", newAlunoEntity);
            return new AlunoResponse(true, LocalDateTime.now(),"Aluno cadastrado com sucesso.", Collections.singletonList(newAlunoEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Falha ao adicionar Aluno. Erro: {}", e.getMessage());
            return new AlunoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public AlunoResponse updateAluno(Long targetId, AlunoRequest alunoRequest) {
        try {
            log.info("Bla bla bla bla bla bla");
            AlunoEntity foundAlunoEntity = alunoRepository.findById(targetId).orElse(null);
            log.info("Aluno encontrado com sucesso: {}", foundAlunoEntity);
            if (Objects.isNull(foundAlunoEntity))
                return new AlunoResponse(false, LocalDateTime.now() , "Aluno id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            foundAlunoEntity.setNome(alunoRequest.nome());
            AlunoEntity savedDocenteEntity = alunoRepository.save(foundAlunoEntity);
            log.info("Aluno atualizado com sucesso: {}", foundAlunoEntity);
            return new AlunoResponse(true, LocalDateTime.now(),"Aluno cadastrado com sucesso.", Collections.singletonList(foundAlunoEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Aluno. Erro: {}", e.getMessage());
            return new AlunoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public AlunoResponse deleteAluno(Long targetId) {
        try {
            AlunoEntity foundAlunoEntity = alunoRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundAlunoEntity))
                return new AlunoResponse(false, LocalDateTime.now(), "Aluno id [" + targetId + "] não encontrado", null, HttpStatus.NOT_FOUND);
            alunoRepository.delete(foundAlunoEntity);
            log.info("Aluno removido com sucess: {}", foundAlunoEntity);
            return new AlunoResponse(true, LocalDateTime.now(),"Aluno cadastrado com sucesso.", Collections.singletonList(foundAlunoEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Aluno. Erro: {}", e.getMessage());
            return new AlunoResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

}
