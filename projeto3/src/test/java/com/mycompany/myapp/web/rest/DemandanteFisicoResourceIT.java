package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandaFisica;
import com.mycompany.myapp.domain.DemandanteFisico;
import com.mycompany.myapp.repository.DemandanteFisicoRepository;
import com.mycompany.myapp.service.criteria.DemandanteFisicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteFisicoMapper;
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
 * Integration tests for the {@link DemandanteFisicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandanteFisicoResourceIT {

    private static final Long DEFAULT_CPF = 1L;
    private static final Long UPDATED_CPF = 2L;
    private static final Long SMALLER_CPF = 1L - 1L;

    private static final String DEFAULT_NOME_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEFONE = 1L;
    private static final Long UPDATED_TELEFONE = 2L;
    private static final Long SMALLER_TELEFONE = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/demandante-fisicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandanteFisicoRepository demandanteFisicoRepository;

    @Autowired
    private DemandanteFisicoMapper demandanteFisicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandanteFisicoMockMvc;

    private DemandanteFisico demandanteFisico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandanteFisico createEntity(EntityManager em) {
        DemandanteFisico demandanteFisico = new DemandanteFisico()
            .cpf(DEFAULT_CPF)
            .nomeCompleto(DEFAULT_NOME_COMPLETO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return demandanteFisico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandanteFisico createUpdatedEntity(EntityManager em) {
        DemandanteFisico demandanteFisico = new DemandanteFisico()
            .cpf(UPDATED_CPF)
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return demandanteFisico;
    }

    @BeforeEach
    public void initTest() {
        demandanteFisico = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandanteFisico() throws Exception {
        int databaseSizeBeforeCreate = demandanteFisicoRepository.findAll().size();
        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);
        restDemandanteFisicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeCreate + 1);
        DemandanteFisico testDemandanteFisico = demandanteFisicoList.get(demandanteFisicoList.size() - 1);
        assertThat(testDemandanteFisico.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testDemandanteFisico.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testDemandanteFisico.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandanteFisico.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createDemandanteFisicoWithExistingId() throws Exception {
        // Create the DemandanteFisico with an existing ID
        demandanteFisico.setId(1L);
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        int databaseSizeBeforeCreate = demandanteFisicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandanteFisicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicos() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandanteFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));
    }

    @Test
    @Transactional
    void getDemandanteFisico() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get the demandanteFisico
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL_ID, demandanteFisico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandanteFisico.getId().intValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.intValue()))
            .andExpect(jsonPath("$.nomeCompleto").value(DEFAULT_NOME_COMPLETO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.intValue()));
    }

    @Test
    @Transactional
    void getDemandanteFisicosByIdFiltering() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        Long id = demandanteFisico.getId();

        defaultDemandanteFisicoShouldBeFound("id.equals=" + id);
        defaultDemandanteFisicoShouldNotBeFound("id.notEquals=" + id);

        defaultDemandanteFisicoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDemandanteFisicoShouldNotBeFound("id.greaterThan=" + id);

        defaultDemandanteFisicoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDemandanteFisicoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf equals to DEFAULT_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf equals to UPDATED_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf not equals to DEFAULT_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.notEquals=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf not equals to UPDATED_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.notEquals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the demandanteFisicoList where cpf equals to UPDATED_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf is not null
        defaultDemandanteFisicoShouldBeFound("cpf.specified=true");

        // Get all the demandanteFisicoList where cpf is null
        defaultDemandanteFisicoShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf is greater than or equal to DEFAULT_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.greaterThanOrEqual=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf is greater than or equal to UPDATED_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.greaterThanOrEqual=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf is less than or equal to DEFAULT_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.lessThanOrEqual=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf is less than or equal to SMALLER_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.lessThanOrEqual=" + SMALLER_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsLessThanSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf is less than DEFAULT_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.lessThan=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf is less than UPDATED_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.lessThan=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByCpfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where cpf is greater than DEFAULT_CPF
        defaultDemandanteFisicoShouldNotBeFound("cpf.greaterThan=" + DEFAULT_CPF);

        // Get all the demandanteFisicoList where cpf is greater than SMALLER_CPF
        defaultDemandanteFisicoShouldBeFound("cpf.greaterThan=" + SMALLER_CPF);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto equals to DEFAULT_NOME_COMPLETO
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.equals=" + DEFAULT_NOME_COMPLETO);

        // Get all the demandanteFisicoList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.equals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto not equals to DEFAULT_NOME_COMPLETO
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.notEquals=" + DEFAULT_NOME_COMPLETO);

        // Get all the demandanteFisicoList where nomeCompleto not equals to UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.notEquals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto in DEFAULT_NOME_COMPLETO or UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.in=" + DEFAULT_NOME_COMPLETO + "," + UPDATED_NOME_COMPLETO);

        // Get all the demandanteFisicoList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.in=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto is not null
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.specified=true");

        // Get all the demandanteFisicoList where nomeCompleto is null
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoContainsSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto contains DEFAULT_NOME_COMPLETO
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.contains=" + DEFAULT_NOME_COMPLETO);

        // Get all the demandanteFisicoList where nomeCompleto contains UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.contains=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByNomeCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where nomeCompleto does not contain DEFAULT_NOME_COMPLETO
        defaultDemandanteFisicoShouldNotBeFound("nomeCompleto.doesNotContain=" + DEFAULT_NOME_COMPLETO);

        // Get all the demandanteFisicoList where nomeCompleto does not contain UPDATED_NOME_COMPLETO
        defaultDemandanteFisicoShouldBeFound("nomeCompleto.doesNotContain=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email equals to DEFAULT_EMAIL
        defaultDemandanteFisicoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the demandanteFisicoList where email equals to UPDATED_EMAIL
        defaultDemandanteFisicoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email not equals to DEFAULT_EMAIL
        defaultDemandanteFisicoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the demandanteFisicoList where email not equals to UPDATED_EMAIL
        defaultDemandanteFisicoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDemandanteFisicoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the demandanteFisicoList where email equals to UPDATED_EMAIL
        defaultDemandanteFisicoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email is not null
        defaultDemandanteFisicoShouldBeFound("email.specified=true");

        // Get all the demandanteFisicoList where email is null
        defaultDemandanteFisicoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailContainsSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email contains DEFAULT_EMAIL
        defaultDemandanteFisicoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the demandanteFisicoList where email contains UPDATED_EMAIL
        defaultDemandanteFisicoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where email does not contain DEFAULT_EMAIL
        defaultDemandanteFisicoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the demandanteFisicoList where email does not contain UPDATED_EMAIL
        defaultDemandanteFisicoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone equals to DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone equals to UPDATED_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone not equals to DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone not equals to UPDATED_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the demandanteFisicoList where telefone equals to UPDATED_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone is not null
        defaultDemandanteFisicoShouldBeFound("telefone.specified=true");

        // Get all the demandanteFisicoList where telefone is null
        defaultDemandanteFisicoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone is greater than or equal to DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.greaterThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone is greater than or equal to UPDATED_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.greaterThanOrEqual=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone is less than or equal to DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.lessThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone is less than or equal to SMALLER_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.lessThanOrEqual=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsLessThanSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone is less than DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.lessThan=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone is less than UPDATED_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.lessThan=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByTelefoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        // Get all the demandanteFisicoList where telefone is greater than DEFAULT_TELEFONE
        defaultDemandanteFisicoShouldNotBeFound("telefone.greaterThan=" + DEFAULT_TELEFONE);

        // Get all the demandanteFisicoList where telefone is greater than SMALLER_TELEFONE
        defaultDemandanteFisicoShouldBeFound("telefone.greaterThan=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteFisicosByDemandaIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);
        DemandaFisica demanda = DemandaFisicaResourceIT.createEntity(em);
        em.persist(demanda);
        em.flush();
        demandanteFisico.setDemanda(demanda);
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);
        Long demandaId = demanda.getId();

        // Get all the demandanteFisicoList where demanda equals to demandaId
        defaultDemandanteFisicoShouldBeFound("demandaId.equals=" + demandaId);

        // Get all the demandanteFisicoList where demanda equals to (demandaId + 1)
        defaultDemandanteFisicoShouldNotBeFound("demandaId.equals=" + (demandaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDemandanteFisicoShouldBeFound(String filter) throws Exception {
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandanteFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));

        // Check, that the count call also returns 1
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDemandanteFisicoShouldNotBeFound(String filter) throws Exception {
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDemandanteFisicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDemandanteFisico() throws Exception {
        // Get the demandanteFisico
        restDemandanteFisicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandanteFisico() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();

        // Update the demandanteFisico
        DemandanteFisico updatedDemandanteFisico = demandanteFisicoRepository.findById(demandanteFisico.getId()).get();
        // Disconnect from session so that the updates on updatedDemandanteFisico are not directly saved in db
        em.detach(updatedDemandanteFisico);
        updatedDemandanteFisico.cpf(UPDATED_CPF).nomeCompleto(UPDATED_NOME_COMPLETO).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(updatedDemandanteFisico);

        restDemandanteFisicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandanteFisicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteFisico testDemandanteFisico = demandanteFisicoList.get(demandanteFisicoList.size() - 1);
        assertThat(testDemandanteFisico.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDemandanteFisico.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testDemandanteFisico.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandanteFisico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandanteFisicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandanteFisicoWithPatch() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();

        // Update the demandanteFisico using partial update
        DemandanteFisico partialUpdatedDemandanteFisico = new DemandanteFisico();
        partialUpdatedDemandanteFisico.setId(demandanteFisico.getId());

        partialUpdatedDemandanteFisico.cpf(UPDATED_CPF);

        restDemandanteFisicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandanteFisico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandanteFisico))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteFisico testDemandanteFisico = demandanteFisicoList.get(demandanteFisicoList.size() - 1);
        assertThat(testDemandanteFisico.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDemandanteFisico.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testDemandanteFisico.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandanteFisico.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateDemandanteFisicoWithPatch() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();

        // Update the demandanteFisico using partial update
        DemandanteFisico partialUpdatedDemandanteFisico = new DemandanteFisico();
        partialUpdatedDemandanteFisico.setId(demandanteFisico.getId());

        partialUpdatedDemandanteFisico.cpf(UPDATED_CPF).nomeCompleto(UPDATED_NOME_COMPLETO).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);

        restDemandanteFisicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandanteFisico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandanteFisico))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteFisico testDemandanteFisico = demandanteFisicoList.get(demandanteFisicoList.size() - 1);
        assertThat(testDemandanteFisico.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDemandanteFisico.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testDemandanteFisico.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandanteFisico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandanteFisicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandanteFisico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteFisicoRepository.findAll().size();
        demandanteFisico.setId(count.incrementAndGet());

        // Create the DemandanteFisico
        DemandanteFisicoDTO demandanteFisicoDTO = demandanteFisicoMapper.toDto(demandanteFisico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteFisicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteFisicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandanteFisico in the database
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandanteFisico() throws Exception {
        // Initialize the database
        demandanteFisicoRepository.saveAndFlush(demandanteFisico);

        int databaseSizeBeforeDelete = demandanteFisicoRepository.findAll().size();

        // Delete the demandanteFisico
        restDemandanteFisicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandanteFisico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandanteFisico> demandanteFisicoList = demandanteFisicoRepository.findAll();
        assertThat(demandanteFisicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
