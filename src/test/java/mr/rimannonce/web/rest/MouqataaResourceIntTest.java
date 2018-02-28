package mr.rimannonce.web.rest;

import mr.rimannonce.RimAnnoceApp;

import mr.rimannonce.domain.Mouqataa;
import mr.rimannonce.repository.MouqataaRepository;
import mr.rimannonce.service.MouqataaService;
import mr.rimannonce.service.dto.MouqataaDTO;
import mr.rimannonce.service.mapper.MouqataaMapper;
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
 * Test class for the MouqataaResource REST controller.
 *
 * @see MouqataaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RimAnnoceApp.class)
public class MouqataaResourceIntTest {

    private static final String DEFAULT_MOUQATAA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOUQATAA_NAME = "BBBBBBBBBB";

    @Autowired
    private MouqataaRepository mouqataaRepository;

    @Autowired
    private MouqataaMapper mouqataaMapper;

    @Autowired
    private MouqataaService mouqataaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMouqataaMockMvc;

    private Mouqataa mouqataa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MouqataaResource mouqataaResource = new MouqataaResource(mouqataaService);
        this.restMouqataaMockMvc = MockMvcBuilders.standaloneSetup(mouqataaResource)
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
    public static Mouqataa createEntity(EntityManager em) {
        Mouqataa mouqataa = new Mouqataa()
            .mouqataaName(DEFAULT_MOUQATAA_NAME);
        return mouqataa;
    }

    @Before
    public void initTest() {
        mouqataa = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouqataa() throws Exception {
        int databaseSizeBeforeCreate = mouqataaRepository.findAll().size();

        // Create the Mouqataa
        MouqataaDTO mouqataaDTO = mouqataaMapper.toDto(mouqataa);
        restMouqataaMockMvc.perform(post("/api/mouqataas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouqataaDTO)))
            .andExpect(status().isCreated());

        // Validate the Mouqataa in the database
        List<Mouqataa> mouqataaList = mouqataaRepository.findAll();
        assertThat(mouqataaList).hasSize(databaseSizeBeforeCreate + 1);
        Mouqataa testMouqataa = mouqataaList.get(mouqataaList.size() - 1);
        assertThat(testMouqataa.getMouqataaName()).isEqualTo(DEFAULT_MOUQATAA_NAME);
    }

    @Test
    @Transactional
    public void createMouqataaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouqataaRepository.findAll().size();

        // Create the Mouqataa with an existing ID
        mouqataa.setId(1L);
        MouqataaDTO mouqataaDTO = mouqataaMapper.toDto(mouqataa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouqataaMockMvc.perform(post("/api/mouqataas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouqataaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mouqataa in the database
        List<Mouqataa> mouqataaList = mouqataaRepository.findAll();
        assertThat(mouqataaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMouqataas() throws Exception {
        // Initialize the database
        mouqataaRepository.saveAndFlush(mouqataa);

        // Get all the mouqataaList
        restMouqataaMockMvc.perform(get("/api/mouqataas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouqataa.getId().intValue())))
            .andExpect(jsonPath("$.[*].mouqataaName").value(hasItem(DEFAULT_MOUQATAA_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMouqataa() throws Exception {
        // Initialize the database
        mouqataaRepository.saveAndFlush(mouqataa);

        // Get the mouqataa
        restMouqataaMockMvc.perform(get("/api/mouqataas/{id}", mouqataa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mouqataa.getId().intValue()))
            .andExpect(jsonPath("$.mouqataaName").value(DEFAULT_MOUQATAA_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMouqataa() throws Exception {
        // Get the mouqataa
        restMouqataaMockMvc.perform(get("/api/mouqataas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouqataa() throws Exception {
        // Initialize the database
        mouqataaRepository.saveAndFlush(mouqataa);
        int databaseSizeBeforeUpdate = mouqataaRepository.findAll().size();

        // Update the mouqataa
        Mouqataa updatedMouqataa = mouqataaRepository.findOne(mouqataa.getId());
        // Disconnect from session so that the updates on updatedMouqataa are not directly saved in db
        em.detach(updatedMouqataa);
        updatedMouqataa
            .mouqataaName(UPDATED_MOUQATAA_NAME);
        MouqataaDTO mouqataaDTO = mouqataaMapper.toDto(updatedMouqataa);

        restMouqataaMockMvc.perform(put("/api/mouqataas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouqataaDTO)))
            .andExpect(status().isOk());

        // Validate the Mouqataa in the database
        List<Mouqataa> mouqataaList = mouqataaRepository.findAll();
        assertThat(mouqataaList).hasSize(databaseSizeBeforeUpdate);
        Mouqataa testMouqataa = mouqataaList.get(mouqataaList.size() - 1);
        assertThat(testMouqataa.getMouqataaName()).isEqualTo(UPDATED_MOUQATAA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMouqataa() throws Exception {
        int databaseSizeBeforeUpdate = mouqataaRepository.findAll().size();

        // Create the Mouqataa
        MouqataaDTO mouqataaDTO = mouqataaMapper.toDto(mouqataa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMouqataaMockMvc.perform(put("/api/mouqataas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mouqataaDTO)))
            .andExpect(status().isCreated());

        // Validate the Mouqataa in the database
        List<Mouqataa> mouqataaList = mouqataaRepository.findAll();
        assertThat(mouqataaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMouqataa() throws Exception {
        // Initialize the database
        mouqataaRepository.saveAndFlush(mouqataa);
        int databaseSizeBeforeDelete = mouqataaRepository.findAll().size();

        // Get the mouqataa
        restMouqataaMockMvc.perform(delete("/api/mouqataas/{id}", mouqataa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mouqataa> mouqataaList = mouqataaRepository.findAll();
        assertThat(mouqataaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mouqataa.class);
        Mouqataa mouqataa1 = new Mouqataa();
        mouqataa1.setId(1L);
        Mouqataa mouqataa2 = new Mouqataa();
        mouqataa2.setId(mouqataa1.getId());
        assertThat(mouqataa1).isEqualTo(mouqataa2);
        mouqataa2.setId(2L);
        assertThat(mouqataa1).isNotEqualTo(mouqataa2);
        mouqataa1.setId(null);
        assertThat(mouqataa1).isNotEqualTo(mouqataa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MouqataaDTO.class);
        MouqataaDTO mouqataaDTO1 = new MouqataaDTO();
        mouqataaDTO1.setId(1L);
        MouqataaDTO mouqataaDTO2 = new MouqataaDTO();
        assertThat(mouqataaDTO1).isNotEqualTo(mouqataaDTO2);
        mouqataaDTO2.setId(mouqataaDTO1.getId());
        assertThat(mouqataaDTO1).isEqualTo(mouqataaDTO2);
        mouqataaDTO2.setId(2L);
        assertThat(mouqataaDTO1).isNotEqualTo(mouqataaDTO2);
        mouqataaDTO1.setId(null);
        assertThat(mouqataaDTO1).isNotEqualTo(mouqataaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mouqataaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mouqataaMapper.fromId(null)).isNull();
    }
}
