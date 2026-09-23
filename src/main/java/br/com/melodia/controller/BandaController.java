package br.com.melodia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.melodia.model.Album;
import br.com.melodia.model.Banda;
import br.com.melodia.model.Integrante;
import br.com.melodia.model.Musica;
import br.com.melodia.model.Usuario;
import br.com.melodia.service.BandaService;
import br.com.melodia.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BandaController {

    private static final int SLOTS_INTEGRANTES = 5;
    private static final int SLOTS_ALBUNS = 2;
    private static final int SLOTS_MUSICAS_POR_ALBUM = 3;

    private final BandaService bandaService;
    private final UsuarioService usuarioService;

    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "nome", required = false) String nome, Model model) {
        model.addAttribute("nome", nome);
        model.addAttribute("bandas", bandaService.pesquisar(nome));
        return "buscar";
    }

    @GetMapping("/banda/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("banda", bandaService.buscarAprovadaPorId(id));
        return "banda-detalhe";
    }

    @GetMapping("/bandas/nova")
    public String novaBandaForm(Model model) {
        model.addAttribute("banda", montarBandaVazia());
        return "banda-form";
    }

    @PostMapping("/bandas/nova")
    public String novaBandaSubmit(@Valid @ModelAttribute("banda") Banda banda,
                                   BindingResult resultado,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            return "banda-form";
        }

        removerSlotsEmBranco(banda);

        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        bandaService.propor(banda, usuario);

        redirectAttributes.addFlashAttribute("mensagem",
                "Banda enviada com sucesso! Ela ficará visível assim que um administrador aprovar.");
        return "redirect:/minhas-contribuicoes";
    }

    private Banda montarBandaVazia() {
        Banda banda = new Banda();

        for (int i = 0; i < SLOTS_INTEGRANTES; i++) {
            banda.getIntegrantes().add(new Integrante());
        }

        for (int i = 0; i < SLOTS_ALBUNS; i++) {
            Album album = new Album();
            for (int j = 0; j < SLOTS_MUSICAS_POR_ALBUM; j++) {
                album.getMusicas().add(new Musica());
            }
            banda.getAlbuns().add(album);
        }

        return banda;
    }

    private void removerSlotsEmBranco(Banda banda) {
        banda.getIntegrantes().removeIf(integrante -> integrante.getNome() == null || integrante.getNome().isBlank());
        banda.getIntegrantes().forEach(integrante -> integrante.setBanda(banda));

        banda.getAlbuns().removeIf(album -> album.getNome() == null || album.getNome().isBlank());
        banda.getAlbuns().forEach(album ->
                album.getMusicas().removeIf(musica -> musica.getNome() == null || musica.getNome().isBlank())
        );
    }
}
