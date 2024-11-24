package com.example.morangomania.controles;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
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

        // Inicializando os elementos da tela
        spinnerEndereco = findViewById(R.id.spinnerEndereco);
        btnCadastrarEndereco = findViewById(R.id.btnCadastrarEndereco);
        btnConfirmarEndereco = findViewById(R.id.btnConfirmarEndereco);

        // Carregar lista de endereços disponíveis (exemplo estático, substitua com dados do banco)
        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        listaEnderecos = new ArrayList<>();
        listaEnderecos.add(cliente.getEndereco().toString());

        // Configurar o Adapter para o Spinner
        adapterEndereco = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndereco.setAdapter(adapterEndereco);

        // Ação do botão para cadastrar um novo endereço
        btnCadastrarEndereco.setOnClickListener(v -> {
            //Intent intent = new Intent(EscolherEnderecoActivity.this, CadastrarEnderecoActivity.class);
            //startActivity(intent);
        });

        // Ação do botão para confirmar o endereço escolhido
        btnConfirmarEndereco.setOnClickListener(v -> {
            String enderecoEscolhido = spinnerEndereco.getSelectedItem().toString();
            Toast.makeText(EscolhaEnderecoActivity.this, "Endereço escolhido: " + enderecoEscolhido, Toast.LENGTH_SHORT).show();
        });
    }
}

