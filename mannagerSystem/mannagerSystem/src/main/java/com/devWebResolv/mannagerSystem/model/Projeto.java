package com.devWebResolv.mannagerSystem.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusProjeto status;

    @Column(precision = 10, scale = 2)
    private Double orcamento;

    @Column(name = "data_previsao_entrega")
    private LocalDate dataPrevisaoEntrega;

    // Construtor padrão exigido pelo JPA
    public Projeto() {
    }

    // Construtor completo
    public Projeto(Long id, String titulo, String descricao, StatusProjeto status, Double orcamento, LocalDate dataPrevisaoEntrega) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.orcamento = orcamento;
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    // Construtor auxiliar sem ID (para criação de novos registros)
    public Projeto(String titulo, String descricao, StatusProjeto status, Double orcamento, LocalDate dataPrevisaoEntrega) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.orcamento = orcamento;
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public void setStatus(StatusProjeto status) {
        this.status = status;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public LocalDate getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }

    public void setDataPrevisaoEntrega(LocalDate dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", status=" + status +
                ", orcamento=" + orcamento +
                ", dataPrevisaoEntrega=" + dataPrevisaoEntrega +
                '}';
    }
}
