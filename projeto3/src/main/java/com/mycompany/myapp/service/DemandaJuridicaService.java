package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DemandaJuridica}.
 */
public interface DemandaJuridicaService {
    /**
     * Save a demandaJuridica.
     *
     * @param demandaJuridicaDTO the entity to save.
     * @return the persisted entity.
     */
    DemandaJuridicaDTO save(DemandaJuridicaDTO demandaJuridicaDTO);

    /**
     * Partially updates a demandaJuridica.
     *
     * @param demandaJuridicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandaJuridicaDTO> partialUpdate(DemandaJuridicaDTO demandaJuridicaDTO);

    /**
     * Get all the demandaJuridicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandaJuridicaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandaJuridica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandaJuridicaDTO> findOne(Long id);

    /**
     * Delete the "id" demandaJuridica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
