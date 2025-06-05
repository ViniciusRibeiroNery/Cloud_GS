package br.com.fiap.safeheat_backend_java.controller;

import br.com.fiap.safeheat_backend_java.dto.AlertaCalorRequestDTO;
import br.com.fiap.safeheat_backend_java.dto.AlertaCalorResponseDTO;
import br.com.fiap.safeheat_backend_java.mapper.AlertaCalorMapper;
import br.com.fiap.safeheat_backend_java.model.AlertaCalor;
import br.com.fiap.safeheat_backend_java.model.LocalMonitorado;
import br.com.fiap.safeheat_backend_java.service.AlertaCalorService;
import br.com.fiap.safeheat_backend_java.service.LocalMonitoradoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alertas")
@Tag(name = "Alertas de Calor", description = "Gerenciamento dos alertas de calor emitidos para locais monitorados")
public class AlertaCalorController {

    private final AlertaCalorService alertaService;
    private final LocalMonitoradoService localService;

    public AlertaCalorController(AlertaCalorService alertaService, LocalMonitoradoService localService) {
        this.alertaService = alertaService;
        this.localService = localService;
    }

    @Operation(summary = "Listar todos os alertas de calor")
    @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso")
    @GetMapping
    public List<AlertaCalorResponseDTO> listarTodos() {
        return alertaService.listarTodos()
                .stream()
                .map(AlertaCalorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar alerta de calor por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    @GetMapping("/{id}")
    public AlertaCalorResponseDTO buscarPorId(@PathVariable Long id){
        return AlertaCalorMapper.toDTO(alertaService.buscarPorId(id));
    }

    @Operation(summary = "Criar um novo alerta de calor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public AlertaCalorResponseDTO salvar(@RequestBody @Valid AlertaCalorRequestDTO dto){
        LocalMonitorado local = localService.buscarPorId(dto.getLocalId());

        AlertaCalor alerta = AlertaCalorMapper.toEntity(dto, local);

        return AlertaCalorMapper.toDTO(alertaService.salvar(alerta));
    }


    @Operation(summary = "Atualizar um alerta de calor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    @PutMapping("/{id}")
    public AlertaCalorResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid AlertaCalorRequestDTO dto) {
        LocalMonitorado local = localService.buscarPorId(dto.getLocalId());

        AlertaCalor alerta = AlertaCalorMapper.toEntity(dto, local);
        alerta.setId(id);

        return AlertaCalorMapper.toDTO(alertaService.atualizar(id, alerta));
    }


    @Operation(summary = "Deletar um alerta de calor por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        return alertaService.deletar(id);
    }
}
