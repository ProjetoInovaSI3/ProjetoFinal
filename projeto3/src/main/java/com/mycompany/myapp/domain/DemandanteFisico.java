package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A DemandanteFisico.
 */
@Entity
@Table(name = "demandante_fisico")
public class DemandanteFisico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cpf")
    private Long cpf;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private Long telefone;

    @JsonIgnoreProperties(value = { "cursos", "enderecos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private DemandaFisica demanda;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandanteFisico id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCpf() {
        return this.cpf;
    }

    public DemandanteFisico cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public DemandanteFisico nomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandanteFisico email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return this.telefone;
    }

    public DemandanteFisico telefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public DemandaFisica getDemanda() {
        return this.demanda;
    }

    public DemandanteFisico demanda(DemandaFisica demandaFisica) {
        this.setDemanda(demandaFisica);
        return this;
    }

    public void setDemanda(DemandaFisica demandaFisica) {
        this.demanda = demandaFisica;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandanteFisico)) {
            return false;
        }
        return id != null && id.equals(((DemandanteFisico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteFisico{" +
            "id=" + getId() +
            ", cpf=" + getCpf() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            "}";
    }
}
