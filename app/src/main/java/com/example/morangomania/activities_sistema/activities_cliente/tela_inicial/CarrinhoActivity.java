package com.example.morangomania.activities_sistema.activities_cliente.tela_inicial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.adapter.CarrinhoAdapter;
import com.example.morangomania.model.Carrinho;
import com.example.morangomania.activities_sistema.activities_cliente.compra.PagamentoActivity;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.ProdutoCarrinho;

import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    private ListView listViewCarrinho;
    private CarrinhoAdapter carrinhoAdapter;
    private TextView tvTotalCompra;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");

        listViewCarrinho = findViewById(R.id.listViewCarrinho);
        tvTotalCompra = findViewById(R.id.tvTotalCompra);
        btnContinuar = findViewById(R.id.btnContinuar);
        Button continueButton = findViewById(R.id.btnContinuar);
        LinearLayout llCarrinhoVazio = findViewById(R.id.llCarrinhoVazio);

        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();

        carrinhoAdapter = new CarrinhoAdapter(this, produtosCarrinho, tvTotalCompra, btnContinuar);
        listViewCarrinho.setAdapter(carrinhoAdapter);

        // Verifica se há itens no carrinho
        if (carrinhoAdapter.isEmpty()) {
            listViewCarrinho.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
            tvTotalCompra.setVisibility(View.GONE);
            llCarrinhoVazio.setVisibility(View.VISIBLE);
        } else {
            listViewCarrinho.setVisibility(View.VISIBLE);
            llCarrinhoVazio.setVisibility(View.GONE);
        }

        continueButton.setOnClickListener(v -> {
            // Calcula o valor total do carrinho
            double totalCompra = calcularTotalCompra();

            // Cria um Intent e passa o valor total como extra
            Intent intentCarrinho = new Intent(CarrinhoActivity.this, PagamentoActivity.class);
            intentCarrinho.putExtra("Cliente",cliente);
            intentCarrinho.putExtra("TOTAL_COMPRA", totalCompra);
            startActivity(intentCarrinho);
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(CarrinhoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });

        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intentCarrinho = new Intent(CarrinhoActivity.this, InicioActivity.class);
            intentCarrinho.putExtra("Cliente",cliente);
            startActivity(intentCarrinho);
        });
    }

    private double calcularTotalCompra() {
        double total = 0;
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();  // Lista de produtos do carrinho
        for (ProdutoCarrinho produto : produtosCarrinho) {
            total += produto.getPrecoTotal();
        }
        return total;
    }
}