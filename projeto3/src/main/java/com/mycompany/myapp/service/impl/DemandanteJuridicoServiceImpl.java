package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandanteJuridico;
import com.mycompany.myapp.repository.DemandanteJuridicoRepository;
import com.mycompany.myapp.service.DemandanteJuridicoService;
import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
import com.mycompany.myapp.service.mapper.DemandanteJuridicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandanteJuridico}.
 */
@Service
@Transactional
public class DemandanteJuridicoServiceImpl implements DemandanteJuridicoService {

    private final Logger log = LoggerFactory.getLogger(DemandanteJuridicoServiceImpl.class);

    private final DemandanteJuridicoRepository demandanteJuridicoRepository;

    private final DemandanteJuridicoMapper demandanteJuridicoMapper;

    public DemandanteJuridicoServiceImpl(
        DemandanteJuridicoRepository demandanteJuridicoRepository,
        DemandanteJuridicoMapper demandanteJuridicoMapper
    ) {
        this.demandanteJuridicoRepository = demandanteJuridicoRepository;
        this.demandanteJuridicoMapper = demandanteJuridicoMapper;
    }

    @Override
    public DemandanteJuridicoDTO save(DemandanteJuridicoDTO demandanteJuridicoDTO) {
        log.debug("Request to save DemandanteJuridico : {}", demandanteJuridicoDTO);
        DemandanteJuridico demandanteJuridico = demandanteJuridicoMapper.toEntity(demandanteJuridicoDTO);
        demandanteJuridico = demandanteJuridicoRepository.save(demandanteJuridico);
        return demandanteJuridicoMapper.toDto(demandanteJuridico);
    }

    @Override
    public Optional<DemandanteJuridicoDTO> partialUpdate(DemandanteJuridicoDTO demandanteJuridicoDTO) {
        log.debug("Request to partially update DemandanteJuridico : {}", demandanteJuridicoDTO);

        return demandanteJuridicoRepository
            .findById(demandanteJuridicoDTO.getId())
            .map(
                existingDemandanteJuridico -> {
                    demandanteJuridicoMapper.partialUpdate(existingDemandanteJuridico, demandanteJuridicoDTO);

                    return existingDemandanteJuridico;
                }
            )
            .map(demandanteJuridicoRepository::save)
            .map(demandanteJuridicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandanteJuridicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandanteJuridicos");
        return demandanteJuridicoRepository.findAll(pageable).map(demandanteJuridicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandanteJuridicoDTO> findOne(Long id) {
        log.debug("Request to get DemandanteJuridico : {}", id);
        return demandanteJuridicoRepository.findById(id).map(demandanteJuridicoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandanteJuridico : {}", id);
        demandanteJuridicoRepository.deleteById(id);
    }
}
