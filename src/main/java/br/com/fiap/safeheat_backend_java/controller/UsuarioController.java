package br.com.fiap.safeheat_backend_java.controller;

import br.com.fiap.safeheat_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.safeheat_backend_java.dto.UsuarioResponseDTO;
import br.com.fiap.safeheat_backend_java.mapper.UsuarioMapper;
import br.com.fiap.safeheat_backend_java.model.Usuario;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping
    public List<UsuarioResponseDTO> listarTodos() {
        return service.listarTodos()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id){
        return UsuarioMapper.toDTO(service.buscarPorId(id));
    }

    @Operation(summary = "Criar um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @PostMapping
    public UsuarioResponseDTO salvar(@RequestBody @Valid UsuarioRequestDTO dto){
        return UsuarioMapper.toDTO(service.salvar(UsuarioMapper.toEntity(dto)));
    }

    @Operation(summary = "Atualizar um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto){
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setId(id);
        return UsuarioMapper.toDTO(service.atualizar(id,usuario));
    }

    @Operation(summary = "Deletar um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        return service.deletar(id);
    }
}
