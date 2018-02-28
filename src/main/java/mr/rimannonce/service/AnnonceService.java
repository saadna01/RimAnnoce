package mr.rimannonce.service;

import mr.rimannonce.service.dto.AnnonceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Annonce.
 */
public interface AnnonceService {

    /**
     * Save a annonce.
     *
     * @param annonceDTO the entity to save
     * @return the persisted entity
     */
    AnnonceDTO save(AnnonceDTO annonceDTO);

    /**
     * Get all the annonces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AnnonceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" annonce.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AnnonceDTO findOne(Long id);

    /**
     * Delete the "id" annonce.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
