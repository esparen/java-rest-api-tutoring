package br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse;

import br.com.fullstackedu.tutoringMng.database.entity.AlunoEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record AlunoResponse(Boolean success, LocalDateTime timestamp, String message, List<AlunoEntity> alunoData, HttpStatus httpStatus) {
}

