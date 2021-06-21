package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandaJuridica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandaJuridica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandaJuridicaRepository extends JpaRepository<DemandaJuridica, Long>, JpaSpecificationExecutor<DemandaJuridica> {}
