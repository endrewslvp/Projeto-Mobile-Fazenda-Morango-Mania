package com.example.morangomania.controles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morangomania.R;
import com.example.morangomania.adapter.CarrinhoAdapter;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.ProdutoCarrinho;

import java.util.ArrayList;
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

        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();

        carrinhoAdapter = new CarrinhoAdapter(this, produtosCarrinho, tvTotalCompra, btnContinuar);
        listViewCarrinho.setAdapter(carrinhoAdapter);

        Button continueButton = findViewById(R.id.btnContinuar);
        continueButton.setOnClickListener(v -> {
            // Calcula o valor total do carrinho
            double totalCompra = calcularTotalCompra();  // Vamos criar esse método

            // Cria um Intent e passa o valor total como extra
            Intent intentCarrinho = new Intent(CarrinhoActivity.this, PagamentoActivity.class);
            intentCarrinho.putExtra("Cliente",cliente);
            intentCarrinho.putExtra("TOTAL_COMPRA", totalCompra);
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