package com.example.morangomania.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    // Lista estática que armazena os itens no carrinho
    private static List<ProdutoCarrinho> itensCarrinho = new ArrayList<>();

    // Variável estática que armazena o preço total dos itens no carrinho
    private static double precoTotal;

    // Metodo para adicionar um produto ao carrinho
    public static void adicionarProduto(ProdutoCarrinho produto) {
        itensCarrinho.add(produto);  // Adiciona o produto na lista de itens no carrinho
    }

    // Metodo para obter todos os itens do carrinho
    public static List<ProdutoCarrinho> getItensCarrinho() {
        return itensCarrinho;  // Retorna a lista de itens no carrinho
    }

    // Metodo para limpar o carrinho
    public static void limparCarrinho() {
        itensCarrinho.clear();  // Limpa todos os itens do carrinho
    }
}
