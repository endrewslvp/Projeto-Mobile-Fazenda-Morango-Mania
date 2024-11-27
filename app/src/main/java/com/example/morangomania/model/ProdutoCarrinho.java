package com.example.morangomania.model;

import android.graphics.Bitmap;

public class ProdutoCarrinho {

    // Atributos
    private int id;  // Identificador único do produto
    private String nome;  // Nome do produto
    private double preco;  // Preço unitário do produto
    private int quantidade;  // Quantidade do produto no carrinho
    private double precoTotal;  // Preço total (preço unitário * quantidade)
    private Bitmap imagem;  // Imagem do produto

    // Construtor que inicializa o produto no carrinho
    public ProdutoCarrinho(int id, String nome, double preco, int quantidade) {
        this.id = id;  // Define o id do produto
        this.nome = nome;  // Define o nome do produto
        this.preco = preco;  // Define o preço do produto
        this.quantidade = quantidade;  // Define a quantidade do produto
        this.precoTotal = preco * quantidade;  // Calcula o preço total
    }

    // Métodos getters e setters para acessar e modificar os atributos

    public int getId() {
        return id;  // Retorna o id do produto
    }

    public void setId(int id) {
        this.id = id;  // Define o id do produto
    }

    public String getNome() {
        return nome;  // Retorna o nome do produto
    }

    public void setNome(String nome) {
        this.nome = nome;  // Define o nome do produto
    }

    public double getPreco() {
        return preco;  // Retorna o preço unitário do produto
    }

    public void setPreco(double preco) {
        this.preco = preco;  // Define o preço unitário do produto
    }

    public int getQuantidade() {
        return quantidade;  // Retorna a quantidade do produto no carrinho
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;  // Define a quantidade do produto
        this.precoTotal = this.preco * this.quantidade;  // Recalcula o preço total
    }

    public double getPrecoTotal() {
        return precoTotal;  // Retorna o preço total (preço unitário * quantidade)
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;  // Define o preço total
    }

    public Bitmap getImagem() {
        return imagem;  // Retorna a imagem do produto
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;  // Define a imagem do produto
    }
}
