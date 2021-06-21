package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandaFisica;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandaFisica entity.
 */
@Repository
public interface DemandaFisicaRepository extends JpaRepository<DemandaFisica, Long>, JpaSpecificationExecutor<DemandaFisica> {
    @Query(
        value = "select distinct demandaFisica from DemandaFisica demandaFisica left join fetch demandaFisica.enderecos",
        countQuery = "select count(distinct demandaFisica) from DemandaFisica demandaFisica"
    )
    Page<DemandaFisica> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct demandaFisica from DemandaFisica demandaFisica left join fetch demandaFisica.enderecos")
    List<DemandaFisica> findAllWithEagerRelationships();

    @Query("select demandaFisica from DemandaFisica demandaFisica left join fetch demandaFisica.enderecos where demandaFisica.id =:id")
    Optional<DemandaFisica> findOneWithEagerRelationships(@Param("id") Long id);
}
