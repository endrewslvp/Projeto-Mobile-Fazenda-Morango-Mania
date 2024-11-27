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

    // Estabelece uma conexão com o banco de dados
    Connection conn = ConexaoSQL.conectar();

    // Método para registrar uma venda
    public void registrarVenda(Cliente cliente, List<ProdutoCarrinho> produtosCarrinho, Endereco enderecoEntrega, String metodoPagamento) throws SQLException {

        PreparedStatement statement = null;
        PreparedStatement updateStatement = null;

        // Gera um código único para a compra
        String codigoCompra = UUID.randomUUID().toString();

        // Loop para inserir cada produto do carrinho no banco de dados
        for (ProdutoCarrinho produto : produtosCarrinho) {
            // Comando SQL para inserir uma nova venda
            String sql = "INSERT INTO dbo.Vendas (Id_Cliente, ID_Produto, Quantidade, TotalCompra, CodigoCompra, MetodoPagamento, Endereco) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, cliente.getId());  // ID do cliente
            statement.setInt(2, produto.getId());  // ID do produto
            statement.setInt(3, produto.getQuantidade());  // Quantidade comprada
            statement.setDouble(4, produto.getPrecoTotal());  // Total da compra
            statement.setString(5, codigoCompra);  // Código da compra (gerado anteriormente)
            statement.setString(6, metodoPagamento);  // Método de pagamento
            statement.setString(7, enderecoEntrega.toString());  // Endereço de entrega

            // Executa a inserção da venda no banco
            statement.executeUpdate();

            // Comando SQL para atualizar a quantidade do produto no estoque
            String updateSQL = "UPDATE dbo.Produtos SET QtdProduto = QtdProduto - ? WHERE Id = ?";
            updateStatement = conn.prepareStatement(updateSQL);
            updateStatement.setInt(1, produto.getQuantidade());  // Quantidade a ser subtraída do estoque
            updateStatement.setInt(2, produto.getId());  // ID do produto

            // Executa a atualização do estoque
            updateStatement.executeUpdate();
        }

        // Fecha a conexão com o banco de dados
        conn.close();
    }

    // Método para carregar as vendas de um cliente
    public Map<String, List<Venda>> carregarVendas(int clienteId) {
        // Mapa para armazenar as listas de vendas separadas por status
        Map<String, List<Venda>> listasVendas = new HashMap<>();
        List<Venda> listaAPreparar = new ArrayList<>();
        List<Venda> listaEntregue = new ArrayList<>();

        // Conexão com o banco de dados
        Connection conn = ConexaoSQL.conectar();

        // Consulta SQL para obter todas as vendas do cliente
        String query = "SELECT * FROM dbo.Vendas WHERE Id_Cliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clienteId);  // Filtra as vendas pelo ID do cliente
            ResultSet rs = stmt.executeQuery();

            // Mapa para agrupar as vendas pelo código de compra
            Map<String, Venda> mapaVendas = new HashMap<>();

            // Processa cada venda retornada pela consulta
            while (rs.next()) {
                String codigoCompra = rs.getString("CodigoCompra");  // Código único da compra
                double totalCompra = rs.getDouble("TotalCompra");  // Total da compra
                int idProduto = rs.getInt("ID_Produto");  // ID do produto
                String metodoPagamento = rs.getString("MetodoPagamento");  // Método de pagamento
                String endereco = rs.getString("Endereco");  // Endereço de entrega
                int quantidade = rs.getInt("Quantidade");  // Quantidade comprada

                // Se já existe uma venda com o mesmo código, atualiza o total e adiciona o produto
                if (mapaVendas.containsKey(codigoCompra)) {
                    Venda vendaExistente = mapaVendas.get(codigoCompra);
                    vendaExistente.setTotalCompra(vendaExistente.getTotalCompra() + totalCompra);  // Atualiza o total da compra
                    vendaExistente.addProduto(idProduto, quantidade);  // Adiciona o produto à venda
                } else {
                    // Cria uma nova venda e adiciona o primeiro produto
                    Venda novaVenda = new Venda();
                    novaVenda.setCodigoCompra(codigoCompra);
                    novaVenda.setTotalCompra(totalCompra);
                    novaVenda.setMetodoPagamento(metodoPagamento);
                    novaVenda.setEndereco(endereco);
                    novaVenda.addProduto(idProduto, quantidade);  // Adiciona o produto à venda
                    novaVenda.setStatus(rs.getString("Situacao"));  // Define o status da venda

                    mapaVendas.put(codigoCompra, novaVenda);  // Adiciona a nova venda ao mapa
                }
            }

            // Converte o mapa de vendas em listas separadas por status
            for (Venda venda : mapaVendas.values()) {
                if ("A preparar".equals(venda.getStatus())) {
                    listaAPreparar.add(venda);  // Adiciona à lista de vendas a preparar
                } else if ("Entregue".equals(venda.getStatus())) {
                    listaEntregue.add(venda);  // Adiciona à lista de vendas entregues
                }
            }

            // Coloca as listas de vendas no mapa final
            listasVendas.put("A preparar", listaAPreparar);
            listasVendas.put("Entregue", listaEntregue);

        } catch (SQLException e) {
            e.printStackTrace();  // Imprime o erro caso ocorra uma exceção
        }

        // Retorna o mapa com as vendas agrupadas por status
        return listasVendas;
    }
}
