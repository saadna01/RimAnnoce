package mr.rimannonce.service.impl;

import mr.rimannonce.service.MouqataaService;
import mr.rimannonce.domain.Mouqataa;
import mr.rimannonce.repository.MouqataaRepository;
import mr.rimannonce.service.dto.MouqataaDTO;
import mr.rimannonce.service.mapper.MouqataaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mouqataa.
 */
@Service
@Transactional
public class MouqataaServiceImpl implements MouqataaService {

    private final Logger log = LoggerFactory.getLogger(MouqataaServiceImpl.class);

    private final MouqataaRepository mouqataaRepository;

    private final MouqataaMapper mouqataaMapper;

    public MouqataaServiceImpl(MouqataaRepository mouqataaRepository, MouqataaMapper mouqataaMapper) {
        this.mouqataaRepository = mouqataaRepository;
        this.mouqataaMapper = mouqataaMapper;
    }

    /**
     * Save a mouqataa.
     *
     * @param mouqataaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MouqataaDTO save(MouqataaDTO mouqataaDTO) {
        log.debug("Request to save Mouqataa : {}", mouqataaDTO);
        Mouqataa mouqataa = mouqataaMapper.toEntity(mouqataaDTO);
        mouqataa = mouqataaRepository.save(mouqataa);
        return mouqataaMapper.toDto(mouqataa);
    }

    /**
     * Get all the mouqataas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MouqataaDTO> findAll() {
        log.debug("Request to get all Mouqataas");
        return mouqataaRepository.findAll().stream()
            .map(mouqataaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mouqataa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MouqataaDTO findOne(Long id) {
        log.debug("Request to get Mouqataa : {}", id);
        Mouqataa mouqataa = mouqataaRepository.findOne(id);
        return mouqataaMapper.toDto(mouqataa);
    }

    /**
     * Delete the mouqataa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mouqataa : {}", id);
        mouqataaRepository.delete(id);
    }
}
