package mr.rimannonce.web.rest;

import mr.rimannonce.RimAnnoceApp;

import mr.rimannonce.domain.Wilaya;
import mr.rimannonce.repository.WilayaRepository;
import mr.rimannonce.service.WilayaService;
import mr.rimannonce.service.dto.WilayaDTO;
import mr.rimannonce.service.mapper.WilayaMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static mr.rimannonce.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WilayaResource REST controller.
 *
 * @see WilayaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RimAnnoceApp.class)
public class WilayaResourceIntTest {

    private static final String DEFAULT_WILAYA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WILAYA_NAME = "BBBBBBBBBB";

    @Autowired
    private WilayaRepository wilayaRepository;

    @Autowired
    private WilayaMapper wilayaMapper;

    @Autowired
    private WilayaService wilayaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWilayaMockMvc;

    private Wilaya wilaya;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WilayaResource wilayaResource = new WilayaResource(wilayaService);
        this.restWilayaMockMvc = MockMvcBuilders.standaloneSetup(wilayaResource)
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
    public static Wilaya createEntity(EntityManager em) {
        Wilaya wilaya = new Wilaya()
            .wilayaName(DEFAULT_WILAYA_NAME);
        return wilaya;
    }

    @Before
    public void initTest() {
        wilaya = createEntity(em);
    }

    @Test
    @Transactional
    public void createWilaya() throws Exception {
        int databaseSizeBeforeCreate = wilayaRepository.findAll().size();

        // Create the Wilaya
        WilayaDTO wilayaDTO = wilayaMapper.toDto(wilaya);
        restWilayaMockMvc.perform(post("/api/wilayas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wilayaDTO)))
            .andExpect(status().isCreated());

        // Validate the Wilaya in the database
        List<Wilaya> wilayaList = wilayaRepository.findAll();
        assertThat(wilayaList).hasSize(databaseSizeBeforeCreate + 1);
        Wilaya testWilaya = wilayaList.get(wilayaList.size() - 1);
        assertThat(testWilaya.getWilayaName()).isEqualTo(DEFAULT_WILAYA_NAME);
    }

    @Test
    @Transactional
    public void createWilayaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wilayaRepository.findAll().size();

        // Create the Wilaya with an existing ID
        wilaya.setId(1L);
        WilayaDTO wilayaDTO = wilayaMapper.toDto(wilaya);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWilayaMockMvc.perform(post("/api/wilayas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wilayaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wilaya in the database
        List<Wilaya> wilayaList = wilayaRepository.findAll();
        assertThat(wilayaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWilayas() throws Exception {
        // Initialize the database
        wilayaRepository.saveAndFlush(wilaya);

        // Get all the wilayaList
        restWilayaMockMvc.perform(get("/api/wilayas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wilaya.getId().intValue())))
            .andExpect(jsonPath("$.[*].wilayaName").value(hasItem(DEFAULT_WILAYA_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWilaya() throws Exception {
        // Initialize the database
        wilayaRepository.saveAndFlush(wilaya);

        // Get the wilaya
        restWilayaMockMvc.perform(get("/api/wilayas/{id}", wilaya.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wilaya.getId().intValue()))
            .andExpect(jsonPath("$.wilayaName").value(DEFAULT_WILAYA_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWilaya() throws Exception {
        // Get the wilaya
        restWilayaMockMvc.perform(get("/api/wilayas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWilaya() throws Exception {
        // Initialize the database
        wilayaRepository.saveAndFlush(wilaya);
        int databaseSizeBeforeUpdate = wilayaRepository.findAll().size();

        // Update the wilaya
        Wilaya updatedWilaya = wilayaRepository.findOne(wilaya.getId());
        // Disconnect from session so that the updates on updatedWilaya are not directly saved in db
        em.detach(updatedWilaya);
        updatedWilaya
            .wilayaName(UPDATED_WILAYA_NAME);
        WilayaDTO wilayaDTO = wilayaMapper.toDto(updatedWilaya);

        restWilayaMockMvc.perform(put("/api/wilayas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wilayaDTO)))
            .andExpect(status().isOk());

        // Validate the Wilaya in the database
        List<Wilaya> wilayaList = wilayaRepository.findAll();
        assertThat(wilayaList).hasSize(databaseSizeBeforeUpdate);
        Wilaya testWilaya = wilayaList.get(wilayaList.size() - 1);
        assertThat(testWilaya.getWilayaName()).isEqualTo(UPDATED_WILAYA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWilaya() throws Exception {
        int databaseSizeBeforeUpdate = wilayaRepository.findAll().size();

        // Create the Wilaya
        WilayaDTO wilayaDTO = wilayaMapper.toDto(wilaya);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWilayaMockMvc.perform(put("/api/wilayas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wilayaDTO)))
            .andExpect(status().isCreated());

        // Validate the Wilaya in the database
        List<Wilaya> wilayaList = wilayaRepository.findAll();
        assertThat(wilayaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWilaya() throws Exception {
        // Initialize the database
        wilayaRepository.saveAndFlush(wilaya);
        int databaseSizeBeforeDelete = wilayaRepository.findAll().size();

        // Get the wilaya
        restWilayaMockMvc.perform(delete("/api/wilayas/{id}", wilaya.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wilaya> wilayaList = wilayaRepository.findAll();
        assertThat(wilayaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wilaya.class);
        Wilaya wilaya1 = new Wilaya();
        wilaya1.setId(1L);
        Wilaya wilaya2 = new Wilaya();
        wilaya2.setId(wilaya1.getId());
        assertThat(wilaya1).isEqualTo(wilaya2);
        wilaya2.setId(2L);
        assertThat(wilaya1).isNotEqualTo(wilaya2);
        wilaya1.setId(null);
        assertThat(wilaya1).isNotEqualTo(wilaya2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WilayaDTO.class);
        WilayaDTO wilayaDTO1 = new WilayaDTO();
        wilayaDTO1.setId(1L);
        WilayaDTO wilayaDTO2 = new WilayaDTO();
        assertThat(wilayaDTO1).isNotEqualTo(wilayaDTO2);
        wilayaDTO2.setId(wilayaDTO1.getId());
        assertThat(wilayaDTO1).isEqualTo(wilayaDTO2);
        wilayaDTO2.setId(2L);
        assertThat(wilayaDTO1).isNotEqualTo(wilayaDTO2);
        wilayaDTO1.setId(null);
        assertThat(wilayaDTO1).isNotEqualTo(wilayaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wilayaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wilayaMapper.fromId(null)).isNull();
    }
}
