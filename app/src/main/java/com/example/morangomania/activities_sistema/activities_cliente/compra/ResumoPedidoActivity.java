package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.adapter.ResumoPedidoAdapter;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.model.Carrinho;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;

import java.util.List;

public class ResumoPedidoActivity extends AppCompatActivity {

    private ListView listViewResumo;
    private TextView tvMetodoPagamento, tvTotalCompra, tvEnderecoEntrega;
    private Button btnFinalizar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        // Referências ao layout
        listViewResumo = findViewById(R.id.listViewResumo);
        tvMetodoPagamento = findViewById(R.id.tvMetodoPagamento);
        tvTotalCompra = findViewById(R.id.tvTotalCompra);
        tvEnderecoEntrega = findViewById(R.id.tvEnderecoEntrega);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Receber dados da Intent
        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");

        // Caso o endereço não tenha sido passado, usa o endereço do cliente
        if (enderecoEntrega == null) {
            enderecoEntrega = cliente.getEndereco();
        }

        Endereco finalEnderecoEntrega = enderecoEntrega;

        // Configurar o ListView com o adaptador
        ResumoPedidoAdapter adapter = new ResumoPedidoAdapter(this, produtosCarrinho);
        listViewResumo.setAdapter(adapter);

        // Configurar textos de resumo
        tvMetodoPagamento.setText("Método de Pagamento: " + metodoPagamento);
        tvTotalCompra.setText(String.format("Total: R$ %.2f", totalCompra));
        tvEnderecoEntrega.setText(enderecoEntrega.toString());

        // Botão Cancelar
        btnCancelar.setOnClickListener(v -> finish());

        // Botão Finalizar

        btnFinalizar.setOnClickListener(v -> {
            if (validarDados(cliente, metodoPagamento, totalCompra, finalEnderecoEntrega)) {
                iniciarPagamento(metodoPagamento, totalCompra, finalEnderecoEntrega, cliente);
            } else {
                // Feedback para o usuário em caso de erro nos dados
                Toast.makeText(ResumoPedidoActivity.this, "Verifique os dados do pedido!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botão para acessar o perfil
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(ResumoPedidoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile);
        });
    }

    // Método para validar os dados antes de prosseguir com o pagamento
    private boolean validarDados(Cliente cliente, String metodoPagamento, double totalCompra, Endereco enderecoEntrega) {
        return cliente != null && metodoPagamento != null && totalCompra > 0 && enderecoEntrega != null;
    }

    // Método para iniciar o pagamento com base no método escolhido
    private void iniciarPagamento(String metodoPagamento, double totalCompra, Endereco enderecoEntrega, Cliente cliente) {
        Intent intent;
        if ("Pix".equals(metodoPagamento)) {
            // Abre a tela de pagamento Pix
            intent = new Intent(ResumoPedidoActivity.this, PagamentoPixActivity.class);
        } else {
            // Abre a tela de pagamento com cartão
            intent = new Intent(ResumoPedidoActivity.this, PagamentoCartaoActivity.class);
        }

        // Passa os dados para a próxima tela
        intent.putExtra("valorTotal", totalCompra);
        intent.putExtra("EnderecoEntrega", enderecoEntrega);
        intent.putExtra("Cliente", cliente);
        intent.putExtra("metodoPagamento", metodoPagamento);
        intent.putExtra("totalCompra", totalCompra);

        // Inicia a tela de pagamento
        startActivity(intent);
    }
}
