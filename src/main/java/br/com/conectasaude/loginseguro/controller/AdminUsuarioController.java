package br.com.conectasaude.loginseguro.controller;

import br.com.conectasaude.loginseguro.model.Role;
import br.com.conectasaude.loginseguro.service.AdminUsuarioService;
import br.com.conectasaude.loginseguro.service.LogAuditoriaService;
import java.security.Principal;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final AdminUsuarioService adminUsuarioService;
    private final LogAuditoriaService logs;

    public AdminUsuarioController(AdminUsuarioService adminUsuarioService, LogAuditoriaService logs) {
        this.adminUsuarioService = adminUsuarioService;
        this.logs = logs;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", adminUsuarioService.listarTodos());
        model.addAttribute("rolesDisponiveis", Role.values());
        return "admin/usuarios";
    }

    @PostMapping("/{id}/roles")
    public String atualizarPerfis(
            @PathVariable String id,
            @RequestParam(name = "roles", required = false) Set<Role> roles,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            adminUsuarioService.atualizarPerfis(id, roles, principal.getName());
            logs.registrar("INFO", "PERFIS_USUARIO_ATUALIZADOS", "Administrador atualizou os perfis de uma conta.", principal.getName());
            redirectAttributes.addFlashAttribute("sucesso", "Perfis atualizados com sucesso.");
        } catch (IllegalArgumentException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/status")
    public String alternarStatus(
            @PathVariable String id,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            adminUsuarioService.alternarStatus(id, principal.getName());
            logs.registrar("WARN", "STATUS_USUARIO_ALTERADO", "Administrador alterou o status de uma conta.", principal.getName());
            redirectAttributes.addFlashAttribute("sucesso", "Status do usuario atualizado com sucesso.");
        } catch (IllegalArgumentException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
}
