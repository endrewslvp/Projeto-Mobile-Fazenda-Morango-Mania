//Este codigo implementa a tela de pagamento por cartao.
//Ele permite ao usuario inserir os dados do cartao, valida as informacoes fornecidas e, se validas,
//registra a venda no banco de dados e limpa o carrinho.


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

        // Captura dos campos do layout
        EditText numeroCartao = findViewById(R.id.etNumeroCartao);
        EditText validadeCartao = findViewById(R.id.etValidadeCartao);
        EditText cvcCartao = findViewById(R.id.etCvcCartao);
        Button finalizarPagamento = findViewById(R.id.btnFinalizarPagamento);

        // Recupera os dados passados pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Define a acao do botao finalizar pagamento
        finalizarPagamento.setOnClickListener(v -> {
            // Captura os dados do cartao inseridos pelo usuario
            String numero = numeroCartao.getText().toString();
            String validade = validadeCartao.getText().toString();
            String cvc = cvcCartao.getText().toString();

            // Valida se todos os campos foram preenchidos
            if (numero.isEmpty() || validade.isEmpty() || cvc.isEmpty()) {
                Toast.makeText(PagamentoCartaoActivity.this, "Por favor, preencha todos os campos do cartao", Toast.LENGTH_SHORT).show();
                return; // Interrompe o fluxo se algum campo estiver vazio
            }

            // Validacao do numero do cartao
            if (!validarNumeroCartao(numero)) {
                Toast.makeText(PagamentoCartaoActivity.this, "Numero de cartao invalido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validacao da data de validade do cartao
            if (!validarDataValidade(validade)) {
                Toast.makeText(PagamentoCartaoActivity.this, "Data de validade invalida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validacao do codigo CVC do cartao
            if (!validarCvc(cvc)) {
                Toast.makeText(PagamentoCartaoActivity.this, "CVC invalido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tenta registrar a venda no banco de dados
            VendaDAO vendaDAO = new VendaDAO();
            try {
                vendaDAO.registrarVenda(cliente, produtosCarrinho, enderecoEntrega, metodoPagamento);
                Carrinho.limparCarrinho(); // Limpa o carrinho apos a venda ser registrada com sucesso

                // Navega para a tela de confirmacao da compra
                Intent intent = new Intent(PagamentoCartaoActivity.this, ConfirmacaoCompraActivity.class);
                intent.putExtra("EnderecoEntrega", enderecoEntrega);
                intent.putExtra("Cliente", cliente);
                startActivity(intent);
            } catch (SQLException e) {
                // Exibe uma mensagem de erro caso a venda nao seja registrada corretamente
                Toast.makeText(PagamentoCartaoActivity.this, "Erro ao registrar a venda. Tente novamente.", Toast.LENGTH_SHORT).show();
                e.printStackTrace(); // Imprime o erro no log para debug
            }
        });
    }

    // Metodo para validar o numero do cartao (validacao basica de 16 digitos)
    private boolean validarNumeroCartao(String numero) {
        // Retorna verdadeiro se o numero tiver exatamente 16 digitos
        return numero.length() == 16;
    }

    // Metodo para validar a data de validade do cartao (formato MM/AAAA)
    private boolean validarDataValidade(String validade) {
        // Verifica se a data corresponde ao formato MM/AAAA usando expressao regular
        return validade.matches("(0[1-9]|1[0-2])/\\d{4}");
    }

    // Metodo para validar o codigo CVC do cartao (3 ou 4 digitos)
    private boolean validarCvc(String cvc) {
        // Retorna verdadeiro se o CVC tiver entre 3 e 4 digitos
        return cvc.length() >= 3 && cvc.length() <= 4;
    }
}
