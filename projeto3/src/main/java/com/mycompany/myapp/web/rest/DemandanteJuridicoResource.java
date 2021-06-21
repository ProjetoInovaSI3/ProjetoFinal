package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemandanteJuridicoRepository;
import com.mycompany.myapp.service.DemandanteJuridicoQueryService;
import com.mycompany.myapp.service.DemandanteJuridicoService;
import com.mycompany.myapp.service.criteria.DemandanteJuridicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandanteJuridico}.
 */
@RestController
@RequestMapping("/api")
public class DemandanteJuridicoResource {

    private final Logger log = LoggerFactory.getLogger(DemandanteJuridicoResource.class);

    private static final String ENTITY_NAME = "demandanteJuridico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandanteJuridicoService demandanteJuridicoService;

    private final DemandanteJuridicoRepository demandanteJuridicoRepository;

    private final DemandanteJuridicoQueryService demandanteJuridicoQueryService;

    public DemandanteJuridicoResource(
        DemandanteJuridicoService demandanteJuridicoService,
        DemandanteJuridicoRepository demandanteJuridicoRepository,
        DemandanteJuridicoQueryService demandanteJuridicoQueryService
    ) {
        this.demandanteJuridicoService = demandanteJuridicoService;
        this.demandanteJuridicoRepository = demandanteJuridicoRepository;
        this.demandanteJuridicoQueryService = demandanteJuridicoQueryService;
    }

    /**
     * {@code POST  /demandante-juridicos} : Create a new demandanteJuridico.
     *
     * @param demandanteJuridicoDTO the demandanteJuridicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandanteJuridicoDTO, or with status {@code 400 (Bad Request)} if the demandanteJuridico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demandante-juridicos")
    public ResponseEntity<DemandanteJuridicoDTO> createDemandanteJuridico(@RequestBody DemandanteJuridicoDTO demandanteJuridicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save DemandanteJuridico : {}", demandanteJuridicoDTO);
        if (demandanteJuridicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandanteJuridico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandanteJuridicoDTO result = demandanteJuridicoService.save(demandanteJuridicoDTO);
        return ResponseEntity
            .created(new URI("/api/demandante-juridicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demandante-juridicos/:id} : Updates an existing demandanteJuridico.
     *
     * @param id the id of the demandanteJuridicoDTO to save.
     * @param demandanteJuridicoDTO the demandanteJuridicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandanteJuridicoDTO,
     * or with status {@code 400 (Bad Request)} if the demandanteJuridicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandanteJuridicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demandante-juridicos/{id}")
    public ResponseEntity<DemandanteJuridicoDTO> updateDemandanteJuridico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandanteJuridicoDTO demandanteJuridicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandanteJuridico : {}, {}", id, demandanteJuridicoDTO);
        if (demandanteJuridicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandanteJuridicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandanteJuridicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandanteJuridicoDTO result = demandanteJuridicoService.save(demandanteJuridicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandanteJuridicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demandante-juridicos/:id} : Partial updates given fields of an existing demandanteJuridico, field will ignore if it is null
     *
     * @param id the id of the demandanteJuridicoDTO to save.
     * @param demandanteJuridicoDTO the demandanteJuridicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandanteJuridicoDTO,
     * or with status {@code 400 (Bad Request)} if the demandanteJuridicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandanteJuridicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandanteJuridicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demandante-juridicos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandanteJuridicoDTO> partialUpdateDemandanteJuridico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandanteJuridicoDTO demandanteJuridicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandanteJuridico partially : {}, {}", id, demandanteJuridicoDTO);
        if (demandanteJuridicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandanteJuridicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandanteJuridicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandanteJuridicoDTO> result = demandanteJuridicoService.partialUpdate(demandanteJuridicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandanteJuridicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demandante-juridicos} : get all the demandanteJuridicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandanteJuridicos in body.
     */
    @GetMapping("/demandante-juridicos")
    public ResponseEntity<List<DemandanteJuridicoDTO>> getAllDemandanteJuridicos(DemandanteJuridicoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DemandanteJuridicos by criteria: {}", criteria);
        Page<DemandanteJuridicoDTO> page = demandanteJuridicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demandante-juridicos/count} : count all the demandanteJuridicos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/demandante-juridicos/count")
    public ResponseEntity<Long> countDemandanteJuridicos(DemandanteJuridicoCriteria criteria) {
        log.debug("REST request to count DemandanteJuridicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandanteJuridicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demandante-juridicos/:id} : get the "id" demandanteJuridico.
     *
     * @param id the id of the demandanteJuridicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandanteJuridicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demandante-juridicos/{id}")
    public ResponseEntity<DemandanteJuridicoDTO> getDemandanteJuridico(@PathVariable Long id) {
        log.debug("REST request to get DemandanteJuridico : {}", id);
        Optional<DemandanteJuridicoDTO> demandanteJuridicoDTO = demandanteJuridicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandanteJuridicoDTO);
    }

    /**
     * {@code DELETE  /demandante-juridicos/:id} : delete the "id" demandanteJuridico.
     *
     * @param id the id of the demandanteJuridicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demandante-juridicos/{id}")
    public ResponseEntity<Void> deleteDemandanteJuridico(@PathVariable Long id) {
        log.debug("REST request to delete DemandanteJuridico : {}", id);
        demandanteJuridicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
