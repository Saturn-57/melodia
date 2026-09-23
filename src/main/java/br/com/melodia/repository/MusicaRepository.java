package br.com.melodia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.melodia.model.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
