package br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AgendaRequest(
        @NotNull(message = "Atributo id_aluno é obrigatorio")
        Long id_aluno,

        @NotNull(message = "Atributo id_tutor é obrigatorio")
        Long id_tutor,

        @NotNull(message = "Atributo data é obrigatório")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,

        String tema,

        String descricao,

        @NotNull(message = "Atributo status é obrigatório")
        String status
) {
}
