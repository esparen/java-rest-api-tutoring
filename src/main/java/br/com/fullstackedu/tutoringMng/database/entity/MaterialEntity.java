package br.com.fullstackedu.tutoringMng.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "material")
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(nullable = false, name = "caminho_arquivos")
    @NotNull(message = "Atributo caminho_arquivos é obrigatório")
    private String caminhoArquivos;

    @ManyToOne
    @JoinColumn(name = "id_agenda")
    @NotNull(message = "Atributo id_agenda é obrigatorio")
    private AgendaEntity agenda;




}
