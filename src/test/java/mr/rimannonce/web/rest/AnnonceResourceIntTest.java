package mr.rimannonce.web.rest;

import mr.rimannonce.RimAnnoceApp;

import mr.rimannonce.domain.Annonce;
import mr.rimannonce.repository.AnnonceRepository;
import mr.rimannonce.service.AnnonceService;
import mr.rimannonce.service.dto.AnnonceDTO;
import mr.rimannonce.service.mapper.AnnonceMapper;
import mr.rimannonce.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mr.rimannonce.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mr.rimannonce.domain.enumeration.AnnonceStatus;
/**
 * Test class for the AnnonceResource REST controller.
 *
 * @see AnnonceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RimAnnoceApp.class)
public class AnnonceResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_3 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_3_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AnnonceStatus DEFAULT_STATUS = AnnonceStatus.PENDING;
    private static final AnnonceStatus UPDATED_STATUS = AnnonceStatus.CANCELED;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private AnnonceMapper annonceMapper;

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnonceMockMvc;

    private Annonce annonce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnonceResource annonceResource = new AnnonceResource(annonceService);
        this.restAnnonceMockMvc = MockMvcBuilders.standaloneSetup(annonceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annonce createEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .title(DEFAULT_TITLE)
            .details(DEFAULT_DETAILS)
            .photo1(DEFAULT_PHOTO_1)
            .photo1ContentType(DEFAULT_PHOTO_1_CONTENT_TYPE)
            .photo2(DEFAULT_PHOTO_2)
            .photo2ContentType(DEFAULT_PHOTO_2_CONTENT_TYPE)
            .photo3(DEFAULT_PHOTO_3)
            .photo3ContentType(DEFAULT_PHOTO_3_CONTENT_TYPE)
            .prix(DEFAULT_PRIX)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS);
        return annonce;
    }

    @Before
    public void initTest() {
        annonce = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnonce() throws Exception {
        int databaseSizeBeforeCreate = annonceRepository.findAll().size();

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);
        restAnnonceMockMvc.perform(post("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isCreated());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate + 1);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnnonce.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testAnnonce.getPhoto1()).isEqualTo(DEFAULT_PHOTO_1);
        assertThat(testAnnonce.getPhoto1ContentType()).isEqualTo(DEFAULT_PHOTO_1_CONTENT_TYPE);
        assertThat(testAnnonce.getPhoto2()).isEqualTo(DEFAULT_PHOTO_2);
        assertThat(testAnnonce.getPhoto2ContentType()).isEqualTo(DEFAULT_PHOTO_2_CONTENT_TYPE);
        assertThat(testAnnonce.getPhoto3()).isEqualTo(DEFAULT_PHOTO_3);
        assertThat(testAnnonce.getPhoto3ContentType()).isEqualTo(DEFAULT_PHOTO_3_CONTENT_TYPE);
        assertThat(testAnnonce.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testAnnonce.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAnnonce.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAnnonceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annonceRepository.findAll().size();

        // Create the Annonce with an existing ID
        annonce.setId(1L);
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceMockMvc.perform(post("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnonces() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get all the annonceList
        restAnnonceMockMvc.perform(get("/api/annonces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].photo1ContentType").value(hasItem(DEFAULT_PHOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo1").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_1))))
            .andExpect(jsonPath("$.[*].photo2ContentType").value(hasItem(DEFAULT_PHOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo2").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_2))))
            .andExpect(jsonPath("$.[*].photo3ContentType").value(hasItem(DEFAULT_PHOTO_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo3").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_3))))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get the annonce
        restAnnonceMockMvc.perform(get("/api/annonces/{id}", annonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annonce.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.photo1ContentType").value(DEFAULT_PHOTO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo1").value(Base64Utils.encodeToString(DEFAULT_PHOTO_1)))
            .andExpect(jsonPath("$.photo2ContentType").value(DEFAULT_PHOTO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo2").value(Base64Utils.encodeToString(DEFAULT_PHOTO_2)))
            .andExpect(jsonPath("$.photo3ContentType").value(DEFAULT_PHOTO_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo3").value(Base64Utils.encodeToString(DEFAULT_PHOTO_3)))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnonce() throws Exception {
        // Get the annonce
        restAnnonceMockMvc.perform(get("/api/annonces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Update the annonce
        Annonce updatedAnnonce = annonceRepository.findOne(annonce.getId());
        // Disconnect from session so that the updates on updatedAnnonce are not directly saved in db
        em.detach(updatedAnnonce);
        updatedAnnonce
            .title(UPDATED_TITLE)
            .details(UPDATED_DETAILS)
            .photo1(UPDATED_PHOTO_1)
            .photo1ContentType(UPDATED_PHOTO_1_CONTENT_TYPE)
            .photo2(UPDATED_PHOTO_2)
            .photo2ContentType(UPDATED_PHOTO_2_CONTENT_TYPE)
            .photo3(UPDATED_PHOTO_3)
            .photo3ContentType(UPDATED_PHOTO_3_CONTENT_TYPE)
            .prix(UPDATED_PRIX)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS);
        AnnonceDTO annonceDTO = annonceMapper.toDto(updatedAnnonce);

        restAnnonceMockMvc.perform(put("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnonce.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testAnnonce.getPhoto1()).isEqualTo(UPDATED_PHOTO_1);
        assertThat(testAnnonce.getPhoto1ContentType()).isEqualTo(UPDATED_PHOTO_1_CONTENT_TYPE);
        assertThat(testAnnonce.getPhoto2()).isEqualTo(UPDATED_PHOTO_2);
        assertThat(testAnnonce.getPhoto2ContentType()).isEqualTo(UPDATED_PHOTO_2_CONTENT_TYPE);
        assertThat(testAnnonce.getPhoto3()).isEqualTo(UPDATED_PHOTO_3);
        assertThat(testAnnonce.getPhoto3ContentType()).isEqualTo(UPDATED_PHOTO_3_CONTENT_TYPE);
        assertThat(testAnnonce.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testAnnonce.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAnnonce.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnonceMockMvc.perform(put("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isCreated());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);
        int databaseSizeBeforeDelete = annonceRepository.findAll().size();

        // Get the annonce
        restAnnonceMockMvc.perform(delete("/api/annonces/{id}", annonce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Annonce.class);
        Annonce annonce1 = new Annonce();
        annonce1.setId(1L);
        Annonce annonce2 = new Annonce();
        annonce2.setId(annonce1.getId());
        assertThat(annonce1).isEqualTo(annonce2);
        annonce2.setId(2L);
        assertThat(annonce1).isNotEqualTo(annonce2);
        annonce1.setId(null);
        assertThat(annonce1).isNotEqualTo(annonce2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnonceDTO.class);
        AnnonceDTO annonceDTO1 = new AnnonceDTO();
        annonceDTO1.setId(1L);
        AnnonceDTO annonceDTO2 = new AnnonceDTO();
        assertThat(annonceDTO1).isNotEqualTo(annonceDTO2);
        annonceDTO2.setId(annonceDTO1.getId());
        assertThat(annonceDTO1).isEqualTo(annonceDTO2);
        annonceDTO2.setId(2L);
        assertThat(annonceDTO1).isNotEqualTo(annonceDTO2);
        annonceDTO1.setId(null);
        assertThat(annonceDTO1).isNotEqualTo(annonceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(annonceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(annonceMapper.fromId(null)).isNull();
    }
}
