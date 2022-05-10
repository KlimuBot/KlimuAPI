package eus.klimu.alerts.domain.repository;

import eus.klimu.alerts.domain.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Alert findByAlertID(Long alertID);

}
