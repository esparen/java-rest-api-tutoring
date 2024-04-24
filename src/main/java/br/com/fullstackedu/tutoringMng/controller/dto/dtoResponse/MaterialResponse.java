package br.com.fullstackedu.tutoringMng.controller.dto.dtoResponse;

import br.com.fullstackedu.tutoringMng.database.entity.MaterialEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record MaterialResponse(Boolean success, LocalDateTime timestamp, String message, List<MaterialEntity> materialData, HttpStatus httpStatus) {
}

