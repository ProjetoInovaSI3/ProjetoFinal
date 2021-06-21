package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A DemandanteJuridico.
 */
@Entity
@Table(name = "demandante_juridico")
public class DemandanteJuridico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cnpj")
    private Long cnpj;

    @Column(name = "nome_da_empresa")
    private String nomeDaEmpresa;

    @Column(name = "nomefantasia")
    private String nomefantasia;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private Long telefone;

    @JsonIgnoreProperties(value = { "cursos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private DemandaJuridica demanda;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandanteJuridico id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCnpj() {
        return this.cnpj;
    }

    public DemandanteJuridico cnpj(Long cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeDaEmpresa() {
        return this.nomeDaEmpresa;
    }

    public DemandanteJuridico nomeDaEmpresa(String nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
        return this;
    }

    public void setNomeDaEmpresa(String nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
    }

    public String getNomefantasia() {
        return this.nomefantasia;
    }

    public DemandanteJuridico nomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
        return this;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandanteJuridico email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return this.telefone;
    }

    public DemandanteJuridico telefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public DemandaJuridica getDemanda() {
        return this.demanda;
    }

    public DemandanteJuridico demanda(DemandaJuridica demandaJuridica) {
        this.setDemanda(demandaJuridica);
        return this;
    }

    public void setDemanda(DemandaJuridica demandaJuridica) {
        this.demanda = demandaJuridica;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandanteJuridico)) {
            return false;
        }
        return id != null && id.equals(((DemandanteJuridico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteJuridico{" +
            "id=" + getId() +
            ", cnpj=" + getCnpj() +
            ", nomeDaEmpresa='" + getNomeDaEmpresa() + "'" +
            ", nomefantasia='" + getNomefantasia() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            "}";
    }
}
