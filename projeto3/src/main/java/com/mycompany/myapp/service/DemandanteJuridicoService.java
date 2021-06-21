package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DemandanteJuridico}.
 */
public interface DemandanteJuridicoService {
    /**
     * Save a demandanteJuridico.
     *
     * @param demandanteJuridicoDTO the entity to save.
     * @return the persisted entity.
     */
    DemandanteJuridicoDTO save(DemandanteJuridicoDTO demandanteJuridicoDTO);

    /**
     * Partially updates a demandanteJuridico.
     *
     * @param demandanteJuridicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandanteJuridicoDTO> partialUpdate(DemandanteJuridicoDTO demandanteJuridicoDTO);

    /**
     * Get all the demandanteJuridicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandanteJuridicoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandanteJuridico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandanteJuridicoDTO> findOne(Long id);

    /**
     * Delete the "id" demandanteJuridico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
