package br.com.melodia.controller;

import br.com.melodia.model.Usuario;
import br.com.melodia.service.BandaService;
import br.com.melodia.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ContribuicaoController {

    private final BandaService bandaService;
    private final UsuarioService usuarioService;

    @GetMapping("/minhas-contribuicoes")
    public String minhasContribuicoes(Authentication authentication, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        model.addAttribute("bandas", bandaService.listarMinhasContribuicoes(usuario.getId()));
        return "minhas-contribuicoes";
    }
}
