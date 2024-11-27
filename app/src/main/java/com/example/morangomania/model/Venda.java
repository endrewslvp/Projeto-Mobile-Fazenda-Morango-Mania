package com.example.morangomania.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Venda {

    // Atributos principais da venda
    private int Id;               // ID da venda
    private int Id_Cliente;       // ID do cliente que realizou a compra
    private int Id_Produto;       // ID do produto comprado
    private int Quantidade;      // Quantidade do produto comprado
    private double TotalCompra;   // Total da compra
    private String CodigoCompra;  // Código de identificação da compra
    private String MetodoPagamento; // Metodo de pagamento utilizado
    private String Endereco;      // Endereço de entrega
    private String Status;        // Status da venda (ex: 'Em processamento', 'Concluído')

    // Mapa para armazenar os produtos comprados e suas quantidades
    private Map<Integer, Integer> produtosComprados = new HashMap<>();

    // Metodo para adicionar um produto ao mapa de produtos comprados
    public void addProduto(int idProduto, int quantidade) {
        // Se o produto já existe, soma a quantidade
        if (produtosComprados.containsKey(idProduto)) {
            produtosComprados.put(idProduto, produtosComprados.get(idProduto) + quantidade);
        } else {
            produtosComprados.put(idProduto, quantidade); // Caso contrário, adiciona um novo produto
        }
    }

    // Retorna o mapa com os produtos comprados
    public Map<Integer, Integer> getProdutosComprados() {
        return produtosComprados;
    }

    // Métodos de acesso e modificação para os atributos da classe

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
