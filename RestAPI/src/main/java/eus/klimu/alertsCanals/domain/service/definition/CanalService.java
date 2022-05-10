package eus.klimu.canals.domain.service.definition;

import eus.klimu.canals.domain.model.Canal;

public interface CanalService {

    Canal saveCanal(Canal canal);
    Canal getCanalByID(Long canalID);
}
