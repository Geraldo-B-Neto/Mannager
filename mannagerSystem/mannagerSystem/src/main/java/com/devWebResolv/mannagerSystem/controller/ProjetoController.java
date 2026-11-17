package com.devWebResolv.mannagerSystem.controller;

import com.devWebResolv.mannagerSystem.model.Projeto;
import com.devWebResolv.mannagerSystem.model.StatusProjeto;
import com.devWebResolv.mannagerSystem.service.ProjetoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    /**
     * Lista todos os projetos cadastrados
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("projetos", projetoService.listarTodos());
        return "projeto/index";
    }

    /**
     * Exibe o formulário para cadastro de um novo projeto
     */
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("projeto", new Projeto());
        model.addAttribute("statusList", StatusProjeto.values());
        return "projeto/create";
    }

    /**
     * Salva ou atualiza um projeto
     */
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("projeto") Projeto projeto, 
                         RedirectAttributes redirectAttributes, 
                         Model model) {
        try {
            projetoService.salvar(projeto);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto salvo com sucesso!");
            return "redirect:/projetos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagemErro", e.getMessage());
            model.addAttribute("projeto", projeto);
            model.addAttribute("statusList", StatusProjeto.values());
            return "projeto/create";
        }
    }

    /**
     * Exibe o formulário de edição com os dados do projeto carregados
     */
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, 
                                         Model model, 
                                         RedirectAttributes redirectAttributes) {
        Optional<Projeto> projetoOpt = projetoService.buscarPorId(id);
        if (projetoOpt.isPresent()) {
            model.addAttribute("projeto", projetoOpt.get());
            model.addAttribute("statusList", StatusProjeto.values());
            return "projeto/create";
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Projeto não encontrado com o ID " + id);
            return "redirect:/projetos";
        }
    }

    /**
     * Exclui um projeto pelo seu ID
     */
    @GetMapping({"/deletar/{id}", "/excluir/{id}"})
    public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            projetoService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Projeto excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/projetos";
    }
}
