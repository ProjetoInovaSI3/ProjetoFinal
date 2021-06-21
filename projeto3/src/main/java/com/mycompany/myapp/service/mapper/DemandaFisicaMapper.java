package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DemandaFisicaDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandaFisica} and its DTO {@link DemandaFisicaDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface DemandaFisicaMapper extends EntityMapper<DemandaFisicaDTO, DemandaFisica> {
    @Mapping(target = "enderecos", source = "enderecos", qualifiedByName = "cepSet")
    DemandaFisicaDTO toDto(DemandaFisica s);

    @Mapping(target = "removeEndereco", ignore = true)
    DemandaFisica toEntity(DemandaFisicaDTO demandaFisicaDTO);

    @Named("descricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DemandaFisicaDTO toDtoDescricao(DemandaFisica demandaFisica);
}
