package br.com.fiap.safeheat_backend_java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "id_usuario", "nome", "email", "senha", "locais" })
public class UsuarioResponseDTO {

    @JsonProperty("id_usuario")
    private Long id;
    private String nome;
    private String email;
    private String senha;
    @JsonProperty("locais")
    private List<LocalMonitoradoResponseDTO> locais;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String email, String senha, List<LocalMonitoradoResponseDTO> locais) {
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

    public List<LocalMonitoradoResponseDTO> getLocais() {
        return locais;
    }

    public void setLocais(List<LocalMonitoradoResponseDTO> locais) {
        this.locais = locais;
    }
}
