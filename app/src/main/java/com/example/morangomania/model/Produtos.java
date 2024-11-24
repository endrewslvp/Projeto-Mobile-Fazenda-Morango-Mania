package com.example.morangomania.model;

import java.time.LocalDate;

public class Produtos {

    private int id;
    private String nome;
    private double preco;
    private String validade;
    private int QtdProduto;
    public int getQtdProduto() {
        return QtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        QtdProduto = qtdProduto;
    }

    public String  getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
