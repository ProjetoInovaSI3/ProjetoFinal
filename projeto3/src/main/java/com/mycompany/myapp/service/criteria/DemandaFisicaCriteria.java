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
 * Criteria class for the {@link com.mycompany.myapp.domain.DemandaFisica} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DemandaFisicaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /demanda-fisicas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DemandaFisicaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private StringFilter curso;

    private LongFilter cursoId;

    private LongFilter enderecoId;

    public DemandaFisicaCriteria() {}

    public DemandaFisicaCriteria(DemandaFisicaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.curso = other.curso == null ? null : other.curso.copy();
        this.cursoId = other.cursoId == null ? null : other.cursoId.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
    }

    @Override
    public DemandaFisicaCriteria copy() {
        return new DemandaFisicaCriteria(this);
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
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

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public LongFilter enderecoId() {
        if (enderecoId == null) {
            enderecoId = new LongFilter();
        }
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DemandaFisicaCriteria that = (DemandaFisicaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(curso, that.curso) &&
            Objects.equals(cursoId, that.cursoId) &&
            Objects.equals(enderecoId, that.enderecoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, curso, cursoId, enderecoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandaFisicaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (curso != null ? "curso=" + curso + ", " : "") +
            (cursoId != null ? "cursoId=" + cursoId + ", " : "") +
            (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            "}";
    }
}
