package br.com.fiap.safeheat_backend_java.repository;

import br.com.fiap.safeheat_backend_java.model.LocalMonitorado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalMonitoradoRepository extends JpaRepository<LocalMonitorado, Long> {
    List<LocalMonitorado> findAllByOrderByIdAsc();
}
