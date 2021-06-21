package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandanteJuridico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandanteJuridico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandanteJuridicoRepository
    extends JpaRepository<DemandanteJuridico, Long>, JpaSpecificationExecutor<DemandanteJuridico> {}
