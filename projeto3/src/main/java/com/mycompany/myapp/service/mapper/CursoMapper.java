package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring", uses = { DemandaFisicaMapper.class, ProfessorMapper.class, DemandaJuridicaMapper.class })
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {
    @Mapping(target = "demandaFisica", source = "demandaFisica", qualifiedByName = "descricao")
    @Mapping(target = "professor", source = "professor", qualifiedByName = "nomeCompleto")
    @Mapping(target = "demandaJuridica", source = "demandaJuridica", qualifiedByName = "descricao")
    CursoDTO toDto(Curso s);
}
