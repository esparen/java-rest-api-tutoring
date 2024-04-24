package br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse;

import br.com.fullstackedu.tutoringMng.database.entity.AgendaEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record AgendaResponse(Boolean success, LocalDateTime timestamp, String message, List<AgendaEntity> agendaData, HttpStatus httpStatus) {
}

