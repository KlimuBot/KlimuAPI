package eus.klimu.alerts.domain.service.implementation;

import eus.klimu.alerts.domain.model.Alert;
import eus.klimu.alerts.domain.repository.AlertRepository;
import eus.klimu.alerts.domain.service.definition.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImp implements AlertService {

    private final AlertRepository alertRepository;

    @Override
    public Alert saveAlert(Alert alert) {
        log.info("Saving alert {} on the database.", alert.getAlertID());
        return alertRepository.save(alert);
    }

    @Override
    public Alert getAlertByID(Long alertID) {
        return alertRepository.findById(alertID).orElse(null);
    }

    public List<Alert> createAlertList(List<Alert> list) {
        return alertRepository.saveAll(list);
    }

    public List<Alert> getAlertList() {
        return alertRepository.findAll();
    }

    public Alert updateAlertById(Alert alert) {
        Optional<Alert> alertFound = alertRepository.findById(alert.getAlertID());

        if (alertFound.isPresent()) {
            Alert alertUpdate = alertFound.get();
            alertUpdate.setAlertID(alert.getAlertID());
            alertUpdate.setLocalID(alert.getLocalID());
            alertUpdate.setDescripcion(alert.getDescripcion());
            alertUpdate.setFecha(alert.getFecha());

            return alertRepository.save(alert);
        } else {
            return null;
        }
    }

    public String deleteAlertById(long id) {
        alertRepository.deleteById(id);
        return "Alert "+ id +" deleted";
    }
}
