package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Curso;
import com.mycompany.myapp.domain.DemandaFisica;
import com.mycompany.myapp.domain.Endereco;
import com.mycompany.myapp.repository.DemandaFisicaRepository;
import com.mycompany.myapp.service.DemandaFisicaService;
import com.mycompany.myapp.service.criteria.DemandaFisicaCriteria;
import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
import com.mycompany.myapp.service.mapper.DemandaFisicaMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DemandaFisicaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandaFisicaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demanda-fisicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandaFisicaRepository demandaFisicaRepository;

    @Mock
    private DemandaFisicaRepository demandaFisicaRepositoryMock;

    @Autowired
    private DemandaFisicaMapper demandaFisicaMapper;

    @Mock
    private DemandaFisicaService demandaFisicaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandaFisicaMockMvc;

    private DemandaFisica demandaFisica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandaFisica createEntity(EntityManager em) {
        DemandaFisica demandaFisica = new DemandaFisica().descricao(DEFAULT_DESCRICAO).curso(DEFAULT_CURSO);
        return demandaFisica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandaFisica createUpdatedEntity(EntityManager em) {
        DemandaFisica demandaFisica = new DemandaFisica().descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);
        return demandaFisica;
    }

    @BeforeEach
    public void initTest() {
        demandaFisica = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandaFisica() throws Exception {
        int databaseSizeBeforeCreate = demandaFisicaRepository.findAll().size();
        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);
        restDemandaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeCreate + 1);
        DemandaFisica testDemandaFisica = demandaFisicaList.get(demandaFisicaList.size() - 1);
        assertThat(testDemandaFisica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDemandaFisica.getCurso()).isEqualTo(DEFAULT_CURSO);
    }

    @Test
    @Transactional
    void createDemandaFisicaWithExistingId() throws Exception {
        // Create the DemandaFisica with an existing ID
        demandaFisica.setId(1L);
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        int databaseSizeBeforeCreate = demandaFisicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandaFisicas() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandaFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandaFisicasWithEagerRelationshipsIsEnabled() throws Exception {
        when(demandaFisicaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandaFisicaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandaFisicaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandaFisicasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(demandaFisicaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandaFisicaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandaFisicaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDemandaFisica() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get the demandaFisica
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL_ID, demandaFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandaFisica.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO));
    }

    @Test
    @Transactional
    void getDemandaFisicasByIdFiltering() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        Long id = demandaFisica.getId();

        defaultDemandaFisicaShouldBeFound("id.equals=" + id);
        defaultDemandaFisicaShouldNotBeFound("id.notEquals=" + id);

        defaultDemandaFisicaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDemandaFisicaShouldNotBeFound("id.greaterThan=" + id);

        defaultDemandaFisicaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDemandaFisicaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao equals to DEFAULT_DESCRICAO
        defaultDemandaFisicaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the demandaFisicaList where descricao equals to UPDATED_DESCRICAO
        defaultDemandaFisicaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao not equals to DEFAULT_DESCRICAO
        defaultDemandaFisicaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the demandaFisicaList where descricao not equals to UPDATED_DESCRICAO
        defaultDemandaFisicaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDemandaFisicaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the demandaFisicaList where descricao equals to UPDATED_DESCRICAO
        defaultDemandaFisicaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao is not null
        defaultDemandaFisicaShouldBeFound("descricao.specified=true");

        // Get all the demandaFisicaList where descricao is null
        defaultDemandaFisicaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao contains DEFAULT_DESCRICAO
        defaultDemandaFisicaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the demandaFisicaList where descricao contains UPDATED_DESCRICAO
        defaultDemandaFisicaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where descricao does not contain DEFAULT_DESCRICAO
        defaultDemandaFisicaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the demandaFisicaList where descricao does not contain UPDATED_DESCRICAO
        defaultDemandaFisicaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

        @Test
    @Transactional
    void getAllDemandaFisicasByCursoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where curso not equals to DEFAULT_CURSO
        defaultDemandaFisicaShouldNotBeFound("curso.notEquals=" + DEFAULT_CURSO);

        // Get all the demandaFisicaList where curso not equals to UPDATED_CURSO
        defaultDemandaFisicaShouldBeFound("curso.notEquals=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByCursoIsInShouldWork() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where curso in DEFAULT_CURSO or UPDATED_CURSO
        defaultDemandaFisicaShouldBeFound("curso.in=" + DEFAULT_CURSO + "," + UPDATED_CURSO);

        // Get all the demandaFisicaList where curso equals to UPDATED_CURSO
        defaultDemandaFisicaShouldNotBeFound("curso.in=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where curso is not null
        defaultDemandaFisicaShouldBeFound("curso.specified=true");

        // Get all the demandaFisicaList where curso is null
        defaultDemandaFisicaShouldNotBeFound("curso.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByCursoContainsSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where curso contains DEFAULT_CURSO
        defaultDemandaFisicaShouldBeFound("curso.contains=" + DEFAULT_CURSO);

        // Get all the demandaFisicaList where curso contains UPDATED_CURSO
        defaultDemandaFisicaShouldNotBeFound("curso.contains=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByCursoNotContainsSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        // Get all the demandaFisicaList where curso does not contain DEFAULT_CURSO
        defaultDemandaFisicaShouldNotBeFound("curso.doesNotContain=" + DEFAULT_CURSO);

        // Get all the demandaFisicaList where curso does not contain UPDATED_CURSO
        defaultDemandaFisicaShouldBeFound("curso.doesNotContain=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);
        Curso curso = CursoResourceIT.createEntity(em);
        em.persist(curso);
        em.flush();
        demandaFisica.addCurso(curso);
        demandaFisicaRepository.saveAndFlush(demandaFisica);
        Long cursoId = curso.getId();

        // Get all the demandaFisicaList where curso equals to cursoId
        defaultDemandaFisicaShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the demandaFisicaList where curso equals to (cursoId + 1)
        defaultDemandaFisicaShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    @Test
    @Transactional
    void getAllDemandaFisicasByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        demandaFisica.addEndereco(endereco);
        demandaFisicaRepository.saveAndFlush(demandaFisica);
        Long enderecoId = endereco.getId();

        // Get all the demandaFisicaList where endereco equals to enderecoId
        defaultDemandaFisicaShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the demandaFisicaList where endereco equals to (enderecoId + 1)
        defaultDemandaFisicaShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDemandaFisicaShouldBeFound(String filter) throws Exception {
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandaFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)));

        // Check, that the count call also returns 1
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDemandaFisicaShouldNotBeFound(String filter) throws Exception {
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDemandaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDemandaFisica() throws Exception {
        // Get the demandaFisica
        restDemandaFisicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandaFisica() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();

        // Update the demandaFisica
        DemandaFisica updatedDemandaFisica = demandaFisicaRepository.findById(demandaFisica.getId()).get();
        // Disconnect from session so that the updates on updatedDemandaFisica are not directly saved in db
        em.detach(updatedDemandaFisica);
        updatedDemandaFisica.descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(updatedDemandaFisica);

        restDemandaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandaFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaFisica testDemandaFisica = demandaFisicaList.get(demandaFisicaList.size() - 1);
        assertThat(testDemandaFisica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaFisica.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void putNonExistingDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandaFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandaFisicaWithPatch() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();

        // Update the demandaFisica using partial update
        DemandaFisica partialUpdatedDemandaFisica = new DemandaFisica();
        partialUpdatedDemandaFisica.setId(demandaFisica.getId());

        partialUpdatedDemandaFisica.descricao(UPDATED_DESCRICAO);

        restDemandaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandaFisica))
            )
            .andExpect(status().isOk());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaFisica testDemandaFisica = demandaFisicaList.get(demandaFisicaList.size() - 1);
        assertThat(testDemandaFisica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaFisica.getCurso()).isEqualTo(DEFAULT_CURSO);
    }

    @Test
    @Transactional
    void fullUpdateDemandaFisicaWithPatch() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();

        // Update the demandaFisica using partial update
        DemandaFisica partialUpdatedDemandaFisica = new DemandaFisica();
        partialUpdatedDemandaFisica.setId(demandaFisica.getId());

        partialUpdatedDemandaFisica.descricao(UPDATED_DESCRICAO).curso(UPDATED_CURSO);

        restDemandaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandaFisica))
            )
            .andExpect(status().isOk());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
        DemandaFisica testDemandaFisica = demandaFisicaList.get(demandaFisicaList.size() - 1);
        assertThat(testDemandaFisica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDemandaFisica.getCurso()).isEqualTo(UPDATED_CURSO);
    }

    @Test
    @Transactional
    void patchNonExistingDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandaFisicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandaFisica() throws Exception {
        int databaseSizeBeforeUpdate = demandaFisicaRepository.findAll().size();
        demandaFisica.setId(count.incrementAndGet());

        // Create the DemandaFisica
        DemandaFisicaDTO demandaFisicaDTO = demandaFisicaMapper.toDto(demandaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandaFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandaFisica in the database
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandaFisica() throws Exception {
        // Initialize the database
        demandaFisicaRepository.saveAndFlush(demandaFisica);

        int databaseSizeBeforeDelete = demandaFisicaRepository.findAll().size();

        // Delete the demandaFisica
        restDemandaFisicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandaFisica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandaFisica> demandaFisicaList = demandaFisicaRepository.findAll();
        assertThat(demandaFisicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
