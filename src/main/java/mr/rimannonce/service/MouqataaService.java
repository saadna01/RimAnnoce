package mr.rimannonce.service;

import mr.rimannonce.service.dto.MouqataaDTO;
import java.util.List;

/**
 * Service Interface for managing Mouqataa.
 */
public interface MouqataaService {

    /**
     * Save a mouqataa.
     *
     * @param mouqataaDTO the entity to save
     * @return the persisted entity
     */
    MouqataaDTO save(MouqataaDTO mouqataaDTO);

    /**
     * Get all the mouqataas.
     *
     * @return the list of entities
     */
    List<MouqataaDTO> findAll();

    /**
     * Get the "id" mouqataa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MouqataaDTO findOne(Long id);

    /**
     * Delete the "id" mouqataa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
