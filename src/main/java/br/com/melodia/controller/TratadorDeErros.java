package br.com.melodia.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {
        BandaController.class,
        AdminController.class,
        ContribuicaoController.class
})
public class TratadorDeErros {

    @ExceptionHandler(IllegalArgumentException.class)
    public String tratarErroDeNegocio(IllegalArgumentException excecao, Model model) {
        model.addAttribute("mensagem", excecao.getMessage());
        return "erro";
    }
}
