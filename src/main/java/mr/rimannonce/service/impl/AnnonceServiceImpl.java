package mr.rimannonce.service.impl;

import mr.rimannonce.service.AnnonceService;
import mr.rimannonce.domain.Annonce;
import mr.rimannonce.repository.AnnonceRepository;
import mr.rimannonce.service.dto.AnnonceDTO;
import mr.rimannonce.service.mapper.AnnonceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Annonce.
 */
@Service
@Transactional
public class AnnonceServiceImpl implements AnnonceService {

    private final Logger log = LoggerFactory.getLogger(AnnonceServiceImpl.class);

    private final AnnonceRepository annonceRepository;

    private final AnnonceMapper annonceMapper;

    public AnnonceServiceImpl(AnnonceRepository annonceRepository, AnnonceMapper annonceMapper) {
        this.annonceRepository = annonceRepository;
        this.annonceMapper = annonceMapper;
    }

    /**
     * Save a annonce.
     *
     * @param annonceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnnonceDTO save(AnnonceDTO annonceDTO) {
        log.debug("Request to save Annonce : {}", annonceDTO);
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonce = annonceRepository.save(annonce);
        return annonceMapper.toDto(annonce);
    }

    /**
     * Get all the annonces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnnonceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Annonces");
        return annonceRepository.findAll(pageable)
            .map(annonceMapper::toDto);
    }

    /**
     * Get one annonce by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnnonceDTO findOne(Long id) {
        log.debug("Request to get Annonce : {}", id);
        Annonce annonce = annonceRepository.findOne(id);
        return annonceMapper.toDto(annonce);
    }

    /**
     * Delete the annonce by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Annonce : {}", id);
        annonceRepository.delete(id);
    }
}
