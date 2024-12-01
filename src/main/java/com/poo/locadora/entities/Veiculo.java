package com.poo.locadora.entities;

import jakarta.persistence.*;
import java.time.Year;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // A ID continua sendo a chave primária para a persistência no banco

    @Column(nullable = false, length = 10, unique = true)
    private String placa; // A placa será única, mas não é a chave primária

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false, length = 50)
    private String cor;

    @Column(name = "ano_fabricacao", nullable = false)
    private Year anoFabricacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_veiculo", nullable = false)
    private CategoriaVeiculo categoriaVeiculo;

    @Column(nullable = false)
    private int quantidade; // Quantidade de veículos deste modelo disponível

    public Veiculo() {
    }

    public Veiculo(String placa, String marca, String modelo, String cor, Year anoFabricacao, CategoriaVeiculo categoriaVeiculo, int quantidade) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.categoriaVeiculo = categoriaVeiculo;
        this.quantidade = quantidade;
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Year getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Year anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public CategoriaVeiculo getCategoriaVeiculo() {
        return categoriaVeiculo;
    }

    public void setCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) {
        this.categoriaVeiculo = categoriaVeiculo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString(){
        return "Veículo: " + marca + " " + modelo + " " + anoFabricacao + " " + cor + "\n" +
                "Placa: " + placa + "\n" +
                "Categoria: " + categoriaVeiculo + "\n" +
                "Quantidade disponível: " + quantidade;
    }
}
