package br.com.fiap.safeheat_backend_java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@JsonPropertyOrder({ "id_alerta", "temperatura", "nivel_risco", "data_alerta", "id_local" })
@Entity
@Table(name = "sh_alertas_calor")
public class AlertaCalor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sh_alerta_seq")
    @SequenceGenerator(name = "sh_alerta_seq", sequenceName = "SH_ALERTA_SEQ", allocationSize = 1)
    @Column(name = "id_alerta")
    @JsonProperty("id_alerta")
    private Long id;

    @Column(nullable = false, length = 50)
    private String temperatura;

    @Column(nullable = false, length = 300)
    private String mensagem;

    @Column(name = "data_alerta", updatable = false)
    private LocalDateTime dataAlerta;

    @Column(name = "nivel_risco",nullable = false, length = 100)
    private String nivelRisco;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private LocalMonitorado local;

    public AlertaCalor() {
    }

    public AlertaCalor(Long id, String temperatura, String mensagem, LocalDateTime dataAlerta, String nivelRisco, LocalMonitorado local) {
        this.id = id;
        this.temperatura = temperatura;
        this.mensagem = mensagem;
        this.dataAlerta = dataAlerta;
        this.nivelRisco = nivelRisco;
        this.local = local;
    }

    @PrePersist
    protected void onCreate() {
        this.dataAlerta = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(LocalDateTime dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public LocalMonitorado getLocal() {
        return local;
    }

    public void setLocal(LocalMonitorado local) {
        this.local = local;
    }
}
