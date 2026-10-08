package br.com.conectasaude.loginseguro.controller;

import br.com.conectasaude.loginseguro.dto.CadastroUsuarioForm;
import br.com.conectasaude.loginseguro.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final CadastroUsuarioService cadastroUsuarioService;

    public AuthController(CadastroUsuarioService cadastroUsuarioService) {
        this.cadastroUsuarioService = cadastroUsuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        if (!model.containsAttribute("cadastroUsuarioForm")) {
            model.addAttribute("cadastroUsuarioForm", new CadastroUsuarioForm());
        }
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute CadastroUsuarioForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cadastro";
        }

        try {
            cadastroUsuarioService.cadastrar(form);
        } catch (IllegalArgumentException erro) {
            bindingResult.rejectValue("email", "usuario.email.duplicado", erro.getMessage());
            return "cadastro";
        }

        redirectAttributes.addFlashAttribute("sucesso", "Conta criada com sucesso. Entre com seu e-mail e senha.");
        return "redirect:/login";
    }
}
