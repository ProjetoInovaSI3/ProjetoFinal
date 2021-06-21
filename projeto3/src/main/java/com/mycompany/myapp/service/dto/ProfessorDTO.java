package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Professor} entity.
 */
public class ProfessorDTO implements Serializable {

    private Long id;

    private String curso;

    private String nomeCompleto;

    private String email;

    private Long telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfessorDTO)) {
            return false;
        }

        ProfessorDTO professorDTO = (ProfessorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, professorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfessorDTO{" +
            "id=" + getId() +
            ", curso='" + getCurso() + "'" +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            "}";
    }
}
