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

    private TextView tvAgradecimentos,tvEnderecoConfirmacao;
    private Button btnVoltarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmacao_compra);

        tvAgradecimentos = findViewById(R.id.textViewAgradecimentos);
        tvEnderecoConfirmacao =findViewById(R.id.textViewEnderecoConfirmacao);
        btnVoltarInicio = findViewById(R.id.btnVoltar);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");

        tvAgradecimentos.setText(cliente.getNome()+", muito obrigado pela preferência!");
        tvEnderecoConfirmacao.setText("Seus morangos serão enviados para "+enderecoEntrega.toString());

        btnVoltarInicio.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmacaoCompraActivity.this, InicioActivity.class);
            intent.putExtra("Cliente",cliente);
            startActivity(intent);
        });


    }
}