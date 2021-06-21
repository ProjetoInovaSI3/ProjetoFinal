package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandaJuridica;
import com.mycompany.myapp.domain.DemandanteJuridico;
import com.mycompany.myapp.repository.DemandanteJuridicoRepository;
import com.mycompany.myapp.service.criteria.DemandanteJuridicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteJuridicoMapper;
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
 * Integration tests for the {@link DemandanteJuridicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandanteJuridicoResourceIT {

    private static final Long DEFAULT_CNPJ = 1L;
    private static final Long UPDATED_CNPJ = 2L;
    private static final Long SMALLER_CNPJ = 1L - 1L;

    private static final String DEFAULT_NOME_DA_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMEFANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMEFANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEFONE = 1L;
    private static final Long UPDATED_TELEFONE = 2L;
    private static final Long SMALLER_TELEFONE = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/demandante-juridicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandanteJuridicoRepository demandanteJuridicoRepository;

    @Autowired
    private DemandanteJuridicoMapper demandanteJuridicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandanteJuridicoMockMvc;

    private DemandanteJuridico demandanteJuridico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandanteJuridico createEntity(EntityManager em) {
        DemandanteJuridico demandanteJuridico = new DemandanteJuridico()
            .cnpj(DEFAULT_CNPJ)
            .nomeDaEmpresa(DEFAULT_NOME_DA_EMPRESA)
            .nomefantasia(DEFAULT_NOMEFANTASIA)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return demandanteJuridico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandanteJuridico createUpdatedEntity(EntityManager em) {
        DemandanteJuridico demandanteJuridico = new DemandanteJuridico()
            .cnpj(UPDATED_CNPJ)
            .nomeDaEmpresa(UPDATED_NOME_DA_EMPRESA)
            .nomefantasia(UPDATED_NOMEFANTASIA)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return demandanteJuridico;
    }

    @BeforeEach
    public void initTest() {
        demandanteJuridico = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandanteJuridico() throws Exception {
        int databaseSizeBeforeCreate = demandanteJuridicoRepository.findAll().size();
        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);
        restDemandanteJuridicoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeCreate + 1);
        DemandanteJuridico testDemandanteJuridico = demandanteJuridicoList.get(demandanteJuridicoList.size() - 1);
        assertThat(testDemandanteJuridico.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testDemandanteJuridico.getNomeDaEmpresa()).isEqualTo(DEFAULT_NOME_DA_EMPRESA);
        assertThat(testDemandanteJuridico.getNomefantasia()).isEqualTo(DEFAULT_NOMEFANTASIA);
        assertThat(testDemandanteJuridico.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandanteJuridico.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createDemandanteJuridicoWithExistingId() throws Exception {
        // Create the DemandanteJuridico with an existing ID
        demandanteJuridico.setId(1L);
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        int databaseSizeBeforeCreate = demandanteJuridicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandanteJuridicoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicos() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandanteJuridico.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.intValue())))
            .andExpect(jsonPath("$.[*].nomeDaEmpresa").value(hasItem(DEFAULT_NOME_DA_EMPRESA)))
            .andExpect(jsonPath("$.[*].nomefantasia").value(hasItem(DEFAULT_NOMEFANTASIA)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));
    }

    @Test
    @Transactional
    void getDemandanteJuridico() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get the demandanteJuridico
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL_ID, demandanteJuridico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandanteJuridico.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.intValue()))
            .andExpect(jsonPath("$.nomeDaEmpresa").value(DEFAULT_NOME_DA_EMPRESA))
            .andExpect(jsonPath("$.nomefantasia").value(DEFAULT_NOMEFANTASIA))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.intValue()));
    }

    @Test
    @Transactional
    void getDemandanteJuridicosByIdFiltering() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        Long id = demandanteJuridico.getId();

        defaultDemandanteJuridicoShouldBeFound("id.equals=" + id);
        defaultDemandanteJuridicoShouldNotBeFound("id.notEquals=" + id);

        defaultDemandanteJuridicoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDemandanteJuridicoShouldNotBeFound("id.greaterThan=" + id);

        defaultDemandanteJuridicoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDemandanteJuridicoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj equals to DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.equals=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj equals to UPDATED_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.equals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj not equals to DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.notEquals=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj not equals to UPDATED_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.notEquals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj in DEFAULT_CNPJ or UPDATED_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.in=" + DEFAULT_CNPJ + "," + UPDATED_CNPJ);

        // Get all the demandanteJuridicoList where cnpj equals to UPDATED_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.in=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj is not null
        defaultDemandanteJuridicoShouldBeFound("cnpj.specified=true");

        // Get all the demandanteJuridicoList where cnpj is null
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj is greater than or equal to DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.greaterThanOrEqual=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj is greater than or equal to UPDATED_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.greaterThanOrEqual=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj is less than or equal to DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.lessThanOrEqual=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj is less than or equal to SMALLER_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.lessThanOrEqual=" + SMALLER_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsLessThanSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj is less than DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.lessThan=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj is less than UPDATED_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.lessThan=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByCnpjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where cnpj is greater than DEFAULT_CNPJ
        defaultDemandanteJuridicoShouldNotBeFound("cnpj.greaterThan=" + DEFAULT_CNPJ);

        // Get all the demandanteJuridicoList where cnpj is greater than SMALLER_CNPJ
        defaultDemandanteJuridicoShouldBeFound("cnpj.greaterThan=" + SMALLER_CNPJ);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa equals to DEFAULT_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.equals=" + DEFAULT_NOME_DA_EMPRESA);

        // Get all the demandanteJuridicoList where nomeDaEmpresa equals to UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.equals=" + UPDATED_NOME_DA_EMPRESA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa not equals to DEFAULT_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.notEquals=" + DEFAULT_NOME_DA_EMPRESA);

        // Get all the demandanteJuridicoList where nomeDaEmpresa not equals to UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.notEquals=" + UPDATED_NOME_DA_EMPRESA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa in DEFAULT_NOME_DA_EMPRESA or UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.in=" + DEFAULT_NOME_DA_EMPRESA + "," + UPDATED_NOME_DA_EMPRESA);

        // Get all the demandanteJuridicoList where nomeDaEmpresa equals to UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.in=" + UPDATED_NOME_DA_EMPRESA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa is not null
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.specified=true");

        // Get all the demandanteJuridicoList where nomeDaEmpresa is null
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa contains DEFAULT_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.contains=" + DEFAULT_NOME_DA_EMPRESA);

        // Get all the demandanteJuridicoList where nomeDaEmpresa contains UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.contains=" + UPDATED_NOME_DA_EMPRESA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomeDaEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomeDaEmpresa does not contain DEFAULT_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldNotBeFound("nomeDaEmpresa.doesNotContain=" + DEFAULT_NOME_DA_EMPRESA);

        // Get all the demandanteJuridicoList where nomeDaEmpresa does not contain UPDATED_NOME_DA_EMPRESA
        defaultDemandanteJuridicoShouldBeFound("nomeDaEmpresa.doesNotContain=" + UPDATED_NOME_DA_EMPRESA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia equals to DEFAULT_NOMEFANTASIA
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.equals=" + DEFAULT_NOMEFANTASIA);

        // Get all the demandanteJuridicoList where nomefantasia equals to UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.equals=" + UPDATED_NOMEFANTASIA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia not equals to DEFAULT_NOMEFANTASIA
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.notEquals=" + DEFAULT_NOMEFANTASIA);

        // Get all the demandanteJuridicoList where nomefantasia not equals to UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.notEquals=" + UPDATED_NOMEFANTASIA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia in DEFAULT_NOMEFANTASIA or UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.in=" + DEFAULT_NOMEFANTASIA + "," + UPDATED_NOMEFANTASIA);

        // Get all the demandanteJuridicoList where nomefantasia equals to UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.in=" + UPDATED_NOMEFANTASIA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia is not null
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.specified=true");

        // Get all the demandanteJuridicoList where nomefantasia is null
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia contains DEFAULT_NOMEFANTASIA
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.contains=" + DEFAULT_NOMEFANTASIA);

        // Get all the demandanteJuridicoList where nomefantasia contains UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.contains=" + UPDATED_NOMEFANTASIA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByNomefantasiaNotContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where nomefantasia does not contain DEFAULT_NOMEFANTASIA
        defaultDemandanteJuridicoShouldNotBeFound("nomefantasia.doesNotContain=" + DEFAULT_NOMEFANTASIA);

        // Get all the demandanteJuridicoList where nomefantasia does not contain UPDATED_NOMEFANTASIA
        defaultDemandanteJuridicoShouldBeFound("nomefantasia.doesNotContain=" + UPDATED_NOMEFANTASIA);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email equals to DEFAULT_EMAIL
        defaultDemandanteJuridicoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the demandanteJuridicoList where email equals to UPDATED_EMAIL
        defaultDemandanteJuridicoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email not equals to DEFAULT_EMAIL
        defaultDemandanteJuridicoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the demandanteJuridicoList where email not equals to UPDATED_EMAIL
        defaultDemandanteJuridicoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDemandanteJuridicoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the demandanteJuridicoList where email equals to UPDATED_EMAIL
        defaultDemandanteJuridicoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email is not null
        defaultDemandanteJuridicoShouldBeFound("email.specified=true");

        // Get all the demandanteJuridicoList where email is null
        defaultDemandanteJuridicoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email contains DEFAULT_EMAIL
        defaultDemandanteJuridicoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the demandanteJuridicoList where email contains UPDATED_EMAIL
        defaultDemandanteJuridicoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where email does not contain DEFAULT_EMAIL
        defaultDemandanteJuridicoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the demandanteJuridicoList where email does not contain UPDATED_EMAIL
        defaultDemandanteJuridicoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone equals to DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone equals to UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone not equals to DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone not equals to UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the demandanteJuridicoList where telefone equals to UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone is not null
        defaultDemandanteJuridicoShouldBeFound("telefone.specified=true");

        // Get all the demandanteJuridicoList where telefone is null
        defaultDemandanteJuridicoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone is greater than or equal to DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.greaterThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone is greater than or equal to UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.greaterThanOrEqual=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone is less than or equal to DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.lessThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone is less than or equal to SMALLER_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.lessThanOrEqual=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsLessThanSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone is less than DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.lessThan=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone is less than UPDATED_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.lessThan=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByTelefoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        // Get all the demandanteJuridicoList where telefone is greater than DEFAULT_TELEFONE
        defaultDemandanteJuridicoShouldNotBeFound("telefone.greaterThan=" + DEFAULT_TELEFONE);

        // Get all the demandanteJuridicoList where telefone is greater than SMALLER_TELEFONE
        defaultDemandanteJuridicoShouldBeFound("telefone.greaterThan=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllDemandanteJuridicosByDemandaIsEqualToSomething() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);
        DemandaJuridica demanda = DemandaJuridicaResourceIT.createEntity(em);
        em.persist(demanda);
        em.flush();
        demandanteJuridico.setDemanda(demanda);
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);
        Long demandaId = demanda.getId();

        // Get all the demandanteJuridicoList where demanda equals to demandaId
        defaultDemandanteJuridicoShouldBeFound("demandaId.equals=" + demandaId);

        // Get all the demandanteJuridicoList where demanda equals to (demandaId + 1)
        defaultDemandanteJuridicoShouldNotBeFound("demandaId.equals=" + (demandaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDemandanteJuridicoShouldBeFound(String filter) throws Exception {
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandanteJuridico.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.intValue())))
            .andExpect(jsonPath("$.[*].nomeDaEmpresa").value(hasItem(DEFAULT_NOME_DA_EMPRESA)))
            .andExpect(jsonPath("$.[*].nomefantasia").value(hasItem(DEFAULT_NOMEFANTASIA)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));

        // Check, that the count call also returns 1
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDemandanteJuridicoShouldNotBeFound(String filter) throws Exception {
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDemandanteJuridicoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDemandanteJuridico() throws Exception {
        // Get the demandanteJuridico
        restDemandanteJuridicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandanteJuridico() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();

        // Update the demandanteJuridico
        DemandanteJuridico updatedDemandanteJuridico = demandanteJuridicoRepository.findById(demandanteJuridico.getId()).get();
        // Disconnect from session so that the updates on updatedDemandanteJuridico are not directly saved in db
        em.detach(updatedDemandanteJuridico);
        updatedDemandanteJuridico
            .cnpj(UPDATED_CNPJ)
            .nomeDaEmpresa(UPDATED_NOME_DA_EMPRESA)
            .nomefantasia(UPDATED_NOMEFANTASIA)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(updatedDemandanteJuridico);

        restDemandanteJuridicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandanteJuridicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteJuridico testDemandanteJuridico = demandanteJuridicoList.get(demandanteJuridicoList.size() - 1);
        assertThat(testDemandanteJuridico.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testDemandanteJuridico.getNomeDaEmpresa()).isEqualTo(UPDATED_NOME_DA_EMPRESA);
        assertThat(testDemandanteJuridico.getNomefantasia()).isEqualTo(UPDATED_NOMEFANTASIA);
        assertThat(testDemandanteJuridico.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandanteJuridico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandanteJuridicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandanteJuridicoWithPatch() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();

        // Update the demandanteJuridico using partial update
        DemandanteJuridico partialUpdatedDemandanteJuridico = new DemandanteJuridico();
        partialUpdatedDemandanteJuridico.setId(demandanteJuridico.getId());

        partialUpdatedDemandanteJuridico
            .cnpj(UPDATED_CNPJ)
            .nomeDaEmpresa(UPDATED_NOME_DA_EMPRESA)
            .nomefantasia(UPDATED_NOMEFANTASIA)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);

        restDemandanteJuridicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandanteJuridico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandanteJuridico))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteJuridico testDemandanteJuridico = demandanteJuridicoList.get(demandanteJuridicoList.size() - 1);
        assertThat(testDemandanteJuridico.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testDemandanteJuridico.getNomeDaEmpresa()).isEqualTo(UPDATED_NOME_DA_EMPRESA);
        assertThat(testDemandanteJuridico.getNomefantasia()).isEqualTo(UPDATED_NOMEFANTASIA);
        assertThat(testDemandanteJuridico.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandanteJuridico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateDemandanteJuridicoWithPatch() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();

        // Update the demandanteJuridico using partial update
        DemandanteJuridico partialUpdatedDemandanteJuridico = new DemandanteJuridico();
        partialUpdatedDemandanteJuridico.setId(demandanteJuridico.getId());

        partialUpdatedDemandanteJuridico
            .cnpj(UPDATED_CNPJ)
            .nomeDaEmpresa(UPDATED_NOME_DA_EMPRESA)
            .nomefantasia(UPDATED_NOMEFANTASIA)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);

        restDemandanteJuridicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandanteJuridico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandanteJuridico))
            )
            .andExpect(status().isOk());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
        DemandanteJuridico testDemandanteJuridico = demandanteJuridicoList.get(demandanteJuridicoList.size() - 1);
        assertThat(testDemandanteJuridico.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testDemandanteJuridico.getNomeDaEmpresa()).isEqualTo(UPDATED_NOME_DA_EMPRESA);
        assertThat(testDemandanteJuridico.getNomefantasia()).isEqualTo(UPDATED_NOMEFANTASIA);
        assertThat(testDemandanteJuridico.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandanteJuridico.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandanteJuridicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandanteJuridico() throws Exception {
        int databaseSizeBeforeUpdate = demandanteJuridicoRepository.findAll().size();
        demandanteJuridico.setId(count.incrementAndGet());

        // Create the DemandanteJuridico
        DemandanteJuridicoDTO demandanteJuridicoDTO = demandanteJuridicoMapper.toDto(demandanteJuridico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandanteJuridicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandanteJuridicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandanteJuridico in the database
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandanteJuridico() throws Exception {
        // Initialize the database
        demandanteJuridicoRepository.saveAndFlush(demandanteJuridico);

        int databaseSizeBeforeDelete = demandanteJuridicoRepository.findAll().size();

        // Delete the demandanteJuridico
        restDemandanteJuridicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandanteJuridico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandanteJuridico> demandanteJuridicoList = demandanteJuridicoRepository.findAll();
        assertThat(demandanteJuridicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
