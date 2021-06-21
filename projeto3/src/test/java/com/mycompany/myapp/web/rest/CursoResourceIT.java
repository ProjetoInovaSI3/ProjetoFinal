package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Curso;
import com.mycompany.myapp.domain.DemandaFisica;
import com.mycompany.myapp.domain.DemandaJuridica;
import com.mycompany.myapp.domain.Professor;
import com.mycompany.myapp.repository.CursoRepository;
import com.mycompany.myapp.service.criteria.CursoCriteria;
import com.mycompany.myapp.service.dto.CursoDTO;
import com.mycompany.myapp.service.mapper.CursoMapper;
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
 * Integration tests for the {@link CursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CursoResourceIT {

    private static final String DEFAULT_NOME_DO_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_CURSO = "BBBBBBBBBB";

    private static final String DEFAULT_TURMA = "AAAAAAAAAA";
    private static final String UPDATED_TURMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoMapper cursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCursoMockMvc;

    private Curso curso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Curso createEntity(EntityManager em) {
        Curso curso = new Curso().nomeDoCurso(DEFAULT_NOME_DO_CURSO).turma(DEFAULT_TURMA);
        return curso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Curso createUpdatedEntity(EntityManager em) {
        Curso curso = new Curso().nomeDoCurso(UPDATED_NOME_DO_CURSO).turma(UPDATED_TURMA);
        return curso;
    }

    @BeforeEach
    public void initTest() {
        curso = createEntity(em);
    }

    @Test
    @Transactional
    void createCurso() throws Exception {
        int databaseSizeBeforeCreate = cursoRepository.findAll().size();
        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);
        restCursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cursoDTO)))
            .andExpect(status().isCreated());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeCreate + 1);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getNomeDoCurso()).isEqualTo(DEFAULT_NOME_DO_CURSO);
        assertThat(testCurso.getTurma()).isEqualTo(DEFAULT_TURMA);
    }

    @Test
    @Transactional
    void createCursoWithExistingId() throws Exception {
        // Create the Curso with an existing ID
        curso.setId(1L);
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        int databaseSizeBeforeCreate = cursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCursos() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList
        restCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoCurso").value(hasItem(DEFAULT_NOME_DO_CURSO)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)));
    }

    @Test
    @Transactional
    void getCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get the curso
        restCursoMockMvc
            .perform(get(ENTITY_API_URL_ID, curso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(curso.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoCurso").value(DEFAULT_NOME_DO_CURSO))
            .andExpect(jsonPath("$.turma").value(DEFAULT_TURMA));
    }

    @Test
    @Transactional
    void getCursosByIdFiltering() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        Long id = curso.getId();

        defaultCursoShouldBeFound("id.equals=" + id);
        defaultCursoShouldNotBeFound("id.notEquals=" + id);

        defaultCursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCursoShouldNotBeFound("id.greaterThan=" + id);

        defaultCursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso equals to DEFAULT_NOME_DO_CURSO
        defaultCursoShouldBeFound("nomeDoCurso.equals=" + DEFAULT_NOME_DO_CURSO);

        // Get all the cursoList where nomeDoCurso equals to UPDATED_NOME_DO_CURSO
        defaultCursoShouldNotBeFound("nomeDoCurso.equals=" + UPDATED_NOME_DO_CURSO);
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso not equals to DEFAULT_NOME_DO_CURSO
        defaultCursoShouldNotBeFound("nomeDoCurso.notEquals=" + DEFAULT_NOME_DO_CURSO);

        // Get all the cursoList where nomeDoCurso not equals to UPDATED_NOME_DO_CURSO
        defaultCursoShouldBeFound("nomeDoCurso.notEquals=" + UPDATED_NOME_DO_CURSO);
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoIsInShouldWork() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso in DEFAULT_NOME_DO_CURSO or UPDATED_NOME_DO_CURSO
        defaultCursoShouldBeFound("nomeDoCurso.in=" + DEFAULT_NOME_DO_CURSO + "," + UPDATED_NOME_DO_CURSO);

        // Get all the cursoList where nomeDoCurso equals to UPDATED_NOME_DO_CURSO
        defaultCursoShouldNotBeFound("nomeDoCurso.in=" + UPDATED_NOME_DO_CURSO);
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso is not null
        defaultCursoShouldBeFound("nomeDoCurso.specified=true");

        // Get all the cursoList where nomeDoCurso is null
        defaultCursoShouldNotBeFound("nomeDoCurso.specified=false");
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoContainsSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso contains DEFAULT_NOME_DO_CURSO
        defaultCursoShouldBeFound("nomeDoCurso.contains=" + DEFAULT_NOME_DO_CURSO);

        // Get all the cursoList where nomeDoCurso contains UPDATED_NOME_DO_CURSO
        defaultCursoShouldNotBeFound("nomeDoCurso.contains=" + UPDATED_NOME_DO_CURSO);
    }

    @Test
    @Transactional
    void getAllCursosByNomeDoCursoNotContainsSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where nomeDoCurso does not contain DEFAULT_NOME_DO_CURSO
        defaultCursoShouldNotBeFound("nomeDoCurso.doesNotContain=" + DEFAULT_NOME_DO_CURSO);

        // Get all the cursoList where nomeDoCurso does not contain UPDATED_NOME_DO_CURSO
        defaultCursoShouldBeFound("nomeDoCurso.doesNotContain=" + UPDATED_NOME_DO_CURSO);
    }

    @Test
    @Transactional
    void getAllCursosByTurmaIsEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma equals to DEFAULT_TURMA
        defaultCursoShouldBeFound("turma.equals=" + DEFAULT_TURMA);

        // Get all the cursoList where turma equals to UPDATED_TURMA
        defaultCursoShouldNotBeFound("turma.equals=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    void getAllCursosByTurmaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma not equals to DEFAULT_TURMA
        defaultCursoShouldNotBeFound("turma.notEquals=" + DEFAULT_TURMA);

        // Get all the cursoList where turma not equals to UPDATED_TURMA
        defaultCursoShouldBeFound("turma.notEquals=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    void getAllCursosByTurmaIsInShouldWork() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma in DEFAULT_TURMA or UPDATED_TURMA
        defaultCursoShouldBeFound("turma.in=" + DEFAULT_TURMA + "," + UPDATED_TURMA);

        // Get all the cursoList where turma equals to UPDATED_TURMA
        defaultCursoShouldNotBeFound("turma.in=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    void getAllCursosByTurmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma is not null
        defaultCursoShouldBeFound("turma.specified=true");

        // Get all the cursoList where turma is null
        defaultCursoShouldNotBeFound("turma.specified=false");
    }

    @Test
    @Transactional
    void getAllCursosByTurmaContainsSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma contains DEFAULT_TURMA
        defaultCursoShouldBeFound("turma.contains=" + DEFAULT_TURMA);

        // Get all the cursoList where turma contains UPDATED_TURMA
        defaultCursoShouldNotBeFound("turma.contains=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    void getAllCursosByTurmaNotContainsSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList where turma does not contain DEFAULT_TURMA
        defaultCursoShouldNotBeFound("turma.doesNotContain=" + DEFAULT_TURMA);

        // Get all the cursoList where turma does not contain UPDATED_TURMA
        defaultCursoShouldBeFound("turma.doesNotContain=" + UPDATED_TURMA);
    }

    @Test
    @Transactional
    void getAllCursosByDemandaFisicaIsEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);
        DemandaFisica demandaFisica = DemandaFisicaResourceIT.createEntity(em);
        em.persist(demandaFisica);
        em.flush();
        curso.setDemandaFisica(demandaFisica);
        cursoRepository.saveAndFlush(curso);
        Long demandaFisicaId = demandaFisica.getId();

        // Get all the cursoList where demandaFisica equals to demandaFisicaId
        defaultCursoShouldBeFound("demandaFisicaId.equals=" + demandaFisicaId);

        // Get all the cursoList where demandaFisica equals to (demandaFisicaId + 1)
        defaultCursoShouldNotBeFound("demandaFisicaId.equals=" + (demandaFisicaId + 1));
    }

    @Test
    @Transactional
    void getAllCursosByProfessorIsEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);
        Professor professor = ProfessorResourceIT.createEntity(em);
        em.persist(professor);
        em.flush();
        curso.setProfessor(professor);
        cursoRepository.saveAndFlush(curso);
        Long professorId = professor.getId();

        // Get all the cursoList where professor equals to professorId
        defaultCursoShouldBeFound("professorId.equals=" + professorId);

        // Get all the cursoList where professor equals to (professorId + 1)
        defaultCursoShouldNotBeFound("professorId.equals=" + (professorId + 1));
    }

    @Test
    @Transactional
    void getAllCursosByDemandaJuridicaIsEqualToSomething() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);
        DemandaJuridica demandaJuridica = DemandaJuridicaResourceIT.createEntity(em);
        em.persist(demandaJuridica);
        em.flush();
        curso.setDemandaJuridica(demandaJuridica);
        cursoRepository.saveAndFlush(curso);
        Long demandaJuridicaId = demandaJuridica.getId();

        // Get all the cursoList where demandaJuridica equals to demandaJuridicaId
        defaultCursoShouldBeFound("demandaJuridicaId.equals=" + demandaJuridicaId);

        // Get all the cursoList where demandaJuridica equals to (demandaJuridicaId + 1)
        defaultCursoShouldNotBeFound("demandaJuridicaId.equals=" + (demandaJuridicaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCursoShouldBeFound(String filter) throws Exception {
        restCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoCurso").value(hasItem(DEFAULT_NOME_DO_CURSO)))
            .andExpect(jsonPath("$.[*].turma").value(hasItem(DEFAULT_TURMA)));

        // Check, that the count call also returns 1
        restCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCursoShouldNotBeFound(String filter) throws Exception {
        restCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCurso() throws Exception {
        // Get the curso
        restCursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();

        // Update the curso
        Curso updatedCurso = cursoRepository.findById(curso.getId()).get();
        // Disconnect from session so that the updates on updatedCurso are not directly saved in db
        em.detach(updatedCurso);
        updatedCurso.nomeDoCurso(UPDATED_NOME_DO_CURSO).turma(UPDATED_TURMA);
        CursoDTO cursoDTO = cursoMapper.toDto(updatedCurso);

        restCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getNomeDoCurso()).isEqualTo(UPDATED_NOME_DO_CURSO);
        assertThat(testCurso.getTurma()).isEqualTo(UPDATED_TURMA);
    }

    @Test
    @Transactional
    void putNonExistingCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCursoWithPatch() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();

        // Update the curso using partial update
        Curso partialUpdatedCurso = new Curso();
        partialUpdatedCurso.setId(curso.getId());

        partialUpdatedCurso.nomeDoCurso(UPDATED_NOME_DO_CURSO).turma(UPDATED_TURMA);

        restCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurso))
            )
            .andExpect(status().isOk());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getNomeDoCurso()).isEqualTo(UPDATED_NOME_DO_CURSO);
        assertThat(testCurso.getTurma()).isEqualTo(UPDATED_TURMA);
    }

    @Test
    @Transactional
    void fullUpdateCursoWithPatch() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();

        // Update the curso using partial update
        Curso partialUpdatedCurso = new Curso();
        partialUpdatedCurso.setId(curso.getId());

        partialUpdatedCurso.nomeDoCurso(UPDATED_NOME_DO_CURSO).turma(UPDATED_TURMA);

        restCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurso))
            )
            .andExpect(status().isOk());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getNomeDoCurso()).isEqualTo(UPDATED_NOME_DO_CURSO);
        assertThat(testCurso.getTurma()).isEqualTo(UPDATED_TURMA);
    }

    @Test
    @Transactional
    void patchNonExistingCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();
        curso.setId(count.incrementAndGet());

        // Create the Curso
        CursoDTO cursoDTO = cursoMapper.toDto(curso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeDelete = cursoRepository.findAll().size();

        // Delete the curso
        restCursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, curso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
