package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Curso;
import com.mycompany.myapp.domain.DemandaJuridica;
import com.mycompany.myapp.repository.DemandaJuridicaRepository;
import com.mycompany.myapp.service.criteria.DemandaJuridicaCriteria;
import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
import com.mycompany.myapp.service.mapper.DemandaJuridicaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DemandaJuridicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandaJuridicaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demanda-juridicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandaJuridicaRepository demandaJuridicaRepository;

    @Autowired
    private DemandaJuridicaMapper demandaJuridicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandaJuridicaMockMvc;

    private DemandaJuridica demandaJuridica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandaJuridica createEntity(EntityManager em) {
        DemandaJuridica demandaJuridica = new DemandaJuridica().descricao(DEFAULT_DESCRICAO).curso(DEFAULT_CURSO);
        return demandaJuridica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandaJuridica createUpdatedEntity(EntityManager em) {
        DemandaJuridica demandaJuridica = new DemandaJuridica().descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);
        return demandaJuridica;
    }

    @BeforeEach
    public void initTest() {
        demandaJuridica = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandaJuridica() throws Exception {
        int databaseSizeBeforeCreate = demandaJuridicaRepository.findAll().size();
        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);
        restDemandaJuridicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeCreate + 1);
        DemandaJuridica testDemandaJuridica = demandaJuridicaList.get(demandaJuridicaList.size() - 1);
        assertThat(testDemandaJuridica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDemandaJuridica.getCurso()).isEqualTo(DEFAULT_CURSO);
    }

    @Test
    @Transactional
    void createDemandaJuridicaWithExistingId() throws Exception {
        // Create the DemandaJuridica with an existing ID
        demandaJuridica.setId(1L);
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        int databaseSizeBeforeCreate = demandaJuridicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandaJuridicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicas() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandaJuridica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)));
    }

    @Test
    @Transactional
    void getDemandaJuridica() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get the demandaJuridica
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL_ID, demandaJuridica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandaJuridica.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO));
    }

    @Test
    @Transactional
    void getDemandaJuridicasByIdFiltering() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        Long id = demandaJuridica.getId();

        defaultDemandaJuridicaShouldBeFound("id.equals=" + id);
        defaultDemandaJuridicaShouldNotBeFound("id.notEquals=" + id);

        defaultDemandaJuridicaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDemandaJuridicaShouldNotBeFound("id.greaterThan=" + id);

        defaultDemandaJuridicaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDemandaJuridicaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao equals to DEFAULT_DESCRICAO
        defaultDemandaJuridicaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the demandaJuridicaList where descricao equals to UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao not equals to DEFAULT_DESCRICAO
        defaultDemandaJuridicaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the demandaJuridicaList where descricao not equals to UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the demandaJuridicaList where descricao equals to UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao is not null
        defaultDemandaJuridicaShouldBeFound("descricao.specified=true");

        // Get all the demandaJuridicaList where descricao is null
        defaultDemandaJuridicaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao contains DEFAULT_DESCRICAO
        defaultDemandaJuridicaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the demandaJuridicaList where descricao contains UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where descricao does not contain DEFAULT_DESCRICAO
        defaultDemandaJuridicaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the demandaJuridicaList where descricao does not contain UPDATED_DESCRICAO
        defaultDemandaJuridicaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    
    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where curso not equals to DEFAULT_CURSO
        defaultDemandaJuridicaShouldNotBeFound("curso.notEquals=" + DEFAULT_CURSO);

        // Get all the demandaJuridicaList where curso not equals to UPDATED_CURSO
        defaultDemandaJuridicaShouldBeFound("curso.notEquals=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoIsInShouldWork() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where curso in DEFAULT_CURSO or UPDATED_CURSO
        defaultDemandaJuridicaShouldBeFound("curso.in=" + DEFAULT_CURSO + "," + UPDATED_CURSO);

        // Get all the demandaJuridicaList where curso equals to UPDATED_CURSO
        defaultDemandaJuridicaShouldNotBeFound("curso.in=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where curso is not null
        defaultDemandaJuridicaShouldBeFound("curso.specified=true");

        // Get all the demandaJuridicaList where curso is null
        defaultDemandaJuridicaShouldNotBeFound("curso.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoContainsSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where curso contains DEFAULT_CURSO
        defaultDemandaJuridicaShouldBeFound("curso.contains=" + DEFAULT_CURSO);

        // Get all the demandaJuridicaList where curso contains UPDATED_CURSO
        defaultDemandaJuridicaShouldNotBeFound("curso.contains=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoNotContainsSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        // Get all the demandaJuridicaList where curso does not contain DEFAULT_CURSO
        defaultDemandaJuridicaShouldNotBeFound("curso.doesNotContain=" + DEFAULT_CURSO);

        // Get all the demandaJuridicaList where curso does not contain UPDATED_CURSO
        defaultDemandaJuridicaShouldBeFound("curso.doesNotContain=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaJuridicasByCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);
        Curso curso = CursoResourceIT.createEntity(em);
        em.persist(curso);
        em.flush();
        demandaJuridica.addCurso(curso);
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);
        Long cursoId = curso.getId();

        // Get all the demandaJuridicaList where curso equals to cursoId
        defaultDemandaJuridicaShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the demandaJuridicaList where curso equals to (cursoId + 1)
        defaultDemandaJuridicaShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDemandaJuridicaShouldBeFound(String filter) throws Exception {
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandaJuridica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)));

        // Check, that the count call also returns 1
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDemandaJuridicaShouldNotBeFound(String filter) throws Exception {
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDemandaJuridicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDemandaJuridica() throws Exception {
        // Get the demandaJuridica
        restDemandaJuridicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandaJuridica() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();

        // Update the demandaJuridica
        DemandaJuridica updatedDemandaJuridica = demandaJuridicaRepository.findById(demandaJuridica.getId()).get();
        // Disconnect from session so that the updates on updatedDemandaJuridica are not directly saved in db
        em.detach(updatedDemandaJuridica);
        updatedDemandaJuridica.descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(updatedDemandaJuridica);

        restDemandaJuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandaJuridicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaJuridica testDemandaJuridica = demandaJuridicaList.get(demandaJuridicaList.size() - 1);
        assertThat(testDemandaJuridica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaJuridica.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void putNonExistingDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandaJuridicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandaJuridicaWithPatch() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();

        // Update the demandaJuridica using partial update
        DemandaJuridica partialUpdatedDemandaJuridica = new DemandaJuridica();
        partialUpdatedDemandaJuridica.setId(demandaJuridica.getId());

        partialUpdatedDemandaJuridica.descricao(UPDATED_DESCRICAO);

        restDemandaJuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandaJuridica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandaJuridica))
            )
            .andExpect(status().isOk());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaJuridica testDemandaJuridica = demandaJuridicaList.get(demandaJuridicaList.size() - 1);
        assertThat(testDemandaJuridica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaJuridica.getCurso()).isEqualTo(DEFAULT_CURSO);
    }

    @Test
    @Transactional
    void fullUpdateDemandaJuridicaWithPatch() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();

        // Update the demandaJuridica using partial update
        DemandaJuridica partialUpdatedDemandaJuridica = new DemandaJuridica();
        partialUpdatedDemandaJuridica.setId(demandaJuridica.getId());

        partialUpdatedDemandaJuridica.descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);

        restDemandaJuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandaJuridica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandaJuridica))
            )
            .andExpect(status().isOk());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaJuridica testDemandaJuridica = demandaJuridicaList.get(demandaJuridicaList.size() - 1);
        assertThat(testDemandaJuridica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaJuridica.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void patchNonExistingDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandaJuridicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandaJuridica() throws Exception {
        int databaseSizeBeforeUpdate = demandaJuridicaRepository.findAll().size();
        demandaJuridica.setId(count.incrementAndGet());

        // Create the DemandaJuridica
        DemandaJuridicaDTO demandaJuridicaDTO = demandaJuridicaMapper.toDto(demandaJuridica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaJuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaJuridicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandaJuridica in the database
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandaJuridica() throws Exception {
        // Initialize the database
        demandaJuridicaRepository.saveAndFlush(demandaJuridica);

        int databaseSizeBeforeDelete = demandaJuridicaRepository.findAll().size();

        // Delete the demandaJuridica
        restDemandaJuridicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandaJuridica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandaJuridica> demandaJuridicaList = demandaJuridicaRepository.findAll();
        assertThat(demandaJuridicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
