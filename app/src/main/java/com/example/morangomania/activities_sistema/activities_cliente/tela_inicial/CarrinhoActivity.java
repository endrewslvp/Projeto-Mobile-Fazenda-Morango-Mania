// Esta classe implementa a tela de carrinho.
// Ela exibe os produtos adicionados ao carrinho, calcula o valor total da compra 
// e permite ao usuário continuar para a tela de pagamento ou navegar para outras telas 
// (perfil, início ou pedidos). Se o carrinho estiver vazio, uma mensagem informativa é exibida.

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
    // Declaracao dos componentes da interface
    private ListView listViewCarrinho;
    private CarrinhoAdapter carrinhoAdapter;
    private TextView tvTotalCompra;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho); // Define o layout da tela

        // Recebe o objeto Cliente passado pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");

        // Inicializa os componentes da interface
        listViewCarrinho = findViewById(R.id.listViewCarrinho);
        tvTotalCompra = findViewById(R.id.tvTotalCompra);
        btnContinuar = findViewById(R.id.btnContinuar);
        Button continueButton = findViewById(R.id.btnContinuar);
        LinearLayout llCarrinhoVazio = findViewById(R.id.llCarrinhoVazio);

        // Recupera a lista de produtos no carrinho
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();

        // Configura o adaptador do carrinho e o vincula ao ListView
        carrinhoAdapter = new CarrinhoAdapter(this, produtosCarrinho, tvTotalCompra, btnContinuar);
        listViewCarrinho.setAdapter(carrinhoAdapter);

        // Verifica se o carrinho esta vazio e ajusta a visibilidade dos componentes
        if (carrinhoAdapter.isEmpty()) {
            listViewCarrinho.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
            tvTotalCompra.setVisibility(View.GONE);
            llCarrinhoVazio.setVisibility(View.VISIBLE); // Mostra a mensagem de carrinho vazio
        } else {
            listViewCarrinho.setVisibility(View.VISIBLE);
            llCarrinhoVazio.setVisibility(View.GONE);
        }

        // Configura o botao para continuar para a tela de pagamento
        continueButton.setOnClickListener(v -> {
            double totalCompra = calcularTotalCompra(); // Calcula o valor total do carrinho

            // Cria um Intent e passa os dados para a tela de pagamento
            Intent intentCarrinho = new Intent(CarrinhoActivity.this, PagamentoActivity.class);
            intentCarrinho.putExtra("Cliente", cliente);
            intentCarrinho.putExtra("TOTAL_COMPRA", totalCompra);
            startActivity(intentCarrinho); // Inicia a tela de pagamento
        });

        // Botao para acessar o perfil do usuario
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(CarrinhoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile); // Abre a tela de perfil
        });

        // Botao para retornar a tela inicial
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intentHome = new Intent(CarrinhoActivity.this, InicioActivity.class);
            intentHome.putExtra("Cliente", cliente);
            startActivity(intentHome);
        });

        // Botao para acessar a tela de pedidos
        ImageButton pedidosButton = findViewById(R.id.pedidosButton);
        pedidosButton.setOnClickListener(v -> {
            Intent intentPedidos = new Intent(CarrinhoActivity.this, VendasActivity.class);
            intentPedidos.putExtra("Cliente", cliente);
            startActivity(intentPedidos);
        });
    }

    // Metodo para calcular o valor total do carrinho
    private double calcularTotalCompra() {
        double total = 0;
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho(); // Lista de produtos
        for (ProdutoCarrinho produto : produtosCarrinho) {
            total += produto.getPrecoTotal(); // Soma o preco total dos produtos
        }
        return total; // Retorna o valor total
    }
}
