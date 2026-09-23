package br.com.melodia.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.melodia.model.Album;
import br.com.melodia.model.Banda;
import br.com.melodia.model.Integrante;
import br.com.melodia.model.Musica;
import br.com.melodia.model.Role;
import br.com.melodia.model.StatusAprovacao;
import br.com.melodia.model.Usuario;
import br.com.melodia.repository.BandaRepository;
import br.com.melodia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final BandaRepository bandaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Usuario admin = seedAdmin();
        seedBandas(admin);
    }

    private Usuario seedAdmin() {
        String emailAdmin = "admin@melodia.com";

        Usuario existente = usuarioRepository.findByEmail(emailAdmin).orElse(null);
        if (existente != null) {
            return existente;
        }

        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setEmail(emailAdmin);
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        return usuarioRepository.save(admin);
    }

    private void seedBandas(Usuario cadastrante) {
        if (bandaRepository.count() > 0) {
            return;
        }

        bandaRepository.save(legiaoUrbana(cadastrante));
        bandaRepository.save(duranDuran(cadastrante));
        bandaRepository.save(mamonasAssassinas(cadastrante));
        bandaRepository.save(modernTalking(cadastrante));
        bandaRepository.save(theBeatles(cadastrante));
    }

    private Banda legiaoUrbana(Usuario cadastrante) {
        Banda banda = new Banda();
        banda.setNome("Legião Urbana");
        banda.setPais("Brasil");
        banda.setAnoFormacao(1982);
        banda.setBiografia("Banda de rock brasileira formada em Brasília, uma das mais influentes "
                + "do rock nacional dos anos 80 e 90, liderada por Renato Russo.");
        banda.setStatus(StatusAprovacao.APROVADA);
        banda.setUsuarioCadastrante(cadastrante);

        adicionarIntegrantes(banda, "Renato Russo", "Dado Villa-Lobos", "Marcelo Bonfá");

        Album album1 = novoAlbum(banda, "Legião Urbana", 1985);
        adicionarMusicas(album1,
                musica("Geração Coca-Cola", "https://www.youtube.com/results?search_query=Legi%C3%A3o+Urbana+Gera%C3%A7%C3%A3o+Coca-Cola"),
                musica("Será", null));

        Album album2 = novoAlbum(banda, "Que País É Este", 1987);
        adicionarMusicas(album2,
                musica("Que País É Este", "https://www.youtube.com/results?search_query=Legi%C3%A3o+Urbana+Que+Pa%C3%ADs+%C3%89+Este"),
                musica("Faroeste Caboclo", null));

        banda.getAlbuns().add(album1);
        banda.getAlbuns().add(album2);

        return banda;
    }

    private Banda duranDuran(Usuario cadastrante) {
        Banda banda = new Banda();
        banda.setNome("Duran Duran");
        banda.setPais("Reino Unido");
        banda.setAnoFormacao(1978);
        banda.setBiografia("Banda britânica de new wave/synth-pop formada em Birmingham, um dos "
                + "maiores nomes da 'segunda invasão britânica' nos Estados Unidos.");
        banda.setStatus(StatusAprovacao.APROVADA);
        banda.setUsuarioCadastrante(cadastrante);

        adicionarIntegrantes(banda, "Simon Le Bon", "Nick Rhodes", "John Taylor", "Roger Taylor");

        Album album1 = novoAlbum(banda, "Rio", 1982);
        adicionarMusicas(album1,
                musica("Hungry Like the Wolf", "https://www.youtube.com/results?search_query=Duran+Duran+Hungry+Like+the+Wolf"),
                musica("Rio", null));

        Album album2 = novoAlbum(banda, "Seven and the Ragged Tiger", 1983);
        adicionarMusicas(album2,
                musica("The Reflex", null));

        banda.getAlbuns().add(album1);
        banda.getAlbuns().add(album2);

        return banda;
    }

    private Banda mamonasAssassinas(Usuario cadastrante) {
        Banda banda = new Banda();
        banda.setNome("Mamonas Assassinas");
        banda.setPais("Brasil");
        banda.setAnoFormacao(1990);
        banda.setBiografia("Banda de rock com humor formada em Guarulhos (SP), conhecida por letras "
                + "bem-humoradas; lançou apenas um álbum antes de a carreira ser interrompida "
                + "por um acidente aéreo em 1996.");
        banda.setStatus(StatusAprovacao.APROVADA);
        banda.setUsuarioCadastrante(cadastrante);

        adicionarIntegrantes(banda, "Dinho", "Bento Hinoto", "Samuel Reoli", "Júlio Rasec", "Vinícius Assumpção");

        Album album = novoAlbum(banda, "Mamonas Assassinas", 1995);
        adicionarMusicas(album,
                musica("Pelados em Santos", "https://www.youtube.com/results?search_query=Mamonas+Assassinas+Pelados+em+Santos"),
                musica("Robocop Gay", null),
                musica("Chiclete com Banana", null));
        banda.getAlbuns().add(album);

        return banda;
    }

    private Banda modernTalking(Usuario cadastrante) {
        Banda banda = new Banda();
        banda.setNome("Modern Talking");
        banda.setPais("Alemanha");
        banda.setAnoFormacao(1983);
        banda.setBiografia("Dupla alemã de música pop formada por Dieter Bohlen e Thomas Anders, "
                + "um dos maiores sucessos da música pop europeia dos anos 80.");
        banda.setStatus(StatusAprovacao.APROVADA);
        banda.setUsuarioCadastrante(cadastrante);

        adicionarIntegrantes(banda, "Thomas Anders", "Dieter Bohlen");

        Album album = novoAlbum(banda, "The 1st Album", 1985);
        adicionarMusicas(album,
                musica("You're My Heart, You're My Soul", "https://www.youtube.com/results?search_query=Modern+Talking+You%27re+My+Heart+You%27re+My+Soul"),
                musica("Cheri, Cheri Lady", null));
        banda.getAlbuns().add(album);

        return banda;
    }

    private Banda theBeatles(Usuario cadastrante) {
        Banda banda = new Banda();
        banda.setNome("The Beatles");
        banda.setPais("Reino Unido");
        banda.setAnoFormacao(1960);
        banda.setBiografia("Banda britânica formada em Liverpool, considerada uma das mais "
                + "influentes da história da música popular.");
        banda.setStatus(StatusAprovacao.APROVADA);
        banda.setUsuarioCadastrante(cadastrante);

        adicionarIntegrantes(banda, "John Lennon", "Paul McCartney", "George Harrison", "Ringo Starr");

        Album album1 = novoAlbum(banda, "Abbey Road", 1969);
        adicionarMusicas(album1,
                musica("Come Together", "https://www.youtube.com/results?search_query=The+Beatles+Come+Together"),
                musica("Here Comes the Sun", null));

        Album album2 = novoAlbum(banda, "Sgt. Pepper's Lonely Hearts Club Band", 1967);
        adicionarMusicas(album2,
                musica("A Day in the Life", null));

        banda.getAlbuns().add(album1);
        banda.getAlbuns().add(album2);

        return banda;
    }

    private void adicionarIntegrantes(Banda banda, String... nomes) {
        for (String nome : nomes) {
            Integrante integrante = new Integrante();
            integrante.setNome(nome);
            integrante.setBanda(banda);
            banda.getIntegrantes().add(integrante);
        }
    }

    private Album novoAlbum(Banda banda, String nome, Integer anoLancamento) {
        Album album = new Album();
        album.setNome(nome);
        album.setAnoLancamento(anoLancamento);
        album.setBanda(banda);
        return album;
    }

    private void adicionarMusicas(Album album, Musica... musicas) {
        for (Musica musica : musicas) {
            musica.setAlbum(album);
            album.getMusicas().add(musica);
        }
    }

    private Musica musica(String nome, String linkYoutube) {
        Musica musica = new Musica();
        musica.setNome(nome);
        musica.setLinkYoutube(linkYoutube);
        return musica;
    }
}
