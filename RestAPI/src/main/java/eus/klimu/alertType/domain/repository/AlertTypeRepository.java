package eus.klimu.alertType.domain.repository;

import eus.klimu.alertType.domain.model.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertTypeRepository extends JpaRepository<AlertType, Long> {

    AlertType findByAlertTypeID(Long alertTypeID);

}
