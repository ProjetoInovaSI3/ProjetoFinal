package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DemandaFisica} entity.
 */
public class DemandaFisicaDTO implements Serializable {

    private Long id;

    private String descricao;

    private String curso;

    private Set<EnderecoDTO> enderecos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandaFisicaDTO)) {
            return false;
        }

        DemandaFisicaDTO demandaFisicaDTO = (DemandaFisicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandaFisicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandaFisicaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", curso='" + getCurso() + "'" +
            ", enderecos=" + getEnderecos() +
            "}";
    }
}
