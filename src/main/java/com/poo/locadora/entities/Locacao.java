package com.poo.locadora.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column(name = "dia_locacao", nullable = false)
    private LocalDate diaLocacao;

    @Column(name = "dia_devolucao")
    @Null
    private LocalDate diaDevolucao;

    public Locacao() {
    }

    public Locacao(long id, Cliente cliente, Veiculo veiculo, LocalDate diaLocacao, LocalDate diaDevolucao) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.diaLocacao = diaLocacao;
        this.diaDevolucao = diaDevolucao;
    }

    // Getters e Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDiaLocacao() {
        return diaLocacao;
    }

    public void setDiaLocacao(LocalDate diaLocacao) {
        this.diaLocacao = diaLocacao;
    }

    public LocalDate getDiaDevolucao() {
        return diaDevolucao;
    }

    public void setDiaDevolucao(LocalDate diaDevolucao) {
        this.diaDevolucao = diaDevolucao;
    }
}
