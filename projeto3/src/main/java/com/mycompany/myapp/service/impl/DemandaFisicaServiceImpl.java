package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandaFisica;
import com.mycompany.myapp.repository.DemandaFisicaRepository;
import com.mycompany.myapp.service.DemandaFisicaService;
import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
import com.mycompany.myapp.service.mapper.DemandaFisicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandaFisica}.
 */
@Service
@Transactional
public class DemandaFisicaServiceImpl implements DemandaFisicaService {

    private final Logger log = LoggerFactory.getLogger(DemandaFisicaServiceImpl.class);

    private final DemandaFisicaRepository demandaFisicaRepository;

    private final DemandaFisicaMapper demandaFisicaMapper;

    public DemandaFisicaServiceImpl(DemandaFisicaRepository demandaFisicaRepository, DemandaFisicaMapper demandaFisicaMapper) {
        this.demandaFisicaRepository = demandaFisicaRepository;
        this.demandaFisicaMapper = demandaFisicaMapper;
    }

    @Override
    public DemandaFisicaDTO save(DemandaFisicaDTO demandaFisicaDTO) {
        log.debug("Request to save DemandaFisica : {}", demandaFisicaDTO);
        DemandaFisica demandaFisica = demandaFisicaMapper.toEntity(demandaFisicaDTO);
        demandaFisica = demandaFisicaRepository.save(demandaFisica);
        return demandaFisicaMapper.toDto(demandaFisica);
    }

    @Override
    public Optional<DemandaFisicaDTO> partialUpdate(DemandaFisicaDTO demandaFisicaDTO) {
        log.debug("Request to partially update DemandaFisica : {}", demandaFisicaDTO);

        return demandaFisicaRepository
            .findById(demandaFisicaDTO.getId())
            .map(
                existingDemandaFisica -> {
                    demandaFisicaMapper.partialUpdate(existingDemandaFisica, demandaFisicaDTO);

                    return existingDemandaFisica;
                }
            )
            .map(demandaFisicaRepository::save)
            .map(demandaFisicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandaFisicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandaFisicas");
        return demandaFisicaRepository.findAll(pageable).map(demandaFisicaMapper::toDto);
    }

    public Page<DemandaFisicaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return demandaFisicaRepository.findAllWithEagerRelationships(pageable).map(demandaFisicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandaFisicaDTO> findOne(Long id) {
        log.debug("Request to get DemandaFisica : {}", id);
        return demandaFisicaRepository.findOneWithEagerRelationships(id).map(demandaFisicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandaFisica : {}", id);
        demandaFisicaRepository.deleteById(id);
    }
}
