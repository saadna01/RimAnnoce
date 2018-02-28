package mr.rimannonce.web.rest;

import com.codahale.metrics.annotation.Timed;
import mr.rimannonce.service.AnnonceService;
import mr.rimannonce.web.rest.errors.BadRequestAlertException;
import mr.rimannonce.web.rest.util.HeaderUtil;
import mr.rimannonce.web.rest.util.PaginationUtil;
import mr.rimannonce.service.dto.AnnonceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Annonce.
 */
@RestController
@RequestMapping("/api")
public class AnnonceResource {

    private final Logger log = LoggerFactory.getLogger(AnnonceResource.class);

    private static final String ENTITY_NAME = "annonce";

    private final AnnonceService annonceService;

    public AnnonceResource(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    /**
     * POST  /annonces : Create a new annonce.
     *
     * @param annonceDTO the annonceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annonceDTO, or with status 400 (Bad Request) if the annonce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annonces")
    @Timed
    public ResponseEntity<AnnonceDTO> createAnnonce(@RequestBody AnnonceDTO annonceDTO) throws URISyntaxException {
        log.debug("REST request to save Annonce : {}", annonceDTO);
        if (annonceDTO.getId() != null) {
            throw new BadRequestAlertException("A new annonce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnonceDTO result = annonceService.save(annonceDTO);
        return ResponseEntity.created(new URI("/api/annonces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annonces : Updates an existing annonce.
     *
     * @param annonceDTO the annonceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annonceDTO,
     * or with status 400 (Bad Request) if the annonceDTO is not valid,
     * or with status 500 (Internal Server Error) if the annonceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annonces")
    @Timed
    public ResponseEntity<AnnonceDTO> updateAnnonce(@RequestBody AnnonceDTO annonceDTO) throws URISyntaxException {
        log.debug("REST request to update Annonce : {}", annonceDTO);
        if (annonceDTO.getId() == null) {
            return createAnnonce(annonceDTO);
        }
        AnnonceDTO result = annonceService.save(annonceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annonceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annonces : get all the annonces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of annonces in body
     */
    @GetMapping("/annonces")
    @Timed
    public ResponseEntity<List<AnnonceDTO>> getAllAnnonces(Pageable pageable) {
        log.debug("REST request to get a page of Annonces");
        Page<AnnonceDTO> page = annonceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/annonces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /annonces/:id : get the "id" annonce.
     *
     * @param id the id of the annonceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annonceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/annonces/{id}")
    @Timed
    public ResponseEntity<AnnonceDTO> getAnnonce(@PathVariable Long id) {
        log.debug("REST request to get Annonce : {}", id);
        AnnonceDTO annonceDTO = annonceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annonceDTO));
    }

    /**
     * DELETE  /annonces/:id : delete the "id" annonce.
     *
     * @param id the id of the annonceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annonces/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        log.debug("REST request to delete Annonce : {}", id);
        annonceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
