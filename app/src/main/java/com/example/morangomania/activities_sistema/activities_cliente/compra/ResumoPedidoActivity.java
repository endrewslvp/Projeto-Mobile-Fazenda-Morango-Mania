// Este código implementa a tela de resumo do pedido.
// Ele exibe uma lista dos produtos no carrinho, o metodo de pagamento selecionado,
// o valor total da compra e o endereço de entrega. O usuário pode finalizar ou cancelar o pedido.
// Ao finalizar, o app valida os dados e redireciona para a tela de pagamento Pix ou cartão.

package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    // Declaracao dos componentes da interface
    private ListView listViewResumo;
    private TextView tvMetodoPagamento, tvTotalCompra, tvEnderecoEntrega;
    private Button btnFinalizar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido); // Define o layout da tela

        // Inicializa os componentes da interface
        listViewResumo = findViewById(R.id.listViewResumo);
        tvMetodoPagamento = findViewById(R.id.tvMetodoPagamento);
        tvTotalCompra = findViewById(R.id.tvTotalCompra);
        tvEnderecoEntrega = findViewById(R.id.tvEnderecoEntrega);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Recebe dados passados pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");

        // Caso o endereco nao tenha sido passado, usa o endereco do cliente
        if (enderecoEntrega == null) {
            enderecoEntrega = cliente.getEndereco();
        }

        // Configura o ListView com o adaptador personalizado
        ResumoPedidoAdapter adapter = new ResumoPedidoAdapter(this, produtosCarrinho);
        listViewResumo.setAdapter(adapter); // Define o adaptador para a lista

        // Configura os textos de resumo do pedido
        tvMetodoPagamento.setText("Metodo de Pagamento: " + metodoPagamento);
        tvTotalCompra.setText(String.format("Total: R$ %.2f", totalCompra));
        tvEnderecoEntrega.setText(enderecoEntrega.toString());

        // Configura o botao de cancelar para fechar a tela
        btnCancelar.setOnClickListener(v -> finish());


        Endereco finalEnderecoEntrega = enderecoEntrega;

        // Configura o botao de finalizar para validar e iniciar o pagamento
        btnFinalizar.setOnClickListener(v -> {
            if (validarDados(cliente, metodoPagamento, totalCompra, finalEnderecoEntrega)) {
                // Se os dados sao validos, inicia a tela de pagamento
                iniciarPagamento(metodoPagamento, totalCompra, finalEnderecoEntrega, cliente);
            } else {
                // Exibe uma mensagem se os dados nao forem validos
                Toast.makeText(ResumoPedidoActivity.this, "Verifique os dados do pedido!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura o botao para acessar o perfil do usuario
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(ResumoPedidoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente); // Passa o objeto cliente
            startActivity(intentProfile); // Inicia a tela de perfil
        });
    }

    // Metodo para validar os dados do pedido antes de iniciar o pagamento
    private boolean validarDados(Cliente cliente, String metodoPagamento, double totalCompra, Endereco enderecoEntrega) {
        return cliente != null && metodoPagamento != null && totalCompra > 0 && enderecoEntrega != null;
    }

    // Metodo para iniciar o pagamento com base no metodo escolhido
    private void iniciarPagamento(String metodoPagamento, double totalCompra, Endereco enderecoEntrega, Cliente cliente) {
        Intent intent;
        if ("Pix".equals(metodoPagamento)) {
            // Se o metodo for Pix, abre a tela de pagamento Pix
            intent = new Intent(ResumoPedidoActivity.this, PagamentoPixActivity.class);
        } else {
            // Se for cartao, abre a tela de pagamento com cartao
            intent = new Intent(ResumoPedidoActivity.this, PagamentoCartaoActivity.class);
        }

        // Passa os dados necessarios para a tela de pagamento
        intent.putExtra("valorTotal", totalCompra);
        intent.putExtra("EnderecoEntrega", enderecoEntrega);
        intent.putExtra("Cliente", cliente);
        intent.putExtra("metodoPagamento", metodoPagamento);
        intent.putExtra("totalCompra", totalCompra);

        startActivity(intent); // Inicia a tela de pagamento correspondente
    }
}
