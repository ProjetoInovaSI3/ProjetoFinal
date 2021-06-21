package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.DemandanteJuridico;
import com.mycompany.myapp.repository.DemandanteJuridicoRepository;
import com.mycompany.myapp.service.criteria.DemandanteJuridicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteJuridicoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DemandanteJuridico} entities in the database.
 * The main input is a {@link DemandanteJuridicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DemandanteJuridicoDTO} or a {@link Page} of {@link DemandanteJuridicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemandanteJuridicoQueryService extends QueryService<DemandanteJuridico> {

    private final Logger log = LoggerFactory.getLogger(DemandanteJuridicoQueryService.class);

    private final DemandanteJuridicoRepository demandanteJuridicoRepository;

    private final DemandanteJuridicoMapper demandanteJuridicoMapper;

    public DemandanteJuridicoQueryService(
        DemandanteJuridicoRepository demandanteJuridicoRepository,
        DemandanteJuridicoMapper demandanteJuridicoMapper
    ) {
        this.demandanteJuridicoRepository = demandanteJuridicoRepository;
        this.demandanteJuridicoMapper = demandanteJuridicoMapper;
    }

    /**
     * Return a {@link List} of {@link DemandanteJuridicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandanteJuridicoDTO> findByCriteria(DemandanteJuridicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandanteJuridico> specification = createSpecification(criteria);
        return demandanteJuridicoMapper.toDto(demandanteJuridicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandanteJuridicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandanteJuridicoDTO> findByCriteria(DemandanteJuridicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandanteJuridico> specification = createSpecification(criteria);
        return demandanteJuridicoRepository.findAll(specification, page).map(demandanteJuridicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandanteJuridicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandanteJuridico> specification = createSpecification(criteria);
        return demandanteJuridicoRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandanteJuridicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandanteJuridico> createSpecification(DemandanteJuridicoCriteria criteria) {
        Specification<DemandanteJuridico> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandanteJuridico_.id));
            }
            if (criteria.getCnpj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCnpj(), DemandanteJuridico_.cnpj));
            }
            if (criteria.getNomeDaEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeDaEmpresa(), DemandanteJuridico_.nomeDaEmpresa));
            }
            if (criteria.getNomefantasia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomefantasia(), DemandanteJuridico_.nomefantasia));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), DemandanteJuridico_.email));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefone(), DemandanteJuridico_.telefone));
            }
            if (criteria.getDemandaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDemandaId(),
                            root -> root.join(DemandanteJuridico_.demanda, JoinType.LEFT).get(DemandaJuridica_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
