package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Endereco.
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cep")
    private Long cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "uf")
    private String uf;

    @Column(name = "ddd")
    private Long ddd;

    @ManyToMany(mappedBy = "enderecos")
    @JsonIgnoreProperties(value = { "cursos", "enderecos" }, allowSetters = true)
    private Set<DemandaFisica> demandas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Endereco id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCep() {
        return this.cep;
    }

    public Endereco cep(Long cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public Endereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Endereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return this.numero;
    }

    public Endereco numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUf() {
        return this.uf;
    }

    public Endereco uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getDdd() {
        return this.ddd;
    }

    public Endereco ddd(Long ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(Long ddd) {
        this.ddd = ddd;
    }

    public Set<DemandaFisica> getDemandas() {
        return this.demandas;
    }

    public Endereco demandas(Set<DemandaFisica> demandaFisicas) {
        this.setDemandas(demandaFisicas);
        return this;
    }

    public Endereco addDemanda(DemandaFisica demandaFisica) {
        this.demandas.add(demandaFisica);
        demandaFisica.getEnderecos().add(this);
        return this;
    }

    public Endereco removeDemanda(DemandaFisica demandaFisica) {
        this.demandas.remove(demandaFisica);
        demandaFisica.getEnderecos().remove(this);
        return this;
    }

    public void setDemandas(Set<DemandaFisica> demandaFisicas) {
        if (this.demandas != null) {
            this.demandas.forEach(i -> i.removeEndereco(this));
        }
        if (demandaFisicas != null) {
            demandaFisicas.forEach(i -> i.addEndereco(this));
        }
        this.demandas = demandaFisicas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endereco)) {
            return false;
        }
        return id != null && id.equals(((Endereco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", cep=" + getCep() +
            ", logradouro='" + getLogradouro() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", uf='" + getUf() + "'" +
            ", ddd=" + getDdd() +
            "}";
    }
}
