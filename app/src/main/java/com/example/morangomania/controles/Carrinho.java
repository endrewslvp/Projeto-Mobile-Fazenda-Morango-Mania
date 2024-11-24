package com.example.morangomania.controles;

import com.example.morangomania.model.ProdutoCarrinho;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private static List<ProdutoCarrinho> itensCarrinho = new ArrayList<>();

    public static void adicionarProduto(ProdutoCarrinho produto) {
        itensCarrinho.add(produto);
    }

    public static List<ProdutoCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }

    public static double calcularTotal() {
        double total = 0;
        for (ProdutoCarrinho produto : itensCarrinho) {
            total += produto.getPrecoTotal();
        }
        return total;
    }
}

