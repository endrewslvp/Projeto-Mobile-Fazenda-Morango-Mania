package com.example.morangomania.DAO;

import android.widget.Toast;

import com.example.morangomania.conexao.ConexaoSQL;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;
import com.example.morangomania.model.Produtos;
import com.example.morangomania.model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VendaDAO {

    Connection conn = ConexaoSQL.conectar();

    public void registrarVenda(Cliente cliente, List<ProdutoCarrinho> produtosCarrinho, Endereco enderecoEntrega, String metodoPagamento) throws SQLException {

        PreparedStatement statement = null;
        PreparedStatement updateStatement = null;
        // Gera um código de compra único
        String codigoCompra = UUID.randomUUID().toString();

        // Loop para inserir cada item do carrinho no banco

        for (ProdutoCarrinho produto : produtosCarrinho) {
            String sql = "INSERT INTO dbo.Vendas (Id_Cliente, ID_Produto, Quantidade, TotalCompra, CodigoCompra, MetodoPagamento, Endereco) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, cliente.getId());
            statement.setInt(2, produto.getId());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, produto.getPrecoTotal());
            statement.setString(5, codigoCompra);
            statement.setString(6, metodoPagamento);
            statement.setString(7, enderecoEntrega.toString());

            statement.executeUpdate();

            String updateSQL = "UPDATE dbo.Produtos SET QtdProduto = QtdProduto - ? WHERE Id = ?";
            updateStatement = conn.prepareStatement(updateSQL);
            updateStatement.setInt(1, produto.getQuantidade());
            updateStatement.setInt(2, produto.getId());

            updateStatement.executeUpdate();
        }

        conn.close();

    }

    public List<Venda> obterVendas(int id, String status) throws SQLException {

        if (status == null) {
            status = "";  // ou outro valor default
        }
        List<Venda> vendas = new ArrayList<>();

        String comando = "SELECT * FROM dbo.Vendas WHERE Id = ?, Status = ?";

        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os valores dos parâmetros
        pst.setInt(1, id);
        pst.setString(2, status);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Venda venda = new Venda();
            venda.setId(rs.getInt(1));
            venda.setId_Cliente(rs.getInt(2));
            venda.setId_Produto(rs.getInt(3));
            venda.setQuantidade(rs.getInt(4));
            venda.setTotalCompra(rs.getDouble(5));
            venda.setCodigoCompra(rs.getString(6));
            venda.setMetodoPagamento(rs.getString(7));
            venda.setEndereco(rs.getString(8));
            venda.setStatus(rs.getString(9));
            vendas.add(venda);
        }
        conn.close();

        return vendas;
    }
}


