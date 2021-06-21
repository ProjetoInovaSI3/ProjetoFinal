package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DemandaJuridica} entity.
 */
public class DemandaJuridicaDTO implements Serializable {

    private Long id;

    private String descricao;

    private String curso;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandaJuridicaDTO)) {
            return false;
        }

        DemandaJuridicaDTO demandaJuridicaDTO = (DemandaJuridicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandaJuridicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandaJuridicaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
