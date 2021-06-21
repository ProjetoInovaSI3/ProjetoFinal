package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.DemandaFisica;
import com.mycompany.myapp.repository.DemandaFisicaRepository;
import com.mycompany.myapp.service.criteria.DemandaFisicaCriteria;
import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
import com.mycompany.myapp.service.mapper.DemandaFisicaMapper;
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
 * Service for executing complex queries for {@link DemandaFisica} entities in the database.
 * The main input is a {@link DemandaFisicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DemandaFisicaDTO} or a {@link Page} of {@link DemandaFisicaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemandaFisicaQueryService extends QueryService<DemandaFisica> {

    private final Logger log = LoggerFactory.getLogger(DemandaFisicaQueryService.class);

    private final DemandaFisicaRepository demandaFisicaRepository;

    private final DemandaFisicaMapper demandaFisicaMapper;

    public DemandaFisicaQueryService(DemandaFisicaRepository demandaFisicaRepository, DemandaFisicaMapper demandaFisicaMapper) {
        this.demandaFisicaRepository = demandaFisicaRepository;
        this.demandaFisicaMapper = demandaFisicaMapper;
    }

    /**
     * Return a {@link List} of {@link DemandaFisicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandaFisicaDTO> findByCriteria(DemandaFisicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandaFisica> specification = createSpecification(criteria);
        return demandaFisicaMapper.toDto(demandaFisicaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandaFisicaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandaFisicaDTO> findByCriteria(DemandaFisicaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandaFisica> specification = createSpecification(criteria);
        return demandaFisicaRepository.findAll(specification, page).map(demandaFisicaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandaFisicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandaFisica> specification = createSpecification(criteria);
        return demandaFisicaRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandaFisicaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandaFisica> createSpecification(DemandaFisicaCriteria criteria) {
        Specification<DemandaFisica> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandaFisica_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DemandaFisica_.descricao));
            }
            if (criteria.getCurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurso(), DemandaFisica_.curso));
            }
            if (criteria.getCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCursoId(), root -> root.join(DemandaFisica_.cursos, JoinType.LEFT).get(Curso_.id))
                    );
            }
            if (criteria.getEnderecoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecoId(),
                            root -> root.join(DemandaFisica_.enderecos, JoinType.LEFT).get(Endereco_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
