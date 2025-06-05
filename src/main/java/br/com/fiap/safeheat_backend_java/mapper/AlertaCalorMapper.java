package br.com.fiap.safeheat_backend_java.mapper;

import br.com.fiap.safeheat_backend_java.dto.AlertaCalorRequestDTO;
import br.com.fiap.safeheat_backend_java.dto.AlertaCalorResponseDTO;
import br.com.fiap.safeheat_backend_java.model.AlertaCalor;
import br.com.fiap.safeheat_backend_java.model.LocalMonitorado;

public class AlertaCalorMapper {
    public static AlertaCalorResponseDTO toDTO(AlertaCalor alerta) {
        return new AlertaCalorResponseDTO(
                alerta.getId(),
                alerta.getTemperatura(),
                alerta.getMensagem(),
                alerta.getDataAlerta(),
                alerta.getNivelRisco(),
                alerta.getLocal().getId()
        );
    }

    public static AlertaCalor toEntity(AlertaCalorRequestDTO dto, LocalMonitorado local) {
        AlertaCalor alerta = new AlertaCalor();
        alerta.setTemperatura(dto.getTemperatura());
        alerta.setMensagem(dto.getMensagem());
        alerta.setNivelRisco(dto.getNivelRisco());
        alerta.setLocal(local);
        return alerta;
    }
}
