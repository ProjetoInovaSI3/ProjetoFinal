package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.DemandanteFisico;
import com.mycompany.myapp.repository.DemandanteFisicoRepository;
import com.mycompany.myapp.service.criteria.DemandanteFisicoCriteria;
import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteFisicoMapper;
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
 * Service for executing complex queries for {@link DemandanteFisico} entities in the database.
 * The main input is a {@link DemandanteFisicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DemandanteFisicoDTO} or a {@link Page} of {@link DemandanteFisicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemandanteFisicoQueryService extends QueryService<DemandanteFisico> {

    private final Logger log = LoggerFactory.getLogger(DemandanteFisicoQueryService.class);

    private final DemandanteFisicoRepository demandanteFisicoRepository;

    private final DemandanteFisicoMapper demandanteFisicoMapper;

    public DemandanteFisicoQueryService(
        DemandanteFisicoRepository demandanteFisicoRepository,
        DemandanteFisicoMapper demandanteFisicoMapper
    ) {
        this.demandanteFisicoRepository = demandanteFisicoRepository;
        this.demandanteFisicoMapper = demandanteFisicoMapper;
    }

    /**
     * Return a {@link List} of {@link DemandanteFisicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandanteFisicoDTO> findByCriteria(DemandanteFisicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandanteFisico> specification = createSpecification(criteria);
        return demandanteFisicoMapper.toDto(demandanteFisicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandanteFisicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandanteFisicoDTO> findByCriteria(DemandanteFisicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandanteFisico> specification = createSpecification(criteria);
        return demandanteFisicoRepository.findAll(specification, page).map(demandanteFisicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandanteFisicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandanteFisico> specification = createSpecification(criteria);
        return demandanteFisicoRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandanteFisicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandanteFisico> createSpecification(DemandanteFisicoCriteria criteria) {
        Specification<DemandanteFisico> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandanteFisico_.id));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCpf(), DemandanteFisico_.cpf));
            }
            if (criteria.getNomeCompleto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeCompleto(), DemandanteFisico_.nomeCompleto));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), DemandanteFisico_.email));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTelefone(), DemandanteFisico_.telefone));
            }
            if (criteria.getDemandaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDemandaId(),
                            root -> root.join(DemandanteFisico_.demanda, JoinType.LEFT).get(DemandaFisica_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
