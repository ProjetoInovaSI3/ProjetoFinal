package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DemandanteFisico}.
 */
public interface DemandanteFisicoService {
    /**
     * Save a demandanteFisico.
     *
     * @param demandanteFisicoDTO the entity to save.
     * @return the persisted entity.
     */
    DemandanteFisicoDTO save(DemandanteFisicoDTO demandanteFisicoDTO);

    /**
     * Partially updates a demandanteFisico.
     *
     * @param demandanteFisicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandanteFisicoDTO> partialUpdate(DemandanteFisicoDTO demandanteFisicoDTO);

    /**
     * Get all the demandanteFisicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandanteFisicoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandanteFisico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandanteFisicoDTO> findOne(Long id);

    /**
     * Delete the "id" demandanteFisico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
