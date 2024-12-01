package com.poo.locadora.entities;

public enum CategoriaVeiculo {
    BASICO("B"),
    INTERMEDIARIO("I"),
    LUXO("L");

    private final String codigo;

    CategoriaVeiculo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
