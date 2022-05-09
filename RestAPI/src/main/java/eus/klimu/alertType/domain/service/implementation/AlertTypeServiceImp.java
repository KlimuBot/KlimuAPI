package eus.klimu.alertType.domain.service.implementation;

import eus.klimu.alertType.domain.model.AlertType;
import eus.klimu.alertType.domain.repository.AlertTypeRepository;
import eus.klimu.alertType.domain.service.definition.AlertTypeService;
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
public class AlertTypeServiceImp implements AlertTypeService {

    private final AlertTypeRepository alertRepository;

    @Override
    public AlertType saveAlertType(AlertType alert) {
        log.info("Saving alertType{} on the database.", alert.getAlertTypeID());
        return alertRepository.save(alert);
    }

    @Override
    public AlertType getAlertTypeByID(Long alertTypeID) {
        return alertRepository.findById(alertTypeID).orElse(null);
    }

    public List<AlertType> createAlertTypeList(List<AlertType> list) {
        return alertRepository.saveAll(list);
    }

    public List<AlertType> getAlertTypeList() {
        return alertRepository.findAll();
    }

    public AlertType updateAlertTypeById(AlertType alertType) {
        Optional<AlertType> alertFound = alertRepository.findById(alertType.getAlertTypeID());

        if (alertFound.isPresent()) {
            AlertType alertUpdate = alertFound.get();
            alertUpdate.setAlertTypeID(alertType.getAlertTypeID());
            alertUpdate.setNombre(alertType.getNombre());
            alertUpdate.setRecomendacion(alertType.getRecomendacion());

            return alertRepository.save(alertType);
        } else {
            return null;
        }
    }

    public String deleteAlertTypeById(long id) {
        alertRepository.deleteById(id);
        return "AlertType "+ id +" deleted";
    }
}
