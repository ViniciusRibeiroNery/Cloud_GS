package br.com.fiap.safeheat_backend_java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.util.List;

@JsonPropertyOrder({ "id_usuario", "nome", "email", "senha", "locais" })
@Entity
@Table(name = "sh_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sh_usuario_seq")
    @SequenceGenerator(name = "sh_usuario_seq", sequenceName = "SH_USUARIO_SEQ", allocationSize = 1)
    @Column(name = "id_usuario")
    @JsonProperty("id_usuario")
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 150)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocalMonitorado> locais;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, List<LocalMonitorado> locais) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.locais = locais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<LocalMonitorado> getLocais() {
        return locais;
    }

    public void setLocais(List<LocalMonitorado> locais) {
        this.locais = locais;
    }
}
