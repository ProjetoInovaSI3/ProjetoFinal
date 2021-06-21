package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DemandanteJuridicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandanteJuridico} and its DTO {@link DemandanteJuridicoDTO}.
 */
@Mapper(componentModel = "spring", uses = { DemandaJuridicaMapper.class })
public interface DemandanteJuridicoMapper extends EntityMapper<DemandanteJuridicoDTO, DemandanteJuridico> {
    @Mapping(target = "demanda", source = "demanda", qualifiedByName = "descricao")
    DemandanteJuridicoDTO toDto(DemandanteJuridico s);
}
