package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemandaJuridicaRepository;
import com.mycompany.myapp.service.DemandaJuridicaQueryService;
import com.mycompany.myapp.service.DemandaJuridicaService;
import com.mycompany.myapp.service.criteria.DemandaJuridicaCriteria;
import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandaJuridica}.
 */
@RestController
@RequestMapping("/api")
public class DemandaJuridicaResource {

    private final Logger log = LoggerFactory.getLogger(DemandaJuridicaResource.class);

    private static final String ENTITY_NAME = "demandaJuridica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandaJuridicaService demandaJuridicaService;

    private final DemandaJuridicaRepository demandaJuridicaRepository;

    private final DemandaJuridicaQueryService demandaJuridicaQueryService;

    public DemandaJuridicaResource(
        DemandaJuridicaService demandaJuridicaService,
        DemandaJuridicaRepository demandaJuridicaRepository,
        DemandaJuridicaQueryService demandaJuridicaQueryService
    ) {
        this.demandaJuridicaService = demandaJuridicaService;
        this.demandaJuridicaRepository = demandaJuridicaRepository;
        this.demandaJuridicaQueryService = demandaJuridicaQueryService;
    }

    /**
     * {@code POST  /demanda-juridicas} : Create a new demandaJuridica.
     *
     * @param demandaJuridicaDTO the demandaJuridicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandaJuridicaDTO, or with status {@code 400 (Bad Request)} if the demandaJuridica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demanda-juridicas")
    public ResponseEntity<DemandaJuridicaDTO> createDemandaJuridica(@RequestBody DemandaJuridicaDTO demandaJuridicaDTO)
        throws URISyntaxException {
        log.debug("REST request to save DemandaJuridica : {}", demandaJuridicaDTO);
        if (demandaJuridicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandaJuridica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandaJuridicaDTO result = demandaJuridicaService.save(demandaJuridicaDTO);
        return ResponseEntity
            .created(new URI("/api/demanda-juridicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demanda-juridicas/:id} : Updates an existing demandaJuridica.
     *
     * @param id the id of the demandaJuridicaDTO to save.
     * @param demandaJuridicaDTO the demandaJuridicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandaJuridicaDTO,
     * or with status {@code 400 (Bad Request)} if the demandaJuridicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandaJuridicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demanda-juridicas/{id}")
    public ResponseEntity<DemandaJuridicaDTO> updateDemandaJuridica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandaJuridicaDTO demandaJuridicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandaJuridica : {}, {}", id, demandaJuridicaDTO);
        if (demandaJuridicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandaJuridicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandaJuridicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandaJuridicaDTO result = demandaJuridicaService.save(demandaJuridicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandaJuridicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demanda-juridicas/:id} : Partial updates given fields of an existing demandaJuridica, field will ignore if it is null
     *
     * @param id the id of the demandaJuridicaDTO to save.
     * @param demandaJuridicaDTO the demandaJuridicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandaJuridicaDTO,
     * or with status {@code 400 (Bad Request)} if the demandaJuridicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandaJuridicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandaJuridicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demanda-juridicas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandaJuridicaDTO> partialUpdateDemandaJuridica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandaJuridicaDTO demandaJuridicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandaJuridica partially : {}, {}", id, demandaJuridicaDTO);
        if (demandaJuridicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandaJuridicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandaJuridicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandaJuridicaDTO> result = demandaJuridicaService.partialUpdate(demandaJuridicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandaJuridicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demanda-juridicas} : get all the demandaJuridicas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandaJuridicas in body.
     */
    @GetMapping("/demanda-juridicas")
    public ResponseEntity<List<DemandaJuridicaDTO>> getAllDemandaJuridicas(DemandaJuridicaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DemandaJuridicas by criteria: {}", criteria);
        Page<DemandaJuridicaDTO> page = demandaJuridicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demanda-juridicas/count} : count all the demandaJuridicas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/demanda-juridicas/count")
    public ResponseEntity<Long> countDemandaJuridicas(DemandaJuridicaCriteria criteria) {
        log.debug("REST request to count DemandaJuridicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandaJuridicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demanda-juridicas/:id} : get the "id" demandaJuridica.
     *
     * @param id the id of the demandaJuridicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandaJuridicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demanda-juridicas/{id}")
    public ResponseEntity<DemandaJuridicaDTO> getDemandaJuridica(@PathVariable Long id) {
        log.debug("REST request to get DemandaJuridica : {}", id);
        Optional<DemandaJuridicaDTO> demandaJuridicaDTO = demandaJuridicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandaJuridicaDTO);
    }

    /**
     * {@code DELETE  /demanda-juridicas/:id} : delete the "id" demandaJuridica.
     *
     * @param id the id of the demandaJuridicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demanda-juridicas/{id}")
    public ResponseEntity<Void> deleteDemandaJuridica(@PathVariable Long id) {
        log.debug("REST request to delete DemandaJuridica : {}", id);
        demandaJuridicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
