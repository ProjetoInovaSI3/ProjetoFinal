package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Curso} entity.
 */
public class CursoDTO implements Serializable {

    private Long id;

    private String nomeDoCurso;

    private String turma;

    private DemandaFisicaDTO demandaFisica;

    private ProfessorDTO professor;

    private DemandaJuridicaDTO demandaJuridica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoCurso() {
        return nomeDoCurso;
    }

    public void setNomeDoCurso(String nomeDoCurso) {
        this.nomeDoCurso = nomeDoCurso;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public DemandaFisicaDTO getDemandaFisica() {
        return demandaFisica;
    }

    public void setDemandaFisica(DemandaFisicaDTO demandaFisica) {
        this.demandaFisica = demandaFisica;
    }

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

    public DemandaJuridicaDTO getDemandaJuridica() {
        return demandaJuridica;
    }

    public void setDemandaJuridica(DemandaJuridicaDTO demandaJuridica) {
        this.demandaJuridica = demandaJuridica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CursoDTO)) {
            return false;
        }

        CursoDTO cursoDTO = (CursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoDTO{" +
            "id=" + getId() +
            ", nomeDoCurso='" + getNomeDoCurso() + "'" +
            ", turma='" + getTurma() + "'" +
            ", demandaFisica=" + getDemandaFisica() +
            ", professor=" + getProfessor() +
            ", demandaJuridica=" + getDemandaJuridica() +
            "}";
    }
}
