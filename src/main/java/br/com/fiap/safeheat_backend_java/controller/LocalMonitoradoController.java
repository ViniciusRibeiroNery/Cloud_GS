package br.com.fiap.safeheat_backend_java.controller;

import br.com.fiap.safeheat_backend_java.dto.LocalMonitoradoRequestDTO;
import br.com.fiap.safeheat_backend_java.dto.LocalMonitoradoResponseDTO;
import br.com.fiap.safeheat_backend_java.mapper.LocalMonitoradoMapper;
import br.com.fiap.safeheat_backend_java.model.LocalMonitorado;
import br.com.fiap.safeheat_backend_java.model.Usuario;
import br.com.fiap.safeheat_backend_java.service.LocalMonitoradoService;
import br.com.fiap.safeheat_backend_java.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/locais")
@Tag(name = "Locais Monitorados", description = "Gerenciamento dos locais monitorados pelos usuários")
public class LocalMonitoradoController {

    private final LocalMonitoradoService localService;
    private final UsuarioService usuarioService;

    public LocalMonitoradoController(LocalMonitoradoService localService, UsuarioService usuarioService) {
        this.localService = localService;
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar todos os locais monitorados")
    @ApiResponse(responseCode = "200", description = "Lista de locais monitorados retornada com sucesso")
    @GetMapping
    public List<LocalMonitoradoResponseDTO> listarTodos() {
        return localService.listarTodos()
                .stream()
                .map(LocalMonitoradoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar local monitorado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local monitorado encontrado"),
            @ApiResponse(responseCode = "404", description = "Local monitorado não encontrado")
    })
    @GetMapping("/{id}")
    public LocalMonitoradoResponseDTO buscarPorId(@PathVariable Long id) {
        return LocalMonitoradoMapper.toDTO(localService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar um novo local monitorado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Local monitorado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public LocalMonitoradoResponseDTO salvar(@RequestBody @Valid LocalMonitoradoRequestDTO dto){
        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId());

        LocalMonitorado local = LocalMonitoradoMapper.toEntity(dto, usuario);

        return LocalMonitoradoMapper.toDTO(localService.salvar(local));
    }

    @Operation(summary = "Atualizar um local monitorado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local monitorado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local monitorado não encontrado")
    })
    @PutMapping("/{id}")
    public LocalMonitoradoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid LocalMonitoradoRequestDTO dto){

        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId());

        LocalMonitorado local = LocalMonitoradoMapper.toEntity(dto, usuario);
        local.setId(id);

        return LocalMonitoradoMapper.toDTO(localService.atualizar(id, local));
    }

    @Operation(summary = "Deletar um local monitorado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local monitorado deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local monitorado não encontrado")
    })
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        return localService.deletar(id);
    }
}
