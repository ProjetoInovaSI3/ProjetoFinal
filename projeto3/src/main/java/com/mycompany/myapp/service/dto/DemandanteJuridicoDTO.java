package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DemandanteJuridico} entity.
 */
public class DemandanteJuridicoDTO implements Serializable {

    private Long id;

    private Long cnpj;

    private String nomeDaEmpresa;

    private String nomefantasia;

    private String email;

    private Long telefone;

    private DemandaJuridicaDTO demanda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeDaEmpresa() {
        return nomeDaEmpresa;
    }

    public void setNomeDaEmpresa(String nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
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

    public DemandaJuridicaDTO getDemanda() {
        return demanda;
    }

    public void setDemanda(DemandaJuridicaDTO demanda) {
        this.demanda = demanda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandanteJuridicoDTO)) {
            return false;
        }

        DemandanteJuridicoDTO demandanteJuridicoDTO = (DemandanteJuridicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandanteJuridicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteJuridicoDTO{" +
            "id=" + getId() +
            ", cnpj=" + getCnpj() +
            ", nomeDaEmpresa='" + getNomeDaEmpresa() + "'" +
            ", nomefantasia='" + getNomefantasia() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            ", demanda=" + getDemanda() +
            "}";
    }
}
