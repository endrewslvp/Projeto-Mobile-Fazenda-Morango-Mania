// A Activity EscolhaEnderecoActivity permite que o cliente escolha um endereço de entrega,
// cadastre um novo endereço ou confirme a escolha do endereço para continuar o pedido.

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

    // Declaração dos componentes da interface
    private Spinner spinnerEndereco;
    private Button btnCadastrarEndereco, btnConfirmarEndereco;
    private ArrayList<String> listaEnderecos;
    private ArrayAdapter<String> adapterEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_endereco);

        // Recebe os dados do cliente, metodo de pagamento e total da compra
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        // Inicializa os componentes da tela
        spinnerEndereco = findViewById(R.id.spinnerEndereco);
        btnCadastrarEndereco = findViewById(R.id.btnCadastrarEndereco);
        btnConfirmarEndereco = findViewById(R.id.btnConfirmarEndereco);

        // Carrega a lista de endereços disponíveis (exemplo estático, substituir com dados do banco)
        listaEnderecos = new ArrayList<>();
        listaEnderecos.add(cliente.getEndereco().toString());

        // Configura o Adapter para o Spinner
        adapterEndereco = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndereco.setAdapter(adapterEndereco);

        // Ação do botão para cadastrar um novo endereço
        btnCadastrarEndereco.setOnClickListener(v -> {
            // Cria a Intent para a tela de cadastro de novo endereço
            Intent intent = new Intent(EscolhaEnderecoActivity.this, NovoEnderecoEntregaActivity.class);
            intent.putExtra("Cliente", cliente);
            intent.putExtra("metodoPagamento", metodoPagamento);
            intent.putExtra("totalCompra", totalCompra);
            startActivity(intent);
        });

        // Ação do botão para confirmar o endereço escolhido
        btnConfirmarEndereco.setOnClickListener(v -> {
            // Obtém o endereço escolhido e exibe uma mensagem de confirmação
            String enderecoEscolhido = spinnerEndereco.getSelectedItem().toString();
            Toast.makeText(EscolhaEnderecoActivity.this, "Endereço escolhido: " + enderecoEscolhido, Toast.LENGTH_SHORT).show();

            // Cria a Intent para redirecionar para a tela de resumo do pedido
            Intent intent = new Intent(EscolhaEnderecoActivity.this, ResumoPedidoActivity.class);
            intent.putExtra("Cliente", cliente);
            intent.putExtra("metodoPagamento", metodoPagamento);
            intent.putExtra("totalCompra", totalCompra);
            startActivity(intent);
        });

        // Ação do botão de perfil do cliente
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            // Cria a Intent para acessar o perfil do cliente
            Intent intentProfile = new Intent(EscolhaEnderecoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile);
        });
    }
}
