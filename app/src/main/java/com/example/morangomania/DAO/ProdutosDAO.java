package com.example.morangomania.DAO;

import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.model.Produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    // Estabelece uma conexão com o banco de dados
    Connection conn = ConexaoSQL.conectar();

    // Metodo para obter todos os produtos do banco de dados
    public List<Produtos> obterProdutos() throws SQLException {
        // Cria uma lista para armazenar os produtos
        List<Produtos> produtos = new ArrayList<>();

        // Define o formato de data para o campo validade
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Comando SQL para selecionar todos os produtos
        String comando = "SELECT * FROM dbo.Produtos";

        // Cria um Statement para executar a consulta
        Statement st = null;
        st = conn.createStatement();

        // Executa a consulta e obtém o resultado
        ResultSet rs = st.executeQuery(comando);

        // Percorre os resultados da consulta
        while (rs.next()) {
            // Cria um objeto Produto e preenche com os dados do banco
            Produtos produto = new Produtos();
            produto.setId(rs.getInt(1));  // ID do produto
            produto.setNome(rs.getString(2));  // Nome do produto
            produto.setPreco(rs.getDouble(3));  // Preço do produto
            produto.setValidade(rs.getString(4));  // Validade do produto
            produto.setQtdProduto(rs.getInt(5));  // Quantidade disponível do produto

            // Adiciona o produto à lista
            produtos.add(produto);
        }

        // Fecha a conexão com o banco de dados
        conn.close();

        // Retorna a lista de produtos
        return produtos;
    }

    // Método para obter o nome de um produto pelo ID
    public String obterNomeProdutoPorID(int id) throws SQLException {
        // Variável para armazenar o nome do produto
        String nomeProduto = null;

        // Comando SQL para obter o nome do produto com base no ID
        String comando = "SELECT Nome FROM dbo.Produtos WHERE Id = ?";  // Supondo que o nome da coluna seja 'Nome'

        // Prepara a consulta com o parâmetro ID
        PreparedStatement pst = conn.prepareStatement(comando);
        pst.setInt(1, id);

        // Executa a consulta
        ResultSet rs = pst.executeQuery();

        // Se houver um resultado, armazena o nome do produto
        if (rs.next()) {
            nomeProduto = rs.getString("Nome");
        }

        // Fecha o ResultSet e o PreparedStatement
        rs.close();
        pst.close();

        // Retorna o nome do produto encontrado
        return nomeProduto;
    }
}
