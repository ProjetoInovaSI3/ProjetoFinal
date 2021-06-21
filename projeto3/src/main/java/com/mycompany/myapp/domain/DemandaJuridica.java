package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DemandaJuridica.
 */
@Entity
@Table(name = "demanda_juridica")
public class DemandaJuridica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "curso")
    private String curso;

    @OneToMany(mappedBy = "demandaJuridica")
    @JsonIgnoreProperties(value = { "demandaFisica", "professor", "demandaJuridica" }, allowSetters = true)
    private Set<Curso> cursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandaJuridica id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DemandaJuridica descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurso() {
        return this.curso;
    }

    public DemandaJuridica curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public DemandaJuridica cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public DemandaJuridica addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setDemandaJuridica(this);
        return this;
    }

    public DemandaJuridica removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setDemandaJuridica(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        if (this.cursos != null) {
            this.cursos.forEach(i -> i.setDemandaJuridica(null));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.setDemandaJuridica(this));
        }
        this.cursos = cursos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandaJuridica)) {
            return false;
        }
        return id != null && id.equals(((DemandaJuridica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandaJuridica{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", curso='" + getCurso() + "'" +
            "}";
    }
}
