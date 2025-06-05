package br.com.fiap.safeheat_backend_java.service;

import br.com.fiap.safeheat_backend_java.exception.ResourceNotFoundException;
import br.com.fiap.safeheat_backend_java.model.LocalMonitorado;
import br.com.fiap.safeheat_backend_java.repository.LocalMonitoradoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalMonitoradoService extends BaseServiceImpl<LocalMonitorado, Long, LocalMonitoradoRepository> {

    public LocalMonitoradoService(LocalMonitoradoRepository repository) {
        super(repository);
    }

    @Override
    public List<LocalMonitorado> listarTodos() {
        return repository.findAllByOrderByIdAsc();
    }

    @Override
    public LocalMonitorado atualizar(Long id, LocalMonitorado novoLocal) {
        LocalMonitorado existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LocalMonitorado não encontrado"));

        existente.setNome(novoLocal.getNome());
        existente.setRua(novoLocal.getRua());
        existente.setNumero(novoLocal.getNumero());
        existente.setComplemento(novoLocal.getComplemento());
        existente.setBairro(novoLocal.getBairro());
        existente.setCidade(novoLocal.getCidade());
        existente.setEstado(novoLocal.getEstado());
        existente.setCep(novoLocal.getCep());
        existente.setUsuario(novoLocal.getUsuario());

        return repository.save(existente);
    }
}
