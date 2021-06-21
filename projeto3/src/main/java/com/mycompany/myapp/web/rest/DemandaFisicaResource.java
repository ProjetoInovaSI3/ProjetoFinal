package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemandaFisicaRepository;
import com.mycompany.myapp.service.DemandaFisicaQueryService;
import com.mycompany.myapp.service.DemandaFisicaService;
import com.mycompany.myapp.service.criteria.DemandaFisicaCriteria;
import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandaFisica}.
 */
@RestController
@RequestMapping("/api")
public class DemandaFisicaResource {

    private final Logger log = LoggerFactory.getLogger(DemandaFisicaResource.class);

    private static final String ENTITY_NAME = "demandaFisica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandaFisicaService demandaFisicaService;

    private final DemandaFisicaRepository demandaFisicaRepository;

    private final DemandaFisicaQueryService demandaFisicaQueryService;

    public DemandaFisicaResource(
        DemandaFisicaService demandaFisicaService,
        DemandaFisicaRepository demandaFisicaRepository,
        DemandaFisicaQueryService demandaFisicaQueryService
    ) {
        this.demandaFisicaService = demandaFisicaService;
        this.demandaFisicaRepository = demandaFisicaRepository;
        this.demandaFisicaQueryService = demandaFisicaQueryService;
    }

    /**
     * {@code POST  /demanda-fisicas} : Create a new demandaFisica.
     *
     * @param demandaFisicaDTO the demandaFisicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandaFisicaDTO, or with status {@code 400 (Bad Request)} if the demandaFisica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demanda-fisicas")
    public ResponseEntity<DemandaFisicaDTO> createDemandaFisica(@RequestBody DemandaFisicaDTO demandaFisicaDTO) throws URISyntaxException {
        log.debug("REST request to save DemandaFisica : {}", demandaFisicaDTO);
        if (demandaFisicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandaFisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandaFisicaDTO result = demandaFisicaService.save(demandaFisicaDTO);
        return ResponseEntity
            .created(new URI("/api/demanda-fisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demanda-fisicas/:id} : Updates an existing demandaFisica.
     *
     * @param id the id of the demandaFisicaDTO to save.
     * @param demandaFisicaDTO the demandaFisicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandaFisicaDTO,
     * or with status {@code 400 (Bad Request)} if the demandaFisicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandaFisicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demanda-fisicas/{id}")
    public ResponseEntity<DemandaFisicaDTO> updateDemandaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandaFisicaDTO demandaFisicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandaFisica : {}, {}", id, demandaFisicaDTO);
        if (demandaFisicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandaFisicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandaFisicaDTO result = demandaFisicaService.save(demandaFisicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandaFisicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demanda-fisicas/:id} : Partial updates given fields of an existing demandaFisica, field will ignore if it is null
     *
     * @param id the id of the demandaFisicaDTO to save.
     * @param demandaFisicaDTO the demandaFisicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandaFisicaDTO,
     * or with status {@code 400 (Bad Request)} if the demandaFisicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandaFisicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandaFisicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demanda-fisicas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandaFisicaDTO> partialUpdateDemandaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandaFisicaDTO demandaFisicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandaFisica partially : {}, {}", id, demandaFisicaDTO);
        if (demandaFisicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandaFisicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandaFisicaDTO> result = demandaFisicaService.partialUpdate(demandaFisicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandaFisicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demanda-fisicas} : get all the demandaFisicas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandaFisicas in body.
     */
    @GetMapping("/demanda-fisicas")
    public ResponseEntity<List<DemandaFisicaDTO>> getAllDemandaFisicas(DemandaFisicaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DemandaFisicas by criteria: {}", criteria);
        Page<DemandaFisicaDTO> page = demandaFisicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demanda-fisicas/count} : count all the demandaFisicas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/demanda-fisicas/count")
    public ResponseEntity<Long> countDemandaFisicas(DemandaFisicaCriteria criteria) {
        log.debug("REST request to count DemandaFisicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandaFisicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demanda-fisicas/:id} : get the "id" demandaFisica.
     *
     * @param id the id of the demandaFisicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandaFisicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demanda-fisicas/{id}")
    public ResponseEntity<DemandaFisicaDTO> getDemandaFisica(@PathVariable Long id) {
        log.debug("REST request to get DemandaFisica : {}", id);
        Optional<DemandaFisicaDTO> demandaFisicaDTO = demandaFisicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandaFisicaDTO);
    }

    /**
     * {@code DELETE  /demanda-fisicas/:id} : delete the "id" demandaFisica.
     *
     * @param id the id of the demandaFisicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demanda-fisicas/{id}")
    public ResponseEntity<Void> deleteDemandaFisica(@PathVariable Long id) {
        log.debug("REST request to delete DemandaFisica : {}", id);
        demandaFisicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
