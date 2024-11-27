package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // Receber os dados passados pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Ação do botão finalizar pagamento
        finalizarPagamento.setOnClickListener(v -> {
            String numero = numeroCartao.getText().toString();
            String validade = validadeCartao.getText().toString();
            String cvc = cvcCartao.getText().toString();

            // Validação dos dados do cartão
            if (numero.isEmpty() || validade.isEmpty() || cvc.isEmpty()) {
                Toast.makeText(PagamentoCartaoActivity.this, "Por favor, preencha todos os campos do cartão", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validarNumeroCartao(numero)) {
                Toast.makeText(PagamentoCartaoActivity.this, "Número de cartão inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validarDataValidade(validade)) {
                Toast.makeText(PagamentoCartaoActivity.this, "Data de validade inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validarCvc(cvc)) {
                Toast.makeText(PagamentoCartaoActivity.this, "CVC inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Salvar no banco de dados
            VendaDAO vendaDAO = new VendaDAO();
            try {
                vendaDAO.registrarVenda(cliente, produtosCarrinho, enderecoEntrega, metodoPagamento);
                Carrinho.limparCarrinho();
                Intent intent = new Intent(PagamentoCartaoActivity.this, ConfirmacaoCompraActivity.class);
                intent.putExtra("EnderecoEntrega", enderecoEntrega);
                intent.putExtra("Cliente", cliente);
                startActivity(intent);
            } catch (SQLException e) {
                Toast.makeText(PagamentoCartaoActivity.this, "Erro ao registrar a venda. Tente novamente.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    // Método para validar o número do cartão (exemplo simples de validação de formato)
    private boolean validarNumeroCartao(String numero) {
        // Implementação simplificada. Considerar usar uma API externa para validação real do cartão
        return numero.length() == 16; // Validação simples de número de cartão com 16 dígitos
    }

    // Método para validar a data de validade do cartão
    private boolean validarDataValidade(String validade) {
        // Exemplo de validação do formato MM/AAAA
        return validade.matches("(0[1-9]|1[0-2])/\\d{4}");
    }

    // Método para validar o código CVC
    private boolean validarCvc(String cvc) {
        // Validação simples de CVC (3 ou 4 dígitos)
        return cvc.length() >= 3 && cvc.length() <= 4;
    }
}
