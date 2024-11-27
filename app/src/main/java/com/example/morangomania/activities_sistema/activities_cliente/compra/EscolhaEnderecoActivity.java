package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.model.Cliente;

import java.util.ArrayList;

public class EscolhaEnderecoActivity extends AppCompatActivity {
    private Spinner spinnerEndereco;
    private Button btnCadastrarEndereco, btnConfirmarEndereco;
    private ArrayList<String> listaEnderecos;
    private ArrayAdapter<String> adapterEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_endereco);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Inicializando os elementos da tela
        spinnerEndereco = findViewById(R.id.spinnerEndereco);
        btnCadastrarEndereco = findViewById(R.id.btnCadastrarEndereco);
        btnConfirmarEndereco = findViewById(R.id.btnConfirmarEndereco);

        // Carregar lista de endereços disponíveis (exemplo estático, substitua com dados do banco)
        listaEnderecos = new ArrayList<>();
        listaEnderecos.add(cliente.getEndereco().toString());

        // Configurar o Adapter para o Spinner
        adapterEndereco = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndereco.setAdapter(adapterEndereco);

        // Ação do botão para cadastrar um novo endereço
        btnCadastrarEndereco.setOnClickListener(v -> {
            Intent intent = new Intent(EscolhaEnderecoActivity.this, NovoEnderecoEntregaActivity.class);
            intent.putExtra("Cliente",cliente);
            intent.putExtra("metodoPagamento", metodoPagamento);
            intent.putExtra("totalCompra", totalCompra);
            startActivity(intent);
        });

        // Ação do botão para confirmar o endereço escolhido
        btnConfirmarEndereco.setOnClickListener(v -> {
            String enderecoEscolhido = spinnerEndereco.getSelectedItem().toString();
            Toast.makeText(EscolhaEnderecoActivity.this, "Endereço escolhido: " + enderecoEscolhido, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EscolhaEnderecoActivity.this, ResumoPedidoActivity.class);
            intent.putExtra("Cliente",cliente);
            intent.putExtra("metodoPagamento", metodoPagamento);
            intent.putExtra("totalCompra", totalCompra);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(EscolhaEnderecoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });
    }
}

