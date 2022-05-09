package eus.klimu.ubications.domain.service.implementation;

import eus.klimu.ubications.domain.repository.UbicationRepository;
import eus.klimu.ubications.domain.service.definition.UbicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UbicationServiceImp implements UbicationService {

    private final UbicationRepository ubicationRepository;

   /*
    @Override
    public Ubication saveUbication(Ubication ubication) {
        log.info("Saving ubication {} on the database.", ubication.getUbicationID());
        return ubicationRepository.save(ubication);
    }

    @Override
    public Ubication getUbicationByID(Long ubicationID) {
        return ubicationRepository.findById(ubicationID).orElse(null);
    }

    public List<Ubication> createUbicationList(List<Ubication> list) {
        return ubicationRepository.saveAll(list);
    }

    public List<Ubication> getUbicationList() {
        return ubicationRepository.findAll();
    }

    public Ubication updateUbicationById(Ubication ubication) {
        Optional<Ubication> ubicationFound = ubicationRepository.findById(ubication.getUbicationID());

        if (ubicationFound.isPresent()) {
            Ubication ubicationUpdate = ubicationFound.get();
            ubicationUpdate.setUbicationID(ubication.getUbicationID());
            ubicationUpdate.setLocalID(ubication.getLocalID());
            ubicationUpdate.setDescripcion(ubication.getDescripcion());
            ubicationUpdate.setFecha(ubication.getFecha());

            return ubicationRepository.save(ubication);
        } else {
            return null;
        }
    }
    */

    public String deleteUbicationById(long id) {
        ubicationRepository.deleteById(id);
        return "Ubication "+ id +" deleted";
    }
}
