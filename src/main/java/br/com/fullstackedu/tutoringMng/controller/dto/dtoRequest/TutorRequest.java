package br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record TutorRequest(
        @Column(nullable = false)
        @NotBlank(message = "Atributo nome é obrigatório")
        String nome,

        String especialidade
) {
}
