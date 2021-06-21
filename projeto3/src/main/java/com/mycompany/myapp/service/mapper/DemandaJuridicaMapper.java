package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DemandaJuridicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandaJuridica} and its DTO {@link DemandaJuridicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DemandaJuridicaMapper extends EntityMapper<DemandaJuridicaDTO, DemandaJuridica> {
    @Named("descricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DemandaJuridicaDTO toDtoDescricao(DemandaJuridica demandaJuridica);
}
