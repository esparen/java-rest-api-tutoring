package br.com.fullstackedu.tutoringMng.database.repository;

import br.com.fullstackedu.tutoringMng.database.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {
}
