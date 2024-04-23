package br.com.fullstackedu.tutoringMng.database.repository;

import br.com.fullstackedu.tutoringMng.database.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
}
