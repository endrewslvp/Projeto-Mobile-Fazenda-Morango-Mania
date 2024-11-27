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


    Connection conn = ConexaoSQL.conectar();

    public List<Produtos> obterProdutos() throws SQLException {
        List<Produtos> produtos = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String comando = "SELECT * FROM dbo.Produtos";

        Statement st = null;

        st = conn.createStatement();

        ResultSet rs = st.executeQuery(comando);

        while (rs.next()) {
            Produtos produto = new Produtos();
            produto.setId(rs.getInt(1));
            produto.setNome(rs.getString(2));
            produto.setPreco(rs.getDouble(3));
            produto.setValidade(rs.getString(4));
            produto.setQtdProduto(rs.getInt(5));
            produtos.add(produto);
        }
        conn.close();

        return produtos;
    }

    public String obterNomeProdutoPorID(int id) throws SQLException {
        String nomeProduto = null;
        String comando = "SELECT Nome FROM dbo.Produtos WHERE Id = ?";  // Supondo que o nome da coluna seja 'Nome'

        PreparedStatement pst = conn.prepareStatement(comando);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {  // Verifica se há resultado
            nomeProduto = rs.getString("Nome");
        }

        rs.close();  // Fecha o ResultSet
        pst.close();  // Fecha o PreparedStatement

        return nomeProduto;
    }



}
