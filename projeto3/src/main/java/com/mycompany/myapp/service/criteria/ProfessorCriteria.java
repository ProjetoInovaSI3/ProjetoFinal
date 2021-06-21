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
 * Criteria class for the {@link com.mycompany.myapp.domain.Professor} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProfessorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /professors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfessorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter curso;

    private StringFilter nomeCompleto;

    private StringFilter email;

    private LongFilter telefone;

    private LongFilter cursoId;

    public ProfessorCriteria() {}

    public ProfessorCriteria(ProfessorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.curso = other.curso == null ? null : other.curso.copy();
        this.nomeCompleto = other.nomeCompleto == null ? null : other.nomeCompleto.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.cursoId = other.cursoId == null ? null : other.cursoId.copy();
    }

    @Override
    public ProfessorCriteria copy() {
        return new ProfessorCriteria(this);
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

    public StringFilter getCurso() {
        return curso;
    }

    public StringFilter curso() {
        if (curso == null) {
            curso = new StringFilter();
        }
        return curso;
    }

    public void setCurso(StringFilter curso) {
        this.curso = curso;
    }

    public StringFilter getNomeCompleto() {
        return nomeCompleto;
    }

    public StringFilter nomeCompleto() {
        if (nomeCompleto == null) {
            nomeCompleto = new StringFilter();
        }
        return nomeCompleto;
    }

    public void setNomeCompleto(StringFilter nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getTelefone() {
        return telefone;
    }

    public LongFilter telefone() {
        if (telefone == null) {
            telefone = new LongFilter();
        }
        return telefone;
    }

    public void setTelefone(LongFilter telefone) {
        this.telefone = telefone;
    }

    public LongFilter getCursoId() {
        return cursoId;
    }

    public LongFilter cursoId() {
        if (cursoId == null) {
            cursoId = new LongFilter();
        }
        return cursoId;
    }

    public void setCursoId(LongFilter cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfessorCriteria that = (ProfessorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(curso, that.curso) &&
            Objects.equals(nomeCompleto, that.nomeCompleto) &&
            Objects.equals(email, that.email) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(cursoId, that.cursoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, curso, nomeCompleto, email, telefone, cursoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfessorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (curso != null ? "curso=" + curso + ", " : "") +
            (nomeCompleto != null ? "nomeCompleto=" + nomeCompleto + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (cursoId != null ? "cursoId=" + cursoId + ", " : "") +
            "}";
    }
}
