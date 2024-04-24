package br.com.fullstackedu.tutoringMng.service;

import br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest.AgendaRequest;
import br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse.AgendaResponse;
import br.com.fullstackedu.tutoringMng.database.entity.AgendaEntity;
import br.com.fullstackedu.tutoringMng.database.entity.AlunoEntity;
import br.com.fullstackedu.tutoringMng.database.entity.TutorEntity;
import br.com.fullstackedu.tutoringMng.database.repository.AgendaRepository;
import br.com.fullstackedu.tutoringMng.database.repository.AlunoRepository;
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
public class AgendaService {
    private final AgendaRepository agendaRepository;
    private final AlunoRepository alunoRepository;
    private final TutorRepository tutorRepository;

    public AgendaResponse getAll() {
        try {
            List<AgendaEntity> agendasList = agendaRepository.findAll();
            if (agendasList.isEmpty()) {
                return new AgendaResponse(false, LocalDateTime.now() , "Nenhum agenda encontrado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new AgendaResponse(true, LocalDateTime.now(), "Registros encontrados: "+ agendasList.size(), agendasList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Agendas cadastrados. Erro: {}", e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public AgendaResponse getById(Long targetId) {
        try {
            AgendaEntity agenda = agendaRepository.findById(targetId).orElse(null);
            if (Objects.isNull(agenda)) {
                return new AgendaResponse(false, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new AgendaResponse(true, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(agenda),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Agenda cadastrado. Erro: {}", e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public AgendaResponse insertAgenda(AgendaRequest agendaRequest) {
        try {
            AlunoEntity foundAlunoEntity = alunoRepository.findById(agendaRequest.id_aluno()).orElse(null);
            if (Objects.isNull(foundAlunoEntity))
                return new AgendaResponse(false, LocalDateTime.now(), "Aluno id [" + agendaRequest.id_aluno() + "] não encontrado", null, HttpStatus.NOT_FOUND);

            TutorEntity foundTutorEntity = tutorRepository.findById(agendaRequest.id_tutor()).orElse(null);
            if (Objects.isNull(foundTutorEntity))
                return new AgendaResponse(false, LocalDateTime.now() , "Tutor id [" + agendaRequest.id_tutor() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            AgendaEntity newAgendaEntity = new AgendaEntity();
            AgendaEntity savedAgendaEntity = agendaRepository.save(
                    _setAgendaEntity(newAgendaEntity, agendaRequest, foundAlunoEntity, foundTutorEntity)
            );
            log.info("Agenda inserida com sucesso: {}", savedAgendaEntity);
            return new AgendaResponse(
                    true, LocalDateTime.now() ,
                    "Registro salvo com sucesso" ,
                    Collections.singletonList(savedAgendaEntity),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            log.info("Falha ao adicionar Agenda. Erro: {}", e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    private AgendaEntity _setAgendaEntity(AgendaEntity newAgendaEntity, AgendaRequest agendaRequest, AlunoEntity alunoEntity, TutorEntity tutorEntity) {
        newAgendaEntity.setAluno(alunoEntity);
        newAgendaEntity.setTutor(tutorEntity);
        if (Objects.nonNull(agendaRequest.data())) newAgendaEntity.setData(agendaRequest.data());
        if (Objects.nonNull(agendaRequest.tema())) newAgendaEntity.setTema(agendaRequest.tema());
        if (Objects.nonNull(agendaRequest.descricao())) newAgendaEntity.setDescricaoBreve(agendaRequest.descricao());
        if (Objects.nonNull(agendaRequest.status())) newAgendaEntity.setStatus(agendaRequest.status());
        return newAgendaEntity;
    }

    public AgendaResponse updateAgenda(Long targetId, AgendaRequest agendaRequest) {
        try {

            AgendaEntity foundAgendaEntity = agendaRepository.findById(targetId).orElse(null);
            log.info("Agenda encontrado com sucesso: {}", foundAgendaEntity);
            if (Objects.isNull(foundAgendaEntity))
                return new AgendaResponse(false, LocalDateTime.now() , "Agenda id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            AlunoEntity foundAlunoEntity = alunoRepository.findById(agendaRequest.id_aluno()).orElse(null);
            if (Objects.isNull(foundAlunoEntity))
                return new AgendaResponse(false, LocalDateTime.now(), "Aluno id [" + agendaRequest.id_aluno() + "] não encontrado", null, HttpStatus.NOT_FOUND);

            TutorEntity foundTutorEntity = tutorRepository.findById(agendaRequest.id_tutor()).orElse(null);
            if (Objects.isNull(foundTutorEntity))
                return new AgendaResponse(false, LocalDateTime.now() , "Tutor id [" + agendaRequest.id_tutor() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            AgendaEntity savedAgendaEntity = agendaRepository.save(
                    _setAgendaEntity(foundAgendaEntity, agendaRequest, foundAlunoEntity, foundTutorEntity)
            );

            log.info("Agenda atualizado com sucesso: {}", savedAgendaEntity);
            return new AgendaResponse(true, LocalDateTime.now(),"Agenda atualizada com sucesso.", Collections.singletonList(foundAgendaEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Agenda. Erro: {}", e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public AgendaResponse deleteAgenda(Long targetId) {
        try {
            AgendaEntity foundAgendaEntity = agendaRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundAgendaEntity))
                return new AgendaResponse(false, LocalDateTime.now(), "Agenda id [" + targetId + "] não encontrado", null, HttpStatus.NOT_FOUND);
            agendaRepository.delete(foundAgendaEntity);
            log.info("Agenda removida com sucesso: {}", foundAgendaEntity);
            return new AgendaResponse(true, LocalDateTime.now(),"Agenda removida com sucesso.", Collections.singletonList(foundAgendaEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Agenda. Erro: {}", e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public AgendaResponse getByAlunoId(long targetId) {
        try {
            List<AgendaEntity> agendasByAlunoId = agendaRepository.findAllByAlunoIdOrderByDataDesc(targetId);
            if (agendasByAlunoId.isEmpty()) {
                return new AgendaResponse(true, LocalDateTime.now(), "Nenhum registro encontrado para o Aluno id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new AgendaResponse(true, LocalDateTime.now() , "Registros encontrados: " + agendasByAlunoId.size() , agendasByAlunoId,  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Agendas para o Aluno id {} . Erro: {}", targetId, e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    public AgendaResponse getByTutorId(long targetId) {
        try {
            List<AgendaEntity> agendasByTutorId = agendaRepository.findAllByTutor_IdOrderByDataDesc(targetId);
            if (agendasByTutorId.isEmpty()) {
                return new AgendaResponse(true, LocalDateTime.now(), "Nenhum registro encontrado para o Tutor id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new AgendaResponse(true, LocalDateTime.now() , "Registros encontrados:" + agendasByTutorId.size() , agendasByTutorId,  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Agendas para o Tutor id {} . Erro: {}", targetId, e.getMessage());
            return new AgendaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
