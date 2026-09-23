package br.com.melodia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.melodia.model.Banda;
import br.com.melodia.model.StatusAprovacao;
import br.com.melodia.model.Usuario;
import br.com.melodia.repository.BandaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BandaService {

    private final BandaRepository bandaRepository;

    public List<Banda> pesquisar(String nome) {
        if (nome == null || nome.isBlank()) {
            return bandaRepository.findByStatus(StatusAprovacao.APROVADA);
        }
        return bandaRepository.findByNomeContainingIgnoreCaseAndStatus(nome, StatusAprovacao.APROVADA);
    }

    public Banda buscarAprovadaPorId(Long id) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banda não encontrada."));

        if (banda.getStatus() != StatusAprovacao.APROVADA) {
            throw new IllegalArgumentException("Esta banda ainda não foi aprovada pelo administrador.");
        }
        return banda;
    }

    public Banda propor(Banda banda, Usuario usuarioCadastrante) {
        banda.setStatus(StatusAprovacao.PENDENTE);
        banda.setUsuarioCadastrante(usuarioCadastrante);

        if (banda.getIntegrantes() != null) {
            banda.getIntegrantes().forEach(integrante -> integrante.setBanda(banda));
        }

        if (banda.getAlbuns() != null) {
            banda.getAlbuns().forEach(album -> {
                album.setBanda(banda);
                if (album.getMusicas() != null) {
                    album.getMusicas().forEach(musica -> musica.setAlbum(album));
                }
            });
        }

        return bandaRepository.save(banda);
    }

    public List<Banda> listarPendentes() {
        return bandaRepository.findByStatus(StatusAprovacao.PENDENTE);
    }

    public List<Banda> listarMinhasContribuicoes(Long usuarioId) {
        return bandaRepository.findByUsuarioCadastranteId(usuarioId);
    }

    public void aprovar(Long id) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banda não encontrada."));
        banda.setStatus(StatusAprovacao.APROVADA);
        bandaRepository.save(banda);
    }

    public void rejeitar(Long id) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banda não encontrada."));
        banda.setStatus(StatusAprovacao.REJEITADA);
        bandaRepository.save(banda);
    }
}
