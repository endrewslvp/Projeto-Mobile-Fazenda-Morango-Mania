package com.example.morangomania.model;

import java.time.LocalDate;

public class Produtos {

    // Atributos
    private int id;  // Identificador único do produto
    private String nome;  // Nome do produto
    private double preco;  // Preço unitário do produto
    private String validade;  // Data de validade do produto em formato String
    private int QtdProduto;  // Quantidade disponível do produto

    // Métodos Getters e Setters para acessar e modificar os atributos

    public int getQtdProduto() {
        return QtdProduto;  // Retorna a quantidade disponível do produto
    }

    public void setQtdProduto(int qtdProduto) {
        QtdProduto = qtdProduto;  // Define a quantidade disponível do produto
    }

    public String getValidade() {
        return validade;  // Retorna a data de validade do produto
    }

    public void setValidade(String validade) {
        this.validade = validade;  // Define a data de validade do produto
    }

    public double getPreco() {
        return preco;  // Retorna o preço unitário do produto
    }

    public void setPreco(double preco) {
        this.preco = preco;  // Define o preço unitário do produto
    }

    public String getNome() {
        return nome;  // Retorna o nome do produto
    }

    public void setNome(String nome) {
        this.nome = nome;  // Define o nome do produto
    }

    public int getId() {
        return id;  // Retorna o id do produto
    }

    public void setId(int id) {
        this.id = id;  // Define o id do produto
    }

}
