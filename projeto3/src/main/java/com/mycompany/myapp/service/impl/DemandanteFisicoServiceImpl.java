package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandanteFisico;
import com.mycompany.myapp.repository.DemandanteFisicoRepository;
import com.mycompany.myapp.service.DemandanteFisicoService;
import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteFisicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandanteFisico}.
 */
@Service
@Transactional
public class DemandanteFisicoServiceImpl implements DemandanteFisicoService {

    private final Logger log = LoggerFactory.getLogger(DemandanteFisicoServiceImpl.class);

    private final DemandanteFisicoRepository demandanteFisicoRepository;

    private final DemandanteFisicoMapper demandanteFisicoMapper;

    public DemandanteFisicoServiceImpl(
        DemandanteFisicoRepository demandanteFisicoRepository,
        DemandanteFisicoMapper demandanteFisicoMapper
    ) {
        this.demandanteFisicoRepository = demandanteFisicoRepository;
        this.demandanteFisicoMapper = demandanteFisicoMapper;
    }

    @Override
    public DemandanteFisicoDTO save(DemandanteFisicoDTO demandanteFisicoDTO) {
        log.debug("Request to save DemandanteFisico : {}", demandanteFisicoDTO);
        DemandanteFisico demandanteFisico = demandanteFisicoMapper.toEntity(demandanteFisicoDTO);
        demandanteFisico = demandanteFisicoRepository.save(demandanteFisico);
        return demandanteFisicoMapper.toDto(demandanteFisico);
    }

    @Override
    public Optional<DemandanteFisicoDTO> partialUpdate(DemandanteFisicoDTO demandanteFisicoDTO) {
        log.debug("Request to partially update DemandanteFisico : {}", demandanteFisicoDTO);

        return demandanteFisicoRepository
            .findById(demandanteFisicoDTO.getId())
            .map(
                existingDemandanteFisico -> {
                    demandanteFisicoMapper.partialUpdate(existingDemandanteFisico, demandanteFisicoDTO);

                    return existingDemandanteFisico;
                }
            )
            .map(demandanteFisicoRepository::save)
            .map(demandanteFisicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandanteFisicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandanteFisicos");
        return demandanteFisicoRepository.findAll(pageable).map(demandanteFisicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandanteFisicoDTO> findOne(Long id) {
        log.debug("Request to get DemandanteFisico : {}", id);
        return demandanteFisicoRepository.findById(id).map(demandanteFisicoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandanteFisico : {}", id);
        demandanteFisicoRepository.deleteById(id);
    }
}
