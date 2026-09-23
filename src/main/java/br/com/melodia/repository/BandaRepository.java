package br.com.melodia.repository;

import br.com.melodia.model.Banda;
import br.com.melodia.model.StatusAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BandaRepository extends JpaRepository<Banda, Long> {

    List<Banda> findByStatus(StatusAprovacao status);

    List<Banda> findByNomeContainingIgnoreCaseAndStatus(String nome, StatusAprovacao status);

    List<Banda> findByUsuarioCadastranteId(Long usuarioId);
}
