package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
        Endereco enderecoEntrega = new Endereco();
        if((Endereco) getIntent().getSerializableExtra("EnderecoEntrega")!=null){
            enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");
        } else enderecoEntrega = cliente.getEndereco();
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
            if ("Pix".equals(metodoPagamento)) {
                // Abre a tela de pagamento Pix
                Intent intentPix = new Intent(ResumoPedidoActivity.this, PagamentoPixActivity.class);
                intentPix.putExtra("valorTotal", totalCompra);
                intentPix.putExtra("EnderecoEntrega", finalEnderecoEntrega);
                intentPix.putExtra("Cliente",cliente);
                intentPix.putExtra("metodoPagamento", metodoPagamento);
                intentPix.putExtra("totalCompra", totalCompra);
                startActivity(intentPix);
            } else {
                // Abre a tela de pagamento com cartão
                Intent intentCartao = new Intent(ResumoPedidoActivity.this, PagamentoCartaoActivity.class);
                intentCartao.putExtra("valorTotal", totalCompra);
                intentCartao.putExtra("EnderecoEntrega", finalEnderecoEntrega);
                intentCartao.putExtra("Cliente",cliente);
                intentCartao.putExtra("metodoPagamento", metodoPagamento);
                intentCartao.putExtra("totalCompra", totalCompra);
                startActivity(intentCartao);
            }
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(ResumoPedidoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });
    }
}
