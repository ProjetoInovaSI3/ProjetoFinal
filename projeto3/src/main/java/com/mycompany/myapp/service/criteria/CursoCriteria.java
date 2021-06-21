package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Curso} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CursoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cursos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CursoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeDoCurso;

    private StringFilter turma;

    private LongFilter demandaFisicaId;

    private LongFilter professorId;

    private LongFilter demandaJuridicaId;

    public CursoCriteria() {}

    public CursoCriteria(CursoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeDoCurso = other.nomeDoCurso == null ? null : other.nomeDoCurso.copy();
        this.turma = other.turma == null ? null : other.turma.copy();
        this.demandaFisicaId = other.demandaFisicaId == null ? null : other.demandaFisicaId.copy();
        this.professorId = other.professorId == null ? null : other.professorId.copy();
        this.demandaJuridicaId = other.demandaJuridicaId == null ? null : other.demandaJuridicaId.copy();
    }

    @Override
    public CursoCriteria copy() {
        return new CursoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeDoCurso() {
        return nomeDoCurso;
    }

    public StringFilter nomeDoCurso() {
        if (nomeDoCurso == null) {
            nomeDoCurso = new StringFilter();
        }
        return nomeDoCurso;
    }

    public void setNomeDoCurso(StringFilter nomeDoCurso) {
        this.nomeDoCurso = nomeDoCurso;
    }

    public StringFilter getTurma() {
        return turma;
    }

    public StringFilter turma() {
        if (turma == null) {
            turma = new StringFilter();
        }
        return turma;
    }

    public void setTurma(StringFilter turma) {
        this.turma = turma;
    }

    public LongFilter getDemandaFisicaId() {
        return demandaFisicaId;
    }

    public LongFilter demandaFisicaId() {
        if (demandaFisicaId == null) {
            demandaFisicaId = new LongFilter();
        }
        return demandaFisicaId;
    }

    public void setDemandaFisicaId(LongFilter demandaFisicaId) {
        this.demandaFisicaId = demandaFisicaId;
    }

    public LongFilter getProfessorId() {
        return professorId;
    }

    public LongFilter professorId() {
        if (professorId == null) {
            professorId = new LongFilter();
        }
        return professorId;
    }

    public void setProfessorId(LongFilter professorId) {
        this.professorId = professorId;
    }

    public LongFilter getDemandaJuridicaId() {
        return demandaJuridicaId;
    }

    public LongFilter demandaJuridicaId() {
        if (demandaJuridicaId == null) {
            demandaJuridicaId = new LongFilter();
        }
        return demandaJuridicaId;
    }

    public void setDemandaJuridicaId(LongFilter demandaJuridicaId) {
        this.demandaJuridicaId = demandaJuridicaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CursoCriteria that = (CursoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeDoCurso, that.nomeDoCurso) &&
            Objects.equals(turma, that.turma) &&
            Objects.equals(demandaFisicaId, that.demandaFisicaId) &&
            Objects.equals(professorId, that.professorId) &&
            Objects.equals(demandaJuridicaId, that.demandaJuridicaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeDoCurso, turma, demandaFisicaId, professorId, demandaJuridicaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeDoCurso != null ? "nomeDoCurso=" + nomeDoCurso + ", " : "") +
            (turma != null ? "turma=" + turma + ", " : "") +
            (demandaFisicaId != null ? "demandaFisicaId=" + demandaFisicaId + ", " : "") +
            (professorId != null ? "professorId=" + professorId + ", " : "") +
            (demandaJuridicaId != null ? "demandaJuridicaId=" + demandaJuridicaId + ", " : "") +
            "}";
    }
}
