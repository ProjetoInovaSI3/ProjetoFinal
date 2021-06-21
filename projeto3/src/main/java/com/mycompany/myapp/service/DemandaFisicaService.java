package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DemandaFisica}.
 */
public interface DemandaFisicaService {
    /**
     * Save a demandaFisica.
     *
     * @param demandaFisicaDTO the entity to save.
     * @return the persisted entity.
     */
    DemandaFisicaDTO save(DemandaFisicaDTO demandaFisicaDTO);

    /**
     * Partially updates a demandaFisica.
     *
     * @param demandaFisicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandaFisicaDTO> partialUpdate(DemandaFisicaDTO demandaFisicaDTO);

    /**
     * Get all the demandaFisicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandaFisicaDTO> findAll(Pageable pageable);

    /**
     * Get all the demandaFisicas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandaFisicaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" demandaFisica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandaFisicaDTO> findOne(Long id);

    /**
     * Delete the "id" demandaFisica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
