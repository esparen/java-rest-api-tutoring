package br.com.fullstackedu.tutoringMng.database.repository;

import br.com.fullstackedu.tutoringMng.database.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    List<AgendaEntity> findAllByTutor_IdOrderByDataAsc(Long alunoId);

    @Query("SELECT a FROM AgendaEntity a WHERE a.aluno.id = :alunoId ORDER BY a.data ASC")
    List<AgendaEntity> findAllByAlunoIdOrderByDataAsc(@Param("alunoId") Long alunoId);

    @Query("SELECT a FROM AgendaEntity a WHERE a.aluno.id = :alunoId AND a.data >= :currentDate")
    List<AgendaEntity> findFutureAgendasByAlunoId(@Param("alunoId") Long alunoId, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT a FROM AgendaEntity a WHERE a.tutor.id = :tutorId AND a.data >= :currentDate")
    List<AgendaEntity> findFutureAgendasByTutorId(@Param("tutorId") Long tutorId, @Param("currentDate") LocalDate currentDate);
}
