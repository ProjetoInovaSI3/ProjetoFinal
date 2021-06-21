package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemandanteFisicoRepository;
import com.mycompany.myapp.service.DemandanteFisicoQueryService;
import com.mycompany.myapp.service.DemandanteFisicoService;
import com.mycompany.myapp.service.criteria.DemandanteFisicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandanteFisico}.
 */
@RestController
@RequestMapping("/api")
public class DemandanteFisicoResource {

    private final Logger log = LoggerFactory.getLogger(DemandanteFisicoResource.class);

    private static final String ENTITY_NAME = "demandanteFisico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandanteFisicoService demandanteFisicoService;

    private final DemandanteFisicoRepository demandanteFisicoRepository;

    private final DemandanteFisicoQueryService demandanteFisicoQueryService;

    public DemandanteFisicoResource(
        DemandanteFisicoService demandanteFisicoService,
        DemandanteFisicoRepository demandanteFisicoRepository,
        DemandanteFisicoQueryService demandanteFisicoQueryService
    ) {
        this.demandanteFisicoService = demandanteFisicoService;
        this.demandanteFisicoRepository = demandanteFisicoRepository;
        this.demandanteFisicoQueryService = demandanteFisicoQueryService;
    }

    /**
     * {@code POST  /demandante-fisicos} : Create a new demandanteFisico.
     *
     * @param demandanteFisicoDTO the demandanteFisicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandanteFisicoDTO, or with status {@code 400 (Bad Request)} if the demandanteFisico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demandante-fisicos")
    public ResponseEntity<DemandanteFisicoDTO> createDemandanteFisico(@RequestBody DemandanteFisicoDTO demandanteFisicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save DemandanteFisico : {}", demandanteFisicoDTO);
        if (demandanteFisicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandanteFisico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandanteFisicoDTO result = demandanteFisicoService.save(demandanteFisicoDTO);
        return ResponseEntity
            .created(new URI("/api/demandante-fisicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demandante-fisicos/:id} : Updates an existing demandanteFisico.
     *
     * @param id the id of the demandanteFisicoDTO to save.
     * @param demandanteFisicoDTO the demandanteFisicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandanteFisicoDTO,
     * or with status {@code 400 (Bad Request)} if the demandanteFisicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandanteFisicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demandante-fisicos/{id}")
    public ResponseEntity<DemandanteFisicoDTO> updateDemandanteFisico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandanteFisicoDTO demandanteFisicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandanteFisico : {}, {}", id, demandanteFisicoDTO);
        if (demandanteFisicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandanteFisicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandanteFisicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandanteFisicoDTO result = demandanteFisicoService.save(demandanteFisicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandanteFisicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demandante-fisicos/:id} : Partial updates given fields of an existing demandanteFisico, field will ignore if it is null
     *
     * @param id the id of the demandanteFisicoDTO to save.
     * @param demandanteFisicoDTO the demandanteFisicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandanteFisicoDTO,
     * or with status {@code 400 (Bad Request)} if the demandanteFisicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandanteFisicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandanteFisicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demandante-fisicos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandanteFisicoDTO> partialUpdateDemandanteFisico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandanteFisicoDTO demandanteFisicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandanteFisico partially : {}, {}", id, demandanteFisicoDTO);
        if (demandanteFisicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandanteFisicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandanteFisicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandanteFisicoDTO> result = demandanteFisicoService.partialUpdate(demandanteFisicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandanteFisicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demandante-fisicos} : get all the demandanteFisicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandanteFisicos in body.
     */
    @GetMapping("/demandante-fisicos")
    public ResponseEntity<List<DemandanteFisicoDTO>> getAllDemandanteFisicos(DemandanteFisicoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DemandanteFisicos by criteria: {}", criteria);
        Page<DemandanteFisicoDTO> page = demandanteFisicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demandante-fisicos/count} : count all the demandanteFisicos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/demandante-fisicos/count")
    public ResponseEntity<Long> countDemandanteFisicos(DemandanteFisicoCriteria criteria) {
        log.debug("REST request to count DemandanteFisicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandanteFisicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demandante-fisicos/:id} : get the "id" demandanteFisico.
     *
     * @param id the id of the demandanteFisicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandanteFisicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demandante-fisicos/{id}")
    public ResponseEntity<DemandanteFisicoDTO> getDemandanteFisico(@PathVariable Long id) {
        log.debug("REST request to get DemandanteFisico : {}", id);
        Optional<DemandanteFisicoDTO> demandanteFisicoDTO = demandanteFisicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandanteFisicoDTO);
    }

    /**
     * {@code DELETE  /demandante-fisicos/:id} : delete the "id" demandanteFisico.
     *
     * @param id the id of the demandanteFisicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demandante-fisicos/{id}")
    public ResponseEntity<Void> deleteDemandanteFisico(@PathVariable Long id) {
        log.debug("REST request to delete DemandanteFisico : {}", id);
        demandanteFisicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
