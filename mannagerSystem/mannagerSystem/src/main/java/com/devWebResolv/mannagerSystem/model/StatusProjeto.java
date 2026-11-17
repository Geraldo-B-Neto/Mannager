package com.devWebResolv.mannagerSystem.model;

public enum StatusProjeto {
    EM_PLANEJAMENTO("Em Planejamento"),
    EM_ANDAMENTO("Em Andamento"),
    EM_TESTES("Em Testes"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusProjeto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
