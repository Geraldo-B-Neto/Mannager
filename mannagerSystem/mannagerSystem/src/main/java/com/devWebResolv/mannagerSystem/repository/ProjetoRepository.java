package com.devWebResolv.mannagerSystem.repository;

import com.devWebResolv.mannagerSystem.model.Projeto;
import com.devWebResolv.mannagerSystem.model.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    /**
     * Busca projetos filtrando por status
     */
    List<Projeto> findByStatus(StatusProjeto status);

    /**
     * Busca projetos cujo título contenha o termo informado (case-insensitive)
     */
    List<Projeto> findByTituloContainingIgnoreCase(String titulo);
}
