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
 * Criteria class for the {@link com.mycompany.myapp.domain.Endereco} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EnderecoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enderecos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnderecoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cep;

    private StringFilter logradouro;

    private StringFilter bairro;

    private StringFilter numero;

    private StringFilter uf;

    private LongFilter ddd;

    private LongFilter demandaId;

    public EnderecoCriteria() {}

    public EnderecoCriteria(EnderecoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.logradouro = other.logradouro == null ? null : other.logradouro.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.uf = other.uf == null ? null : other.uf.copy();
        this.ddd = other.ddd == null ? null : other.ddd.copy();
        this.demandaId = other.demandaId == null ? null : other.demandaId.copy();
    }

    @Override
    public EnderecoCriteria copy() {
        return new EnderecoCriteria(this);
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

    public LongFilter getCep() {
        return cep;
    }

    public LongFilter cep() {
        if (cep == null) {
            cep = new LongFilter();
        }
        return cep;
    }

    public void setCep(LongFilter cep) {
        this.cep = cep;
    }

    public StringFilter getLogradouro() {
        return logradouro;
    }

    public StringFilter logradouro() {
        if (logradouro == null) {
            logradouro = new StringFilter();
        }
        return logradouro;
    }

    public void setLogradouro(StringFilter logradouro) {
        this.logradouro = logradouro;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public StringFilter bairro() {
        if (bairro == null) {
            bairro = new StringFilter();
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getUf() {
        return uf;
    }

    public StringFilter uf() {
        if (uf == null) {
            uf = new StringFilter();
        }
        return uf;
    }

    public void setUf(StringFilter uf) {
        this.uf = uf;
    }

    public LongFilter getDdd() {
        return ddd;
    }

    public LongFilter ddd() {
        if (ddd == null) {
            ddd = new LongFilter();
        }
        return ddd;
    }

    public void setDdd(LongFilter ddd) {
        this.ddd = ddd;
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
        final EnderecoCriteria that = (EnderecoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(logradouro, that.logradouro) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(uf, that.uf) &&
            Objects.equals(ddd, that.ddd) &&
            Objects.equals(demandaId, that.demandaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cep, logradouro, bairro, numero, uf, ddd, demandaId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cep != null ? "cep=" + cep + ", " : "") +
            (logradouro != null ? "logradouro=" + logradouro + ", " : "") +
            (bairro != null ? "bairro=" + bairro + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (uf != null ? "uf=" + uf + ", " : "") +
            (ddd != null ? "ddd=" + ddd + ", " : "") +
            (demandaId != null ? "demandaId=" + demandaId + ", " : "") +
            "}";
    }
}
