package com.example.morangomania.controles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.PagamentoCartaoActivity;
import com.example.morangomania.PagamentoPixActivity;
import com.example.morangomania.R;
import com.example.morangomania.adapter.ResumoPedidoAdapter;
import com.example.morangomania.model.ProdutoCarrinho;
import java.util.List;

public class ResumoPedidoActivity extends AppCompatActivity {

    private ListView listViewResumo;
    private TextView tvMetodoPagamento, tvTotalCompra;
    private Button btnFinalizar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        // Referências ao layout
        listViewResumo = findViewById(R.id.listViewResumo);
        tvMetodoPagamento = findViewById(R.id.tvMetodoPagamento);
        tvTotalCompra = findViewById(R.id.tvTotalCompra);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Receber dados da Intent
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Configurar o ListView com o adaptador
        ResumoPedidoAdapter adapter = new ResumoPedidoAdapter(this, produtosCarrinho);
        listViewResumo.setAdapter(adapter);

        // Configurar textos de resumo
        tvMetodoPagamento.setText("Método de Pagamento: " + metodoPagamento);
        tvTotalCompra.setText(String.format("Total: R$ %.2f", totalCompra));

        // Botão Cancelar
        btnCancelar.setOnClickListener(v -> finish());

        // Botão Finalizar
        btnFinalizar.setOnClickListener(v -> {
            if ("Pix".equals(metodoPagamento)) {
                // Abre a tela de pagamento Pix
                Intent intentPix = new Intent(ResumoPedidoActivity.this, PagamentoPixActivity.class);
                intentPix.putExtra("valorTotal", totalCompra);
                startActivity(intentPix);
            } else {
                // Abre a tela de pagamento com cartão
                Intent intentCartao = new Intent(ResumoPedidoActivity.this, PagamentoCartaoActivity.class);
                intentCartao.putExtra("valorTotal", totalCompra);
                startActivity(intentCartao);
            }
        });
    }
}