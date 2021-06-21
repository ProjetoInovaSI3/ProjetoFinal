package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Curso;
import com.mycompany.myapp.domain.Professor;
import com.mycompany.myapp.repository.ProfessorRepository;
import com.mycompany.myapp.service.criteria.ProfessorCriteria;
import com.mycompany.myapp.service.dto.ProfessorDTO;
import com.mycompany.myapp.service.mapper.ProfessorMapper;
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
 * Integration tests for the {@link ProfessorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfessorResourceIT {

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEFONE = 1L;
    private static final Long UPDATED_TELEFONE = 2L;
    private static final Long SMALLER_TELEFONE = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/professors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfessorMockMvc;

    private Professor professor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professor createEntity(EntityManager em) {
        Professor professor = new Professor()
            .curso(DEFAULT_CURSO)
            .nomeCompleto(DEFAULT_NOME_COMPLETO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return professor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professor createUpdatedEntity(EntityManager em) {
        Professor professor = new Professor()
            .curso(UPDATED_CURSO)
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return professor;
    }

    @BeforeEach
    public void initTest() {
        professor = createEntity(em);
    }

    @Test
    @Transactional
    void createProfessor() throws Exception {
        int databaseSizeBeforeCreate = professorRepository.findAll().size();
        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);
        restProfessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(professorDTO)))
            .andExpect(status().isCreated());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeCreate + 1);
        Professor testProfessor = professorList.get(professorList.size() - 1);
        assertThat(testProfessor.getCurso()).isEqualTo(DEFAULT_CURSO);
        assertThat(testProfessor.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testProfessor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfessor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createProfessorWithExistingId() throws Exception {
        // Create the Professor with an existing ID
        professor.setId(1L);
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        int databaseSizeBeforeCreate = professorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(professorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfessors() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professor.getId().intValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));
    }

    @Test
    @Transactional
    void getProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get the professor
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL_ID, professor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professor.getId().intValue()))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO))
            .andExpect(jsonPath("$.nomeCompleto").value(DEFAULT_NOME_COMPLETO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.intValue()));
    }

    @Test
    @Transactional
    void getProfessorsByIdFiltering() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        Long id = professor.getId();

        defaultProfessorShouldBeFound("id.equals=" + id);
        defaultProfessorShouldNotBeFound("id.notEquals=" + id);

        defaultProfessorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProfessorShouldNotBeFound("id.greaterThan=" + id);

        defaultProfessorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProfessorShouldNotBeFound("id.lessThan=" + id);
    }

   
    @Test
    @Transactional
    void getAllProfessorsByCursoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where curso not equals to DEFAULT_CURSO
        defaultProfessorShouldNotBeFound("curso.notEquals=" + DEFAULT_CURSO);

        // Get all the professorList where curso not equals to UPDATED_CURSO
        defaultProfessorShouldBeFound("curso.notEquals=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllProfessorsByCursoIsInShouldWork() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where curso in DEFAULT_CURSO or UPDATED_CURSO
        defaultProfessorShouldBeFound("curso.in=" + DEFAULT_CURSO + "," + UPDATED_CURSO);

        // Get all the professorList where curso equals to UPDATED_CURSO
        defaultProfessorShouldNotBeFound("curso.in=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllProfessorsByCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where curso is not null
        defaultProfessorShouldBeFound("curso.specified=true");

        // Get all the professorList where curso is null
        defaultProfessorShouldNotBeFound("curso.specified=false");
    }

    @Test
    @Transactional
    void getAllProfessorsByCursoContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where curso contains DEFAULT_CURSO
        defaultProfessorShouldBeFound("curso.contains=" + DEFAULT_CURSO);

        // Get all the professorList where curso contains UPDATED_CURSO
        defaultProfessorShouldNotBeFound("curso.contains=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllProfessorsByCursoNotContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where curso does not contain DEFAULT_CURSO
        defaultProfessorShouldNotBeFound("curso.doesNotContain=" + DEFAULT_CURSO);

        // Get all the professorList where curso does not contain UPDATED_CURSO
        defaultProfessorShouldBeFound("curso.doesNotContain=" + UPDATED_CURSO);
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto equals to DEFAULT_NOME_COMPLETO
        defaultProfessorShouldBeFound("nomeCompleto.equals=" + DEFAULT_NOME_COMPLETO);

        // Get all the professorList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultProfessorShouldNotBeFound("nomeCompleto.equals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto not equals to DEFAULT_NOME_COMPLETO
        defaultProfessorShouldNotBeFound("nomeCompleto.notEquals=" + DEFAULT_NOME_COMPLETO);

        // Get all the professorList where nomeCompleto not equals to UPDATED_NOME_COMPLETO
        defaultProfessorShouldBeFound("nomeCompleto.notEquals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto in DEFAULT_NOME_COMPLETO or UPDATED_NOME_COMPLETO
        defaultProfessorShouldBeFound("nomeCompleto.in=" + DEFAULT_NOME_COMPLETO + "," + UPDATED_NOME_COMPLETO);

        // Get all the professorList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultProfessorShouldNotBeFound("nomeCompleto.in=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto is not null
        defaultProfessorShouldBeFound("nomeCompleto.specified=true");

        // Get all the professorList where nomeCompleto is null
        defaultProfessorShouldNotBeFound("nomeCompleto.specified=false");
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto contains DEFAULT_NOME_COMPLETO
        defaultProfessorShouldBeFound("nomeCompleto.contains=" + DEFAULT_NOME_COMPLETO);

        // Get all the professorList where nomeCompleto contains UPDATED_NOME_COMPLETO
        defaultProfessorShouldNotBeFound("nomeCompleto.contains=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllProfessorsByNomeCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where nomeCompleto does not contain DEFAULT_NOME_COMPLETO
        defaultProfessorShouldNotBeFound("nomeCompleto.doesNotContain=" + DEFAULT_NOME_COMPLETO);

        // Get all the professorList where nomeCompleto does not contain UPDATED_NOME_COMPLETO
        defaultProfessorShouldBeFound("nomeCompleto.doesNotContain=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email equals to DEFAULT_EMAIL
        defaultProfessorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the professorList where email equals to UPDATED_EMAIL
        defaultProfessorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email not equals to DEFAULT_EMAIL
        defaultProfessorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the professorList where email not equals to UPDATED_EMAIL
        defaultProfessorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProfessorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the professorList where email equals to UPDATED_EMAIL
        defaultProfessorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email is not null
        defaultProfessorShouldBeFound("email.specified=true");

        // Get all the professorList where email is null
        defaultProfessorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email contains DEFAULT_EMAIL
        defaultProfessorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the professorList where email contains UPDATED_EMAIL
        defaultProfessorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfessorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where email does not contain DEFAULT_EMAIL
        defaultProfessorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the professorList where email does not contain UPDATED_EMAIL
        defaultProfessorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone equals to DEFAULT_TELEFONE
        defaultProfessorShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone equals to UPDATED_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone not equals to DEFAULT_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone not equals to UPDATED_TELEFONE
        defaultProfessorShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultProfessorShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the professorList where telefone equals to UPDATED_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone is not null
        defaultProfessorShouldBeFound("telefone.specified=true");

        // Get all the professorList where telefone is null
        defaultProfessorShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone is greater than or equal to DEFAULT_TELEFONE
        defaultProfessorShouldBeFound("telefone.greaterThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone is greater than or equal to UPDATED_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.greaterThanOrEqual=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone is less than or equal to DEFAULT_TELEFONE
        defaultProfessorShouldBeFound("telefone.lessThanOrEqual=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone is less than or equal to SMALLER_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.lessThanOrEqual=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsLessThanSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone is less than DEFAULT_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.lessThan=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone is less than UPDATED_TELEFONE
        defaultProfessorShouldBeFound("telefone.lessThan=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByTelefoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        // Get all the professorList where telefone is greater than DEFAULT_TELEFONE
        defaultProfessorShouldNotBeFound("telefone.greaterThan=" + DEFAULT_TELEFONE);

        // Get all the professorList where telefone is greater than SMALLER_TELEFONE
        defaultProfessorShouldBeFound("telefone.greaterThan=" + SMALLER_TELEFONE);
    }

    @Test
    @Transactional
    void getAllProfessorsByCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);
        Curso curso = CursoResourceIT.createEntity(em);
        em.persist(curso);
        em.flush();
        professor.addCurso(curso);
        professorRepository.saveAndFlush(professor);
        Long cursoId = curso.getId();

        // Get all the professorList where curso equals to cursoId
        defaultProfessorShouldBeFound("cursoId.equals=" + cursoId);

        // Get all the professorList where curso equals to (cursoId + 1)
        defaultProfessorShouldNotBeFound("cursoId.equals=" + (cursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfessorShouldBeFound(String filter) throws Exception {
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professor.getId().intValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.intValue())));

        // Check, that the count call also returns 1
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfessorShouldNotBeFound(String filter) throws Exception {
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProfessor() throws Exception {
        // Get the professor
        restProfessorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        int databaseSizeBeforeUpdate = professorRepository.findAll().size();

        // Update the professor
        Professor updatedProfessor = professorRepository.findById(professor.getId()).get();
        // Disconnect from session so that the updates on updatedProfessor are not directly saved in db
        em.detach(updatedProfessor);
        updatedProfessor.curso(UPDATED_CURSO).nomeCompleto(UPDATED_NOME_COMPLETO).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);
        ProfessorDTO professorDTO = professorMapper.toDto(updatedProfessor);

        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, professorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
        Professor testProfessor = professorList.get(professorList.size() - 1);
        assertThat(testProfessor.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testProfessor.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testProfessor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfessor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, professorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(professorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfessorWithPatch() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        int databaseSizeBeforeUpdate = professorRepository.findAll().size();

        // Update the professor using partial update
        Professor partialUpdatedProfessor = new Professor();
        partialUpdatedProfessor.setId(professor.getId());

        partialUpdatedProfessor.curso(UPDATED_CURSO).nomeCompleto(UPDATED_NOME_COMPLETO).telefone(UPDATED_TELEFONE);

        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfessor))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
        Professor testProfessor = professorList.get(professorList.size() - 1);
        assertThat(testProfessor.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testProfessor.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testProfessor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfessor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateProfessorWithPatch() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        int databaseSizeBeforeUpdate = professorRepository.findAll().size();

        // Update the professor using partial update
        Professor partialUpdatedProfessor = new Professor();
        partialUpdatedProfessor.setId(professor.getId());

        partialUpdatedProfessor.curso(UPDATED_CURSO).nomeCompleto(UPDATED_NOME_COMPLETO).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);

        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfessor))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
        Professor testProfessor = professorList.get(professorList.size() - 1);
        assertThat(testProfessor.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testProfessor.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testProfessor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfessor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, professorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfessor() throws Exception {
        int databaseSizeBeforeUpdate = professorRepository.findAll().size();
        professor.setId(count.incrementAndGet());

        // Create the Professor
        ProfessorDTO professorDTO = professorMapper.toDto(professor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(professorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professor in the database
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfessor() throws Exception {
        // Initialize the database
        professorRepository.saveAndFlush(professor);

        int databaseSizeBeforeDelete = professorRepository.findAll().size();

        // Delete the professor
        restProfessorMockMvc
            .perform(delete(ENTITY_API_URL_ID, professor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Professor> professorList = professorRepository.findAll();
        assertThat(professorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
