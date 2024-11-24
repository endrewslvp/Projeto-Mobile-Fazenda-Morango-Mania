package com.example.morangomania.controles;

import com.example.morangomania.model.ProdutoCarrinho;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private static List<ProdutoCarrinho> itensCarrinho = new ArrayList<>();
    private static double precoTotal;

    public static void adicionarProduto(ProdutoCarrinho produto) {
        itensCarrinho.add(produto);
    }

    public static List<ProdutoCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }

    // Novo método para limpar o carrinho
    public static void limparCarrinho() {
        itensCarrinho.clear();
    }
}

