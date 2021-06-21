package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DemandanteFisicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandanteFisico} and its DTO {@link DemandanteFisicoDTO}.
 */
@Mapper(componentModel = "spring", uses = { DemandaFisicaMapper.class })
public interface DemandanteFisicoMapper extends EntityMapper<DemandanteFisicoDTO, DemandanteFisico> {
    @Mapping(target = "demanda", source = "demanda", qualifiedByName = "descricao")
    DemandanteFisicoDTO toDto(DemandanteFisico s);
}
