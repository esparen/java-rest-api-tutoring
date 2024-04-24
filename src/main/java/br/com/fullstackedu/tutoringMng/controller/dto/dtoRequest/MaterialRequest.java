package br.com.fullstackedu.tutoringMng.controller.dto.dtoRequest;

import jakarta.validation.constraints.NotNull;

public record MaterialRequest(
        @NotNull(message = "Atributo id_agenda é obrigatorio")
        Long id_agenda,

        @NotNull(message = "Atributo caminho_arquivos é obrigatório")
        String caminho_arquivos,

        String descricao
) {
}
