package eus.klimu.ubications.domain.repository;

import eus.klimu.ubications.domain.model.Ubication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicationRepository extends JpaRepository<Ubication, Long> {

    //Ubication findByUbicationID(Long ubicationID);

}
