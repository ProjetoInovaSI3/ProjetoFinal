package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Professor.
 */
@Entity
@Table(name = "professor")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "curso")
    private String curso;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private Long telefone;

    @OneToMany(mappedBy = "professor")
    @JsonIgnoreProperties(value = { "demandaFisica", "professor", "demandaJuridica" }, allowSetters = true)
    private Set<Curso> cursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor id(Long id) {
        this.id = id;
        return this;
    }

    public String getCurso() {
        return this.curso;
    }

    public Professor curso(String curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public Professor nomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return this.email;
    }

    public Professor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return this.telefone;
    }

    public Professor telefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public Professor cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public Professor addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setProfessor(this);
        return this;
    }

    public Professor removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setProfessor(null);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        if (this.cursos != null) {
            this.cursos.forEach(i -> i.setProfessor(null));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.setProfessor(this));
        }
        this.cursos = cursos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professor)) {
            return false;
        }
        return id != null && id.equals(((Professor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", curso='" + getCurso() + "'" +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone=" + getTelefone() +
            "}";
    }
}
