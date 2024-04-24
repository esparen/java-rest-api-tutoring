package br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse;

import br.com.fullstackedu.tutoringMng.database.entity.TutorEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record TutorResponse(Boolean success, LocalDateTime timestamp, String message, List<TutorEntity> tutorData, HttpStatus httpStatus) {
}

