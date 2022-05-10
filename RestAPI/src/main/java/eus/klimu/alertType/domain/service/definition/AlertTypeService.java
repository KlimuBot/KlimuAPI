package eus.klimu.alertType.domain.service.definition;

import eus.klimu.alertType.domain.model.AlertType;

public interface AlertTypeService {

    AlertType saveAlertType(AlertType alert);
    AlertType getAlertTypeByID(Long alertTypeID);
}
