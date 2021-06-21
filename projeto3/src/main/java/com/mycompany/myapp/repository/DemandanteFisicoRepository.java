package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandanteFisico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandanteFisico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandanteFisicoRepository extends JpaRepository<DemandanteFisico, Long>, JpaSpecificationExecutor<DemandanteFisico> {}
