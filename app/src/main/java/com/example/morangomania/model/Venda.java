package com.example.morangomania.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Venda {

    private int Id;
    private int Id_Cliente;
    private int Id_Produto;
    private int Quantidade;
    private double TotalCompra;
    private String CodigoCompra;
    private String MetodoPagamento;
    private String Endereco;
    private String Status;

    private Map<Integer, Integer> produtosComprados = new HashMap<>();

    public void addProduto(int idProduto, int quantidade) {
        // Se o produto já existe, soma a quantidade
        if (produtosComprados.containsKey(idProduto)) {
            produtosComprados.put(idProduto, produtosComprados.get(idProduto) + quantidade);
        } else {
            produtosComprados.put(idProduto, quantidade);
        }
    }

    public Map<Integer, Integer> getProdutosComprados() {
        return produtosComprados;
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        Id_Cliente = id_Cliente;
    }

    public int getId_Produto() {
        return Id_Produto;
    }

    public void setId_Produto(int id_Produto) {
        Id_Produto = id_Produto;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public double getTotalCompra() {
        return TotalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        TotalCompra = totalCompra;
    }

    public String getCodigoCompra() {
        return CodigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        CodigoCompra = codigoCompra;
    }

    public String getMetodoPagamento() {
        return MetodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        MetodoPagamento = metodoPagamento;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
