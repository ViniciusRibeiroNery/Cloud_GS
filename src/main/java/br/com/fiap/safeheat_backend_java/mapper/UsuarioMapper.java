package br.com.fiap.safeheat_backend_java.mapper;

import br.com.fiap.safeheat_backend_java.dto.LocalMonitoradoResponseDTO;
import br.com.fiap.safeheat_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.safeheat_backend_java.dto.UsuarioResponseDTO;
import br.com.fiap.safeheat_backend_java.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        List<LocalMonitoradoResponseDTO> locais;

        if (usuario.getLocais() != null) {
            locais = usuario.getLocais().stream()
                    .map(LocalMonitoradoMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            locais = new ArrayList<>();
        }

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                locais
        );
    }

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
}
