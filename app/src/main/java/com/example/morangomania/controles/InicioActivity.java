package com.example.morangomania.controles;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morangomania.DAO.ProdutosDAO;
import com.example.morangomania.R;
import com.example.morangomania.adapter.ProdutosAdapter;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Produtos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private GridView gridView;

//    private TextView usuarioname;
    private ProdutosAdapter produtoAdapter;
    private ProdutosDAO produtoDao;
    private List<Produtos> produtos;
    private List<Produtos> produtosFiltrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        gridView = findViewById(R.id.productGridView);
        EditText searchBar = findViewById(R.id.searchBar);
        produtoDao = new ProdutosDAO();


//        Intent in = getIntent();
//        Cliente cliente =(Cliente) in.getSerializableExtra("Cliente");
//        usuarioname = findViewById(R.id.teste);
//        usuarioname.setText(cliente.getNome());

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

        ImageButton cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> {
            Intent intentCarrinho = new Intent(InicioActivity.this, CarrinhoActivity.class);
            startActivity(intentCarrinho);
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