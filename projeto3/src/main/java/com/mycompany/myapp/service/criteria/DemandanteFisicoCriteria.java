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
 * Criteria class for the {@link com.mycompany.myapp.domain.DemandanteFisico} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DemandanteFisicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /demandante-fisicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DemandanteFisicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cpf;

    private StringFilter nomeCompleto;

    private StringFilter email;

    private LongFilter telefone;

    private LongFilter demandaId;

    public DemandanteFisicoCriteria() {}

    public DemandanteFisicoCriteria(DemandanteFisicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.nomeCompleto = other.nomeCompleto == null ? null : other.nomeCompleto.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.demandaId = other.demandaId == null ? null : other.demandaId.copy();
    }

    @Override
    public DemandanteFisicoCriteria copy() {
        return new DemandanteFisicoCriteria(this);
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

    public LongFilter getCpf() {
        return cpf;
    }

    public LongFilter cpf() {
        if (cpf == null) {
            cpf = new LongFilter();
        }
        return cpf;
    }

    public void setCpf(LongFilter cpf) {
        this.cpf = cpf;
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

    public LongFilter getDemandaId() {
        return demandaId;
    }

    public LongFilter demandaId() {
        if (demandaId == null) {
            demandaId = new LongFilter();
        }
        return demandaId;
    }

    public void setDemandaId(LongFilter demandaId) {
        this.demandaId = demandaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DemandanteFisicoCriteria that = (DemandanteFisicoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(nomeCompleto, that.nomeCompleto) &&
            Objects.equals(email, that.email) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(demandaId, that.demandaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nomeCompleto, email, telefone, demandaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteFisicoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (nomeCompleto != null ? "nomeCompleto=" + nomeCompleto + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (demandaId != null ? "demandaId=" + demandaId + ", " : "") +
            "}";
    }
}
