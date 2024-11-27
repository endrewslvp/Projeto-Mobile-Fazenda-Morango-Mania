// Esta classe implementa a tela inicial do aplicativo, exibindo uma lista de produtos em um GridView.
// O usuário pode buscar produtos em tempo real, visualizar detalhes dos produtos, acessar o perfil,
// verificar o carrinho ou consultar pedidos. Os produtos são carregados do banco de dados e exibidos de forma dinâmica.

package com.example.morangomania.activities_sistema.activities_cliente.tela_inicial;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.ProdutosDAO;
import com.example.morangomania.R;
import com.example.morangomania.adapter.ProdutosAdapter;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.activities_sistema.activities_cliente.tela_inicial.detalhes.DetalhesProdutoActivity;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Produtos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private ProdutosAdapter produtoAdapter; // Adaptador para o GridView
    private List<Produtos> produtos; // Lista de produtos do banco de dados
    private List<Produtos> produtosFiltrados; // Lista filtrada para busca

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio); // Define o layout da tela

        // Inicializa os componentes da interface
        GridView gridView = findViewById(R.id.productGridView);
        EditText searchBar = findViewById(R.id.searchBar);
        TextView tvWelcome = findViewById(R.id.tvWelcome);

        // Recupera o objeto Cliente passado pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        tvWelcome.setText("Bem-vindo, " + cliente.getNome()); // Mostra mensagem de boas-vindas

        // Carrega a lista de produtos do banco de dados
        ProdutosDAO produtoDao = new ProdutosDAO();
        try {
            produtos = produtoDao.obterProdutos(); // Obtem a lista de produtos
        } catch (SQLException e) {
            throw new RuntimeException(e); // Trata erros de conexão
        }

        produtosFiltrados = new ArrayList<>(produtos); // Inicializa a lista filtrada
        produtoAdapter = new ProdutosAdapter(this, produtosFiltrados); // Configura o adaptador
        gridView.setAdapter(produtoAdapter); // Associa o adaptador ao GridView

        // Configura o clique em um item do GridView para abrir detalhes do produto
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Produtos produtoSelecionado = produtosFiltrados.get(position); // Produto selecionado

            // Verifica se a validade está nula e define um valor padrão
            String validade = produtoSelecionado.getValidade();
            if (validade == null) {
                validade = "Data não disponível";
            }

            // Cria uma Intent para abrir a tela de detalhes do produto
            Intent intent = new Intent(InicioActivity.this, DetalhesProdutoActivity.class);
            intent.putExtra("id", produtoSelecionado.getId());
            intent.putExtra("nome", produtoSelecionado.getNome());
            intent.putExtra("preco", produtoSelecionado.getPreco());
            intent.putExtra("validade", validade);
            intent.putExtra("estoque", produtoSelecionado.getQtdProduto()); // Passa o estoque
            startActivity(intent);
        });

        // Configura a barra de busca para filtrar produtos em tempo real
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarProdutos(s.toString()); // Filtra os produtos conforme o texto digitado
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Botao para abrir o perfil do usuario
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(InicioActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile);
        });

        // Botao para acessar o carrinho
        ImageButton cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> {
            Intent intentCarrinho = new Intent(InicioActivity.this, CarrinhoActivity.class);
            intentCarrinho.putExtra("Cliente", cliente);
            startActivity(intentCarrinho);
        });

        // Botao para abrir a tela de pedidos
        ImageButton pedidosButton = findViewById(R.id.pedidosButton);
        pedidosButton.setOnClickListener(v -> {
            Intent intentPedidos = new Intent(InicioActivity.this, VendasActivity.class);
            intentPedidos.putExtra("Cliente", cliente);
            startActivity(intentPedidos);
        });
    }

    // Metodo para filtrar os produtos com base na barra de busca
    private void filtrarProdutos(String texto) {
        if (produtos == null) return; // Verifica se a lista está nula

        produtosFiltrados.clear(); // Limpa a lista filtrada
        if (texto.isEmpty()) {
            produtosFiltrados.addAll(produtos); // Mostra todos os produtos se a busca estiver vazia
        } else {
            // Adiciona apenas os produtos que correspondem à busca
            for (Produtos produto : produtos) {
                if (produto.getNome() != null && produto.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    produtosFiltrados.add(produto);
                }
            }
        }
        produtoAdapter.notifyDataSetChanged(); // Atualiza o GridView
    }
}
