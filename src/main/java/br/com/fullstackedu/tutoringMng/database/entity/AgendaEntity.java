package br.com.fullstackedu.tutoringMng.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agenda")
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    @NotNull(message = "Atributo id_aluno é obrigatorio")
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    @NotNull(message = "Atributo id_tutor é obrigatorio")
    private TutorEntity tutor;

    @Column(nullable = false)
    @NotNull(message = "Atributo data é obrigatório")
    private LocalDate data;

    private String tema;

    @Column(name = "descricao_breve")
    private String descricaoBreve;

    private String status;
}
