package com.example.morangomania.activities_sistema.activities_cliente.tela_inicial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangomania.DAO.VendaDAO;
import com.example.morangomania.R;
import com.example.morangomania.adapter.VendaAdapter;
import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendasActivity extends AppCompatActivity {
    private RecyclerView recyclerAPreparar, recyclerEntregue;
    private VendaAdapter adapterAPreparar, adapterEntregue;
    private List<Venda> listaAPreparar = new ArrayList<>();
    private List<Venda> listaEntregue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        recyclerAPreparar = findViewById(R.id.recyclerAPreparar);
        recyclerEntregue = findViewById(R.id.recyclerEntregue);

        recyclerAPreparar.setLayoutManager(new LinearLayoutManager(this));
        recyclerEntregue.setLayoutManager(new LinearLayoutManager(this));

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");

        // Carregar dados do banco através do VendaDAO
        VendaDAO vendaDAO = new VendaDAO();
        Map<String, List<Venda>> listasVendas = vendaDAO.carregarVendas(cliente.getId());

        listaAPreparar = listasVendas.get("A preparar");
        listaEntregue = listasVendas.get("Entregue");

        adapterAPreparar = new VendaAdapter(listaAPreparar);
        adapterEntregue = new VendaAdapter(listaEntregue);

        recyclerAPreparar.setAdapter(adapterAPreparar);
        recyclerEntregue.setAdapter(adapterEntregue);

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(VendasActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });

        Button btnVoltarUsuario = findViewById(R.id.btnVoltarPedidos);
        btnVoltarUsuario.setOnClickListener(v -> {finish();});
    }



    private void carregarVendas(int id) {
        Connection conn = ConexaoSQL.conectar();
        String query = "SELECT * FROM dbo.Vendas WHERE Id_Cliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);  // Filtra pelo ID do cliente
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

            adapterAPreparar = new VendaAdapter(listaAPreparar);
            adapterEntregue = new VendaAdapter(listaEntregue);

            recyclerAPreparar.setAdapter(adapterAPreparar);
            recyclerEntregue.setAdapter(adapterEntregue);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao carregar vendas", Toast.LENGTH_SHORT).show();
        }
    }

}
