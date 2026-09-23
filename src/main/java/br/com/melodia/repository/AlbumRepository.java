package br.com.melodia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.melodia.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
