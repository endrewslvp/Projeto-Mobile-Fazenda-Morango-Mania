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

    private ProdutosAdapter produtoAdapter;
    private List<Produtos> produtos;
    private List<Produtos> produtosFiltrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        GridView gridView = findViewById(R.id.productGridView);
        EditText searchBar = findViewById(R.id.searchBar);
        TextView tvWelcome = findViewById(R.id.tvWelcome);

        ProdutosDAO produtoDao = new ProdutosDAO();

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        tvWelcome.setText("Bem-vindo, "+cliente.getNome());

        try {
            produtos = produtoDao.obterProdutos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        produtosFiltrados = new ArrayList<>(produtos);  // Inicializa a lista filtrada
        produtoAdapter = new ProdutosAdapter(this, produtosFiltrados);
        gridView.setAdapter(produtoAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Produtos produtoSelecionado = produtosFiltrados.get(position);  // Obtém o produto clicado

            // Verifique se o valor de validade não é nulo antes de enviar
            String validade = produtoSelecionado.getValidade();
            if (validade == null) {
                validade = "Data não disponível";  // Define um valor padrão se for nulo
            }

            Intent intent = new Intent(InicioActivity.this, DetalhesProdutoActivity.class);
            intent.putExtra("id",produtoSelecionado.getId());
            intent.putExtra("nome", produtoSelecionado.getNome());
            intent.putExtra("preco", produtoSelecionado.getPreco());
            intent.putExtra("validade", produtoSelecionado.getValidade());
            intent.putExtra("estoque", produtoSelecionado.getQtdProduto());  // Passa o estoque
            startActivity(intent);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarProdutos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(InicioActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });

        ImageButton cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> {
            Intent intentCarrinho = new Intent(InicioActivity.this, CarrinhoActivity.class);
            intentCarrinho.putExtra("Cliente",cliente);
            startActivity(intentCarrinho);
        });

        ImageButton pedidosButton = findViewById(R.id.pedidosButton);
        pedidosButton.setOnClickListener(v -> {
            Intent intentPedidos = new Intent(InicioActivity.this, VendasActivity.class);
            intentPedidos.putExtra("Cliente",cliente);
            startActivity(intentPedidos);
        });
    }

    private void filtrarProdutos(String texto) {
        if (produtos == null) return;

        produtosFiltrados.clear();
        if (texto.isEmpty()) {
            produtosFiltrados.addAll(produtos);
        } else {
            for (Produtos produto : produtos) {
                if (produto.getNome() != null && produto.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    produtosFiltrados.add(produto);
                }
            }
        }
        produtoAdapter.notifyDataSetChanged();
    }
}
