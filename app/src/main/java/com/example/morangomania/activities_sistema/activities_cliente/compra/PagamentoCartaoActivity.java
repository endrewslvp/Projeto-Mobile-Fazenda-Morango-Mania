package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.VendaDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Carrinho;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;

import java.sql.SQLException;
import java.util.List;

public class PagamentoCartaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_cartao);

        EditText numeroCartao = findViewById(R.id.etNumeroCartao);
        EditText validadeCartao = findViewById(R.id.etValidadeCartao);
        EditText cvcCartao = findViewById(R.id.etCvcCartao);
        Button finalizarPagamento = findViewById(R.id.btnFinalizarPagamento);

        //Guardar os dados do cartão no banco

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        finalizarPagamento.setOnClickListener(v -> {
            // Salvar no banco de dados
            VendaDAO vendaDAO = new VendaDAO();
            try {
                vendaDAO.registrarVenda(cliente,produtosCarrinho,enderecoEntrega,metodoPagamento);
                Carrinho.limparCarrinho();
                Intent intent = new Intent(PagamentoCartaoActivity.this, ConfirmacaoCompraActivity.class);
                intent.putExtra("EnderecoEntrega",enderecoEntrega);
                intent.putExtra("Cliente",cliente);
                startActivity(intent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
