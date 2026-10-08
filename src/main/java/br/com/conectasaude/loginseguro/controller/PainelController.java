package br.com.conectasaude.loginseguro.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PainelController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/painel";
    }

    @GetMapping("/painel")
    public String painel(Authentication authentication) {
        if (temPerfil(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        if (temPerfil(authentication, "ROLE_PROFESSOR")) {
            return "redirect:/professor";
        }
        return "redirect:/aluno";
    }

    @GetMapping("/aluno")
    public String aluno(Model model) {
        model.addAttribute("titulo", "Area do aluno");
        model.addAttribute("descricao", "Espaco reservado para recursos educativos e atividades do estudante.");
        return "area";
    }

    @GetMapping("/professor")
    public String professor(Model model) {
        model.addAttribute("titulo", "Area do professor");
        model.addAttribute("descricao", "Espaco reservado para acompanhamento de alunos e conteudos criados pelo professor.");
        return "area";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("titulo", "Area administrativa");
        model.addAttribute("descricao", "Espaco para gestao de usuarios, perfis e futuras configuracoes do sistema.");
        return "area";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

    private boolean temPerfil(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
