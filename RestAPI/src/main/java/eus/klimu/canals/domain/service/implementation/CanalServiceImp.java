package eus.klimu.canals.domain.service.implementation;

import eus.klimu.canals.domain.model.Canal;
import eus.klimu.canals.domain.repository.CanalRepository;
import eus.klimu.canals.domain.service.definition.CanalService;
import eus.klimu.canals.domain.service.definition.CanalService;
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
public class CanalServiceImp implements CanalService {

    private final CanalRepository canalRepository;

    @Override
    public Canal saveCanal(Canal canal) {
        log.info("Saving canal {} on the database.", canal.getCanalID());
        return canalRepository.save(canal);
    }

    @Override
    public Canal getCanalByID(Long canalID) {
        return canalRepository.findById(canalID).orElse(null);
    }

    public List<Canal> createCanalList(List<Canal> list) {
        return canalRepository.saveAll(list);
    }

    public List<Canal> getCanalList() {
        return canalRepository.findAll();
    }

    public Canal updateCanalById(Canal canal) {
        Optional<Canal> canalFound = canalRepository.findById(canal.getCanalID());

        if (canalFound.isPresent()) {
            Canal canalUpdate = canalFound.get();
            canalUpdate.setCanalID(canal.getCanalID());
            canalUpdate.setNombre(canal.getNombre());

            return canalRepository.save(canal);
        } else {
            return null;
        }
    }

    public String deleteCanalById(long id) {
        canalRepository.deleteById(id);
        return "Canal "+ id +" deleted";
    }
}
