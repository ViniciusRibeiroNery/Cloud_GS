package br.com.fiap.safeheat_backend_java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({ "id_local", "nome", "rua", "numero", "complemento", "bairro", "cidade", "estado", "cep", "id_usuario", "alertas" })
public class LocalMonitoradoResponseDTO {
    @JsonProperty("id_local")
    private Long id;
    private String nome;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    @JsonProperty("id_usuario")
    private Long usuarioId;
    private List<AlertaCalorResponseDTO> alertas;

    public LocalMonitoradoResponseDTO() {
    }

    public LocalMonitoradoResponseDTO(Long id, String nome, String rua, String numero, String complemento,
                                      String bairro, String cidade, String estado, String cep, Long usuarioId, List<AlertaCalorResponseDTO> alertas) {
        this.id = id;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.usuarioId = usuarioId;
        this.alertas = alertas;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<AlertaCalorResponseDTO> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<AlertaCalorResponseDTO> alertas) {
        this.alertas = alertas;
    }
}
