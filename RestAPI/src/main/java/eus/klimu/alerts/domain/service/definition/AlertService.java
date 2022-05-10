package eus.klimu.alerts.domain.service.definition;

import eus.klimu.alerts.domain.model.Alert;

public interface AlertService {

    Alert saveAlert(Alert alert);
    Alert getAlertByID(Long alertID);
}
