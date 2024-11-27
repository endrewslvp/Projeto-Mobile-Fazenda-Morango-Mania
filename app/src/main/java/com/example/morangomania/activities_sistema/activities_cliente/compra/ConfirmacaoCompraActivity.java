// A Activity ConfirmacaoCompraActivity exibe uma mensagem de agradecimento ao cliente e confirma o endereço de entrega dos morangos. 
// O cliente é redirecionado para a tela inicial ao clicar no botão "Voltar ao Início".

package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.tela_inicial.InicioActivity;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

public class ConfirmacaoCompraActivity extends AppCompatActivity {

    // Declaração dos componentes da interface
    private TextView tvAgradecimentos, tvEnderecoConfirmacao;
    private Button btnVoltarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita o estilo EdgeToEdge (para telas sem barra de status)
        setContentView(R.layout.activity_confirmacao_compra);

        // Inicializa os componentes da interface
        tvAgradecimentos = findViewById(R.id.textViewAgradecimentos);
        tvEnderecoConfirmacao = findViewById(R.id.textViewEnderecoConfirmacao);
        btnVoltarInicio = findViewById(R.id.btnVoltar);

        // Recebe os dados do cliente e do endereço de entrega passados pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");

        // Exibe a mensagem de agradecimento e o endereço de entrega
        tvAgradecimentos.setText(cliente.getNome() + ", muito obrigado pela preferência!");
        tvEnderecoConfirmacao.setText("Seus morangos serão enviados para " + enderecoEntrega.toString());

        // Ação ao clicar no botão Voltar
        btnVoltarInicio.setOnClickListener(v -> {
            // Cria a Intent para voltar à tela inicial
            Intent intent = new Intent(ConfirmacaoCompraActivity.this, InicioActivity.class);
            intent.putExtra("Cliente", cliente);  // Passa o cliente para a tela inicial
            startActivity(intent);  // Inicia a tela inicial
        });
    }
}
