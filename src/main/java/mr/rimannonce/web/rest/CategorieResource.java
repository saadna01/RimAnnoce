package mr.rimannonce.web.rest;

import com.codahale.metrics.annotation.Timed;
import mr.rimannonce.service.CategorieService;
import mr.rimannonce.web.rest.errors.BadRequestAlertException;
import mr.rimannonce.web.rest.util.HeaderUtil;
import mr.rimannonce.service.dto.CategorieDTO;
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
 * REST controller for managing Categorie.
 */
@RestController
@RequestMapping("/api")
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    private static final String ENTITY_NAME = "categorie";

    private final CategorieService categorieService;

    public CategorieResource(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * POST  /categories : Create a new categorie.
     *
     * @param categorieDTO the categorieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorieDTO, or with status 400 (Bad Request) if the categorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categories")
    @Timed
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody CategorieDTO categorieDTO) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorieDTO);
        if (categorieDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieDTO result = categorieService.save(categorieDTO);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categories : Updates an existing categorie.
     *
     * @param categorieDTO the categorieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorieDTO,
     * or with status 400 (Bad Request) if the categorieDTO is not valid,
     * or with status 500 (Internal Server Error) if the categorieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categories")
    @Timed
    public ResponseEntity<CategorieDTO> updateCategorie(@RequestBody CategorieDTO categorieDTO) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorieDTO);
        if (categorieDTO.getId() == null) {
            return createCategorie(categorieDTO);
        }
        CategorieDTO result = categorieService.save(categorieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    @Timed
    public List<CategorieDTO> getAllCategories() {
        log.debug("REST request to get all Categories");
        return categorieService.findAll();
        }

    /**
     * GET  /categories/:id : get the "id" categorie.
     *
     * @param id the id of the categorieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categories/{id}")
    @Timed
    public ResponseEntity<CategorieDTO> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        CategorieDTO categorieDTO = categorieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categorieDTO));
    }

    /**
     * DELETE  /categories/:id : delete the "id" categorie.
     *
     * @param id the id of the categorieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);
        categorieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
