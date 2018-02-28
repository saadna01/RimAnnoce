package mr.rimannonce.service.impl;

import mr.rimannonce.service.WilayaService;
import mr.rimannonce.domain.Wilaya;
import mr.rimannonce.repository.WilayaRepository;
import mr.rimannonce.service.dto.WilayaDTO;
import mr.rimannonce.service.mapper.WilayaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Wilaya.
 */
@Service
@Transactional
public class WilayaServiceImpl implements WilayaService {

    private final Logger log = LoggerFactory.getLogger(WilayaServiceImpl.class);

    private final WilayaRepository wilayaRepository;

    private final WilayaMapper wilayaMapper;

    public WilayaServiceImpl(WilayaRepository wilayaRepository, WilayaMapper wilayaMapper) {
        this.wilayaRepository = wilayaRepository;
        this.wilayaMapper = wilayaMapper;
    }

    /**
     * Save a wilaya.
     *
     * @param wilayaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WilayaDTO save(WilayaDTO wilayaDTO) {
        log.debug("Request to save Wilaya : {}", wilayaDTO);
        Wilaya wilaya = wilayaMapper.toEntity(wilayaDTO);
        wilaya = wilayaRepository.save(wilaya);
        return wilayaMapper.toDto(wilaya);
    }

    /**
     * Get all the wilayas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WilayaDTO> findAll() {
        log.debug("Request to get all Wilayas");
        return wilayaRepository.findAll().stream()
            .map(wilayaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one wilaya by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WilayaDTO findOne(Long id) {
        log.debug("Request to get Wilaya : {}", id);
        Wilaya wilaya = wilayaRepository.findOne(id);
        return wilayaMapper.toDto(wilaya);
    }

    /**
     * Delete the wilaya by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wilaya : {}", id);
        wilayaRepository.delete(id);
    }
}
