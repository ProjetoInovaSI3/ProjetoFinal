package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.DemandaJuridica;
import com.mycompany.myapp.repository.DemandaJuridicaRepository;
import com.mycompany.myapp.service.criteria.DemandaJuridicaCriteria;
import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
import com.mycompany.myapp.service.mapper.DemandaJuridicaMapper;
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
 * Service for executing complex queries for {@link DemandaJuridica} entities in the database.
 * The main input is a {@link DemandaJuridicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DemandaJuridicaDTO} or a {@link Page} of {@link DemandaJuridicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemandaJuridicaQueryService extends QueryService<DemandaJuridica> {

    private final Logger log = LoggerFactory.getLogger(DemandaJuridicaQueryService.class);

    private final DemandaJuridicaRepository demandaJuridicaRepository;

    private final DemandaJuridicaMapper demandaJuridicaMapper;

    public DemandaJuridicaQueryService(DemandaJuridicaRepository demandaJuridicaRepository, DemandaJuridicaMapper demandaJuridicaMapper) {
        this.demandaJuridicaRepository = demandaJuridicaRepository;
        this.demandaJuridicaMapper = demandaJuridicaMapper;
    }

    /**
     * Return a {@link List} of {@link DemandaJuridicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandaJuridicaDTO> findByCriteria(DemandaJuridicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandaJuridica> specification = createSpecification(criteria);
        return demandaJuridicaMapper.toDto(demandaJuridicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandaJuridicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandaJuridicaDTO> findByCriteria(DemandaJuridicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandaJuridica> specification = createSpecification(criteria);
        return demandaJuridicaRepository.findAll(specification, page).map(demandaJuridicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandaJuridicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandaJuridica> specification = createSpecification(criteria);
        return demandaJuridicaRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandaJuridicaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandaJuridica> createSpecification(DemandaJuridicaCriteria criteria) {
        Specification<DemandaJuridica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandaJuridica_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DemandaJuridica_.descricao));
            }
            if (criteria.getCurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurso(), DemandaJuridica_.curso));
            }
            if (criteria.getCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCursoId(), root -> root.join(DemandaJuridica_.cursos, JoinType.LEFT).get(Curso_.id))
                    );
            }
        }
        return specification;
    }
}
