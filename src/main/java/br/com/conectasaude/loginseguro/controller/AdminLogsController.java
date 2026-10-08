package br.com.conectasaude.loginseguro.controller;

import br.com.conectasaude.loginseguro.service.LogAuditoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/logs")
public class AdminLogsController {
    private final LogAuditoriaService logs;

    public AdminLogsController(LogAuditoriaService logs) { this.logs = logs; }

    @GetMapping
    public String listar(@RequestParam(required = false, defaultValue = "TODOS") String nivel,
                         @RequestParam(required = false, defaultValue = "") String busca,
                         Model model) {
        model.addAttribute("logs", logs.pesquisar(nivel, busca));
        model.addAttribute("nivelSelecionado", nivel);
        model.addAttribute("busca", busca);
        model.addAttribute("totalLogs", logs.contarTodos());
        model.addAttribute("totalInfo", logs.contarNivel("INFO"));
        model.addAttribute("totalWarn", logs.contarNivel("WARN"));
        model.addAttribute("totalError", logs.contarNivel("ERROR"));
        return "admin/logs";
    }
}
