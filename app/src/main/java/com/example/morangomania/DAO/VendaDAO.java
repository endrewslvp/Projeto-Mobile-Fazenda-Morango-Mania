package com.example.morangomania.DAO;

import android.widget.Toast;

import com.example.morangomania.conexao.ConexaoSQL;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class VendaDAO {

    Connection conn = ConexaoSQL.conectar();

    public void registrarVenda(Cliente cliente, List<ProdutoCarrinho> produtosCarrinho, Endereco enderecoEntrega,Double valorTotal,String metodoPagamento) throws SQLException {

        PreparedStatement statement = null;
        // Gera um código de compra único
        String codigoCompra = UUID.randomUUID().toString();

        // Loop para inserir cada item do carrinho no banco

        for (ProdutoCarrinho produto : produtosCarrinho) {
            String sql = "INSERT INTO Vendas (Id_Cliente, ID_Produto, Quantidade, TotalCompra, CodigoCompra, MetodoPagamento, Endereco) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, cliente.getId());
            statement.setInt(2, produto.getId());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, valorTotal);
            statement.setString(5, codigoCompra);
            statement.setString(6, metodoPagamento);
            statement.setString(7, enderecoEntrega.toString());

            statement.executeUpdate();
        }
    }
}
