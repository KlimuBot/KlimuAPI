package eus.klimu.canals.domain.repository;

import eus.klimu.canals.domain.model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanalRepository extends JpaRepository<Canal, Long> {

    Canal findByCanalID(Long canalID);

}
