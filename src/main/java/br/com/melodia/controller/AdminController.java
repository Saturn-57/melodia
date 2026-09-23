package br.com.melodia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.melodia.service.BandaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BandaService bandaService;

    @GetMapping("/pendentes")
    public String listarPendentes(Model model) {
        model.addAttribute("bandas", bandaService.listarPendentes());
        return "admin-pendentes";
    }

    @PostMapping("/pendentes/{id}/aprovar")
    public String aprovar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bandaService.aprovar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Banda aprovada com sucesso.");
        return "redirect:/admin/pendentes";
    }

    @PostMapping("/pendentes/{id}/rejeitar")
    public String rejeitar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bandaService.rejeitar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Banda rejeitada.");
        return "redirect:/admin/pendentes";
    }
}
