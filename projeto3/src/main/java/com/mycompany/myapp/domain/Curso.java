package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome_do_curso")
    private String nomeDoCurso;

    @Column(name = "turma")
    private String turma;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cursos", "enderecos" }, allowSetters = true)
    private DemandaFisica demandaFisica;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cursos" }, allowSetters = true)
    private Professor professor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cursos" }, allowSetters = true)
    private DemandaJuridica demandaJuridica;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeDoCurso() {
        return this.nomeDoCurso;
    }

    public Curso nomeDoCurso(String nomeDoCurso) {
        this.nomeDoCurso = nomeDoCurso;
        return this;
    }

    public void setNomeDoCurso(String nomeDoCurso) {
        this.nomeDoCurso = nomeDoCurso;
    }

    public String getTurma() {
        return this.turma;
    }

    public Curso turma(String turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public DemandaFisica getDemandaFisica() {
        return this.demandaFisica;
    }

    public Curso demandaFisica(DemandaFisica demandaFisica) {
        this.setDemandaFisica(demandaFisica);
        return this;
    }

    public void setDemandaFisica(DemandaFisica demandaFisica) {
        this.demandaFisica = demandaFisica;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public Curso professor(Professor professor) {
        this.setProfessor(professor);
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public DemandaJuridica getDemandaJuridica() {
        return this.demandaJuridica;
    }

    public Curso demandaJuridica(DemandaJuridica demandaJuridica) {
        this.setDemandaJuridica(demandaJuridica);
        return this;
    }

    public void setDemandaJuridica(DemandaJuridica demandaJuridica) {
        this.demandaJuridica = demandaJuridica;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curso)) {
            return false;
        }
        return id != null && id.equals(((Curso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", nomeDoCurso='" + getNomeDoCurso() + "'" +
            ", turma='" + getTurma() + "'" +
            "}";
    }
}
