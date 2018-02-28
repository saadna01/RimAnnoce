package mr.rimannonce.web.rest;

import com.codahale.metrics.annotation.Timed;
import mr.rimannonce.service.MouqataaService;
import mr.rimannonce.web.rest.errors.BadRequestAlertException;
import mr.rimannonce.web.rest.util.HeaderUtil;
import mr.rimannonce.service.dto.MouqataaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mouqataa.
 */
@RestController
@RequestMapping("/api")
public class MouqataaResource {

    private final Logger log = LoggerFactory.getLogger(MouqataaResource.class);

    private static final String ENTITY_NAME = "mouqataa";

    private final MouqataaService mouqataaService;

    public MouqataaResource(MouqataaService mouqataaService) {
        this.mouqataaService = mouqataaService;
    }

    /**
     * POST  /mouqataas : Create a new mouqataa.
     *
     * @param mouqataaDTO the mouqataaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mouqataaDTO, or with status 400 (Bad Request) if the mouqataa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouqataas")
    @Timed
    public ResponseEntity<MouqataaDTO> createMouqataa(@RequestBody MouqataaDTO mouqataaDTO) throws URISyntaxException {
        log.debug("REST request to save Mouqataa : {}", mouqataaDTO);
        if (mouqataaDTO.getId() != null) {
            throw new BadRequestAlertException("A new mouqataa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MouqataaDTO result = mouqataaService.save(mouqataaDTO);
        return ResponseEntity.created(new URI("/api/mouqataas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mouqataas : Updates an existing mouqataa.
     *
     * @param mouqataaDTO the mouqataaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouqataaDTO,
     * or with status 400 (Bad Request) if the mouqataaDTO is not valid,
     * or with status 500 (Internal Server Error) if the mouqataaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouqataas")
    @Timed
    public ResponseEntity<MouqataaDTO> updateMouqataa(@RequestBody MouqataaDTO mouqataaDTO) throws URISyntaxException {
        log.debug("REST request to update Mouqataa : {}", mouqataaDTO);
        if (mouqataaDTO.getId() == null) {
            return createMouqataa(mouqataaDTO);
        }
        MouqataaDTO result = mouqataaService.save(mouqataaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouqataaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mouqataas : get all the mouqataas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mouqataas in body
     */
    @GetMapping("/mouqataas")
    @Timed
    public List<MouqataaDTO> getAllMouqataas() {
        log.debug("REST request to get all Mouqataas");
        return mouqataaService.findAll();
        }

    /**
     * GET  /mouqataas/:id : get the "id" mouqataa.
     *
     * @param id the id of the mouqataaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mouqataaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mouqataas/{id}")
    @Timed
    public ResponseEntity<MouqataaDTO> getMouqataa(@PathVariable Long id) {
        log.debug("REST request to get Mouqataa : {}", id);
        MouqataaDTO mouqataaDTO = mouqataaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouqataaDTO));
    }

    /**
     * DELETE  /mouqataas/:id : delete the "id" mouqataa.
     *
     * @param id the id of the mouqataaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouqataas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouqataa(@PathVariable Long id) {
        log.debug("REST request to delete Mouqataa : {}", id);
        mouqataaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
