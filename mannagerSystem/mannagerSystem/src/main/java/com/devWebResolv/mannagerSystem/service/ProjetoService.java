package com.devWebResolv.mannagerSystem.service;

import com.devWebResolv.mannagerSystem.model.Projeto;
import com.devWebResolv.mannagerSystem.model.StatusProjeto;

import java.util.List;
import java.util.Optional;

public interface ProjetoService {

    /**
     * Salva ou atualiza um projeto aplicando as regras de negócio
     */
    Projeto salvar(Projeto projeto);

    /**
     * Retorna a lista de todos os projetos cadastrados
     */
    List<Projeto> listarTodos();

    /**
     * Busca um projeto pelo seu identificador único
     */
    Optional<Projeto> buscarPorId(Long id);

    /**
     * Remove um projeto pelo seu ID
     */
    void deletar(Long id);

    /**
     * Busca projetos filtrados por status
     */
    List<Projeto> buscarPorStatus(StatusProjeto status);
}
