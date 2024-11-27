package com.example.morangomania.DAO;

import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;
import com.example.morangomania.model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String, List<Venda>> carregarVendas(int clienteId) {
        Map<String, List<Venda>> listasVendas = new HashMap<>();
        List<Venda> listaAPreparar = new ArrayList<>();
        List<Venda> listaEntregue = new ArrayList<>();

        Connection conn = ConexaoSQL.conectar();
        String query = "SELECT * FROM dbo.Vendas WHERE Id_Cliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clienteId);  // Filtra pelo ID do cliente
            ResultSet rs = stmt.executeQuery();

            // Mapa para agrupar vendas pelo código de compra
            Map<String, Venda> mapaVendas = new HashMap<>();

            while (rs.next()) {
                String codigoCompra = rs.getString("CodigoCompra");
                double totalCompra = rs.getDouble("TotalCompra");
                int idProduto = rs.getInt("ID_Produto");
                String metodoPagamento = rs.getString("MetodoPagamento");
                String endereco = rs.getString("Endereco");

                int quantidade = rs.getInt("Quantidade");  // Captura a quantidade comprada

                if (mapaVendas.containsKey(codigoCompra)) {
                    // Venda já existente, atualiza total e adiciona o produto com a quantidade
                    Venda vendaExistente = mapaVendas.get(codigoCompra);
                    vendaExistente.setTotalCompra(vendaExistente.getTotalCompra() + totalCompra);
                    vendaExistente.addProduto(idProduto, quantidade);
                } else {
                    // Cria uma nova venda agrupada
                    Venda novaVenda = new Venda();
                    novaVenda.setCodigoCompra(codigoCompra);
                    novaVenda.setTotalCompra(totalCompra);
                    novaVenda.setMetodoPagamento(metodoPagamento);
                    novaVenda.setEndereco(endereco);
                    novaVenda.addProduto(idProduto, quantidade);
                    novaVenda.setStatus(rs.getString("Situacao"));

                    mapaVendas.put(codigoCompra, novaVenda);
                }
            }

            // Converte o mapa em listas separadas
            for (Venda venda : mapaVendas.values()) {
                if ("A preparar".equals(venda.getStatus())) {
                    listaAPreparar.add(venda);
                } else if ("Entregue".equals(venda.getStatus())) {
                    listaEntregue.add(venda);
                }
            }

            listasVendas.put("A preparar", listaAPreparar);
            listasVendas.put("Entregue", listaEntregue);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listasVendas;
    }
}


