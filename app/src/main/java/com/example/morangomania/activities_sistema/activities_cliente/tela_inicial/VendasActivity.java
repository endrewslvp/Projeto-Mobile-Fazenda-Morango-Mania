// Esta classe implementa a tela de visualização das vendas de um cliente.
// As vendas são carregadas do banco de dados e agrupadas em duas listas: "A preparar" e "Entregue".
// As listas são exibidas em dois RecyclerViews separados, permitindo ao usuário acessar o perfil
// ou retornar à tela anterior.

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
    // Declaração dos RecyclerViews e Adapters
    private RecyclerView recyclerAPreparar, recyclerEntregue;
    private VendaAdapter adapterAPreparar, adapterEntregue;
    private List<Venda> listaAPreparar = new ArrayList<>();
    private List<Venda> listaEntregue = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas); // Define o layout da tela de vendas

        // Inicializa os RecyclerViews
        recyclerAPreparar = findViewById(R.id.recyclerAPreparar);
        recyclerEntregue = findViewById(R.id.recyclerEntregue);

        // Define o layout linear para as listas
        recyclerAPreparar.setLayoutManager(new LinearLayoutManager(this));
        recyclerEntregue.setLayoutManager(new LinearLayoutManager(this));

        // Recupera o objeto Cliente da Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");

        // Carrega os dados das vendas do banco de dados usando o DAO
        VendaDAO vendaDAO = new VendaDAO();
        Map<String, List<Venda>> listasVendas = vendaDAO.carregarVendas(cliente.getId());

        // Atribui as listas retornadas aos adaptadores
        listaAPreparar = listasVendas.get("A preparar");
        listaEntregue = listasVendas.get("Entregue");

        adapterAPreparar = new VendaAdapter(listaAPreparar); // Adapter para vendas "A preparar"
        adapterEntregue = new VendaAdapter(listaEntregue);   // Adapter para vendas "Entregue"

        // Define os adapters nos RecyclerViews
        recyclerAPreparar.setAdapter(adapterAPreparar);
        recyclerEntregue.setAdapter(adapterEntregue);

        // Botão para acessar o perfil do usuário
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(VendasActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile); // Abre a tela de perfil do usuário
        });

        // Botão para retornar à tela anterior
        Button btnVoltarUsuario = findViewById(R.id.btnVoltarPedidos);
        btnVoltarUsuario.setOnClickListener(v -> finish()); // Fecha a atividade atual
    }

    // Método para carregar vendas do banco de dados
    private void carregarVendas(int id) {
        Connection conn = ConexaoSQL.conectar(); // Conecta ao banco de dados
        String query = "SELECT * FROM dbo.Vendas WHERE Id_Cliente = ?"; // Consulta SQL

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id); // Filtra vendas pelo ID do cliente
            ResultSet rs = stmt.executeQuery(); // Executa a consulta

            // Mapa para agrupar vendas pelo código de compra
            Map<String, Venda> mapaVendas = new HashMap<>();

            while (rs.next()) {
                // Captura os dados da venda
                String codigoCompra = rs.getString("CodigoCompra");
                double totalCompra = rs.getDouble("TotalCompra");
                int idProduto = rs.getInt("ID_Produto");
                String metodoPagamento = rs.getString("MetodoPagamento");
                String endereco = rs.getString("Endereco");
                int quantidade = rs.getInt("Quantidade");

                if (mapaVendas.containsKey(codigoCompra)) {
                    // Se a venda já existe no mapa, atualiza total e produtos
                    Venda vendaExistente = mapaVendas.get(codigoCompra);
                    vendaExistente.setTotalCompra(vendaExistente.getTotalCompra() + totalCompra);
                    vendaExistente.addProduto(idProduto, quantidade);
                } else {
                    // Cria uma nova venda se não estiver no mapa
                    Venda novaVenda = new Venda();
                    novaVenda.setCodigoCompra(codigoCompra);
                    novaVenda.setTotalCompra(totalCompra);
                    novaVenda.setMetodoPagamento(metodoPagamento);
                    novaVenda.setEndereco(endereco);
                    novaVenda.addProduto(idProduto, quantidade);
                    novaVenda.setStatus(rs.getString("Situacao"));

                    mapaVendas.put(codigoCompra, novaVenda); // Adiciona ao mapa
                }
            }

            // Converte o mapa em listas separadas por status
            for (Venda venda : mapaVendas.values()) {
                if ("A preparar".equals(venda.getStatus())) {
                    listaAPreparar.add(venda);
                } else if ("Entregue".equals(venda.getStatus())) {
                    listaEntregue.add(venda);
                }
            }

            // Atualiza os adaptadores com os dados carregados
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
