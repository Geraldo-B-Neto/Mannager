package com.devWebResolv.mannagerSystem.service.impl;

import com.devWebResolv.mannagerSystem.model.Projeto;
import com.devWebResolv.mannagerSystem.model.StatusProjeto;
import com.devWebResolv.mannagerSystem.repository.ProjetoRepository;
import com.devWebResolv.mannagerSystem.service.ProjetoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;

    // Injeção de dependência via construtor (recomendada pelo Spring)
    public ProjetoServiceImpl(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    @Override
    @Transactional
    public Projeto salvar(Projeto projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("O projeto não pode ser nulo.");
        }

        // Validação de negócio: Título obrigatório e não vazio
        if (projeto.getTitulo() == null || projeto.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do projeto é obrigatório e não pode ser vazio.");
        }

        // Validação de negócio: Orçamento não pode ser negativo
        if (projeto.getOrcamento() != null && projeto.getOrcamento() < 0) {
            throw new IllegalArgumentException("O orçamento do projeto não pode ser negativo.");
        }

        // Regra de negócio: Se o status não for informado, define como EM_PLANEJAMENTO por padrão
        if (projeto.getStatus() == null) {
            projeto.setStatus(StatusProjeto.EM_PLANEJAMENTO);
        }

        return projetoRepository.save(projeto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Projeto> buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID informado não pode ser nulo.");
        }
        return projetoRepository.findById(id);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID informado não pode ser nulo.");
        }
        if (!projetoRepository.existsById(id)) {
            throw new IllegalArgumentException("Projeto com ID " + id + " não encontrado para exclusão.");
        }
        projetoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projeto> buscarPorStatus(StatusProjeto status) {
        if (status == null) {
            return listarTodos();
        }
        return projetoRepository.findByStatus(status);
    }
}
