package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DemandanteFisico} entity.
 */
public class DemandanteFisicoDTO implements Serializable {

    private Long id;

    private Long cpf;

    private String nomeCompleto;

    private String email;

    private Long telefone;

    private DemandaFisicaDTO demanda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public DemandaFisicaDTO getDemanda() {
        return demanda;
    }

    public void setDemanda(DemandaFisicaDTO demanda) {
        this.demanda = demanda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandanteFisicoDTO)) {
            return false;
        }

        DemandanteFisicoDTO demandanteFisicoDTO = (DemandanteFisicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandanteFisicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteFisicoDTO{" +
            "id=" + getId() +
            ", cpf=" + getCpf() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            ", demanda=" + getDemanda() +
            "}";
    }
}
