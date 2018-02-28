package mr.rimannonce.service;

import mr.rimannonce.service.dto.WilayaDTO;
import java.util.List;

/**
 * Service Interface for managing Wilaya.
 */
public interface WilayaService {

    /**
     * Save a wilaya.
     *
     * @param wilayaDTO the entity to save
     * @return the persisted entity
     */
    WilayaDTO save(WilayaDTO wilayaDTO);

    /**
     * Get all the wilayas.
     *
     * @return the list of entities
     */
    List<WilayaDTO> findAll();

    /**
     * Get the "id" wilaya.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WilayaDTO findOne(Long id);

    /**
     * Delete the "id" wilaya.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
