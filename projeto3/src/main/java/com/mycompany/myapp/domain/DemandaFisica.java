package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DemandaFisica.
 */
@Entity
@Table(name = "demanda_fisica")
public class DemandaFisica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "curso")
    private String curso;

    @OneToMany(mappedBy = "demandaFisica")
    @JsonIgnoreProperties(value = { "demandaFisica", "professor", "demandaJuridica" }, allowSetters = true)
    private Set<Curso> cursos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_demanda_fisica__endereco",
        joinColumns = @JoinColumn(name = "demanda_fisica_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    @JsonIgnoreProperties(value = { "demandas" }, allowSetters = true)
    private Set<Endereco> enderecos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandaFisica id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DemandaFisica descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurso() {
        return this.curso;
    }

    public DemandaFisica curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public DemandaFisica cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public DemandaFisica addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setDemandaFisica(this);
        return this;
    }

    public DemandaFisica removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setDemandaFisica(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        if (this.cursos != null) {
            this.cursos.forEach(i -> i.setDemandaFisica(null));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.setDemandaFisica(this));
        }
        this.cursos = cursos;
    }

    public Set<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public DemandaFisica enderecos(Set<Endereco> enderecos) {
        this.setEnderecos(enderecos);
        return this;
    }

    public DemandaFisica addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.getDemandas().add(this);
        return this;
    }

    public DemandaFisica removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.getDemandas().remove(this);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandaFisica)) {
            return false;
        }
        return id != null && id.equals(((DemandaFisica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandaFisica{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
