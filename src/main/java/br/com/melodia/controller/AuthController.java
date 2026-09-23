package br.com.melodia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.melodia.model.Usuario;
import br.com.melodia.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastroSubmit(@Valid @ModelAttribute("usuario") Usuario usuario,
                                  BindingResult resultado,
                                  RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            return "cadastro";
        }

        try {
            usuarioService.cadastrar(usuario);
        } catch (IllegalArgumentException excecao) {
            resultado.rejectValue("email", "email.duplicado", excecao.getMessage());
            return "cadastro";
        }

        redirectAttributes.addFlashAttribute("mensagem", "Conta criada com sucesso! Faça login para continuar.");
        return "redirect:/login";
    }
}
