package br.com.fiap.safeheat_backend_java.repository;

import br.com.fiap.safeheat_backend_java.model.AlertaCalor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaCalorRepository extends JpaRepository<AlertaCalor, Long> {
    List<AlertaCalor> findAllByOrderByIdAsc();
}
