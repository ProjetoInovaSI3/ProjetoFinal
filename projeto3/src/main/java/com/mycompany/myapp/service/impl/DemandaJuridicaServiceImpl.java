package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandaJuridica;
import com.mycompany.myapp.repository.DemandaJuridicaRepository;
import com.mycompany.myapp.service.DemandaJuridicaService;
import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
import com.mycompany.myapp.service.mapper.DemandaJuridicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandaJuridica}.
 */
@Service
@Transactional
public class DemandaJuridicaServiceImpl implements DemandaJuridicaService {

    private final Logger log = LoggerFactory.getLogger(DemandaJuridicaServiceImpl.class);

    private final DemandaJuridicaRepository demandaJuridicaRepository;

    private final DemandaJuridicaMapper demandaJuridicaMapper;

    public DemandaJuridicaServiceImpl(DemandaJuridicaRepository demandaJuridicaRepository, DemandaJuridicaMapper demandaJuridicaMapper) {
        this.demandaJuridicaRepository = demandaJuridicaRepository;
        this.demandaJuridicaMapper = demandaJuridicaMapper;
    }

    @Override
    public DemandaJuridicaDTO save(DemandaJuridicaDTO demandaJuridicaDTO) {
        log.debug("Request to save DemandaJuridica : {}", demandaJuridicaDTO);
        DemandaJuridica demandaJuridica = demandaJuridicaMapper.toEntity(demandaJuridicaDTO);
        demandaJuridica = demandaJuridicaRepository.save(demandaJuridica);
        return demandaJuridicaMapper.toDto(demandaJuridica);
    }

    @Override
    public Optional<DemandaJuridicaDTO> partialUpdate(DemandaJuridicaDTO demandaJuridicaDTO) {
        log.debug("Request to partially update DemandaJuridica : {}", demandaJuridicaDTO);

        return demandaJuridicaRepository
            .findById(demandaJuridicaDTO.getId())
            .map(
                existingDemandaJuridica -> {
                    demandaJuridicaMapper.partialUpdate(existingDemandaJuridica, demandaJuridicaDTO);

                    return existingDemandaJuridica;
                }
            )
            .map(demandaJuridicaRepository::save)
            .map(demandaJuridicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandaJuridicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandaJuridicas");
        return demandaJuridicaRepository.findAll(pageable).map(demandaJuridicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandaJuridicaDTO> findOne(Long id) {
        log.debug("Request to get DemandaJuridica : {}", id);
        return demandaJuridicaRepository.findById(id).map(demandaJuridicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandaJuridica : {}", id);
        demandaJuridicaRepository.deleteById(id);
    }
}
