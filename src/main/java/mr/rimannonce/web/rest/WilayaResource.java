package mr.rimannonce.web.rest;

import com.codahale.metrics.annotation.Timed;
import mr.rimannonce.service.WilayaService;
import mr.rimannonce.web.rest.errors.BadRequestAlertException;
import mr.rimannonce.web.rest.util.HeaderUtil;
import mr.rimannonce.service.dto.WilayaDTO;
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
 * REST controller for managing Wilaya.
 */
@RestController
@RequestMapping("/api")
public class WilayaResource {

    private final Logger log = LoggerFactory.getLogger(WilayaResource.class);

    private static final String ENTITY_NAME = "wilaya";

    private final WilayaService wilayaService;

    public WilayaResource(WilayaService wilayaService) {
        this.wilayaService = wilayaService;
    }

    /**
     * POST  /wilayas : Create a new wilaya.
     *
     * @param wilayaDTO the wilayaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wilayaDTO, or with status 400 (Bad Request) if the wilaya has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wilayas")
    @Timed
    public ResponseEntity<WilayaDTO> createWilaya(@RequestBody WilayaDTO wilayaDTO) throws URISyntaxException {
        log.debug("REST request to save Wilaya : {}", wilayaDTO);
        if (wilayaDTO.getId() != null) {
            throw new BadRequestAlertException("A new wilaya cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WilayaDTO result = wilayaService.save(wilayaDTO);
        return ResponseEntity.created(new URI("/api/wilayas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wilayas : Updates an existing wilaya.
     *
     * @param wilayaDTO the wilayaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wilayaDTO,
     * or with status 400 (Bad Request) if the wilayaDTO is not valid,
     * or with status 500 (Internal Server Error) if the wilayaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wilayas")
    @Timed
    public ResponseEntity<WilayaDTO> updateWilaya(@RequestBody WilayaDTO wilayaDTO) throws URISyntaxException {
        log.debug("REST request to update Wilaya : {}", wilayaDTO);
        if (wilayaDTO.getId() == null) {
            return createWilaya(wilayaDTO);
        }
        WilayaDTO result = wilayaService.save(wilayaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wilayaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wilayas : get all the wilayas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wilayas in body
     */
    @GetMapping("/wilayas")
    @Timed
    public List<WilayaDTO> getAllWilayas() {
        log.debug("REST request to get all Wilayas");
        return wilayaService.findAll();
        }

    /**
     * GET  /wilayas/:id : get the "id" wilaya.
     *
     * @param id the id of the wilayaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wilayaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wilayas/{id}")
    @Timed
    public ResponseEntity<WilayaDTO> getWilaya(@PathVariable Long id) {
        log.debug("REST request to get Wilaya : {}", id);
        WilayaDTO wilayaDTO = wilayaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wilayaDTO));
    }

    /**
     * DELETE  /wilayas/:id : delete the "id" wilaya.
     *
     * @param id the id of the wilayaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wilayas/{id}")
    @Timed
    public ResponseEntity<Void> deleteWilaya(@PathVariable Long id) {
        log.debug("REST request to delete Wilaya : {}", id);
        wilayaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
