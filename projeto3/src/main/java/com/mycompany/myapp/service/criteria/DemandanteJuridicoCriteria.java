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
 * Criteria class for the {@link com.mycompany.myapp.domain.DemandanteJuridico} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DemandanteJuridicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /demandante-juridicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DemandanteJuridicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cnpj;

    private StringFilter nomeDaEmpresa;

    private StringFilter nomefantasia;

    private StringFilter email;

    private LongFilter telefone;

    private LongFilter demandaId;

    public DemandanteJuridicoCriteria() {}

    public DemandanteJuridicoCriteria(DemandanteJuridicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cnpj = other.cnpj == null ? null : other.cnpj.copy();
        this.nomeDaEmpresa = other.nomeDaEmpresa == null ? null : other.nomeDaEmpresa.copy();
        this.nomefantasia = other.nomefantasia == null ? null : other.nomefantasia.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.demandaId = other.demandaId == null ? null : other.demandaId.copy();
    }

    @Override
    public DemandanteJuridicoCriteria copy() {
        return new DemandanteJuridicoCriteria(this);
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

    public LongFilter getCnpj() {
        return cnpj;
    }

    public LongFilter cnpj() {
        if (cnpj == null) {
            cnpj = new LongFilter();
        }
        return cnpj;
    }

    public void setCnpj(LongFilter cnpj) {
        this.cnpj = cnpj;
    }

    public StringFilter getNomeDaEmpresa() {
        return nomeDaEmpresa;
    }

    public StringFilter nomeDaEmpresa() {
        if (nomeDaEmpresa == null) {
            nomeDaEmpresa = new StringFilter();
        }
        return nomeDaEmpresa;
    }

    public void setNomeDaEmpresa(StringFilter nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
    }

    public StringFilter getNomefantasia() {
        return nomefantasia;
    }

    public StringFilter nomefantasia() {
        if (nomefantasia == null) {
            nomefantasia = new StringFilter();
        }
        return nomefantasia;
    }

    public void setNomefantasia(StringFilter nomefantasia) {
        this.nomefantasia = nomefantasia;
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
        final DemandanteJuridicoCriteria that = (DemandanteJuridicoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(nomeDaEmpresa, that.nomeDaEmpresa) &&
            Objects.equals(nomefantasia, that.nomefantasia) &&
            Objects.equals(email, that.email) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(demandaId, that.demandaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cnpj, nomeDaEmpresa, nomefantasia, email, telefone, demandaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandanteJuridicoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cnpj != null ? "cnpj=" + cnpj + ", " : "") +
            (nomeDaEmpresa != null ? "nomeDaEmpresa=" + nomeDaEmpresa + ", " : "") +
            (nomefantasia != null ? "nomefantasia=" + nomefantasia + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (demandaId != null ? "demandaId=" + demandaId + ", " : "") +
            "}";
    }
}
