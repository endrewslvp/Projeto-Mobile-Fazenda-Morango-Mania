package com.example.morangomania.activities_sistema.activities_cliente.tela_inicial.detalhes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.model.Carrinho;
import com.example.morangomania.model.ProdutoCarrinho;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetalhesProdutoActivity extends AppCompatActivity {
    private TextView tvNomeProduto, tvPrecoProduto, tvValidadeProduto, tvQuantidade, tvMensagemEstoque;
    private Button btnDiminuir, btnAumentar, btnAdicionarCarrinho, btnVoltarProduto;
    private ImageView ivProduto;
    private int quantidade = 1;
    private int estoqueDisponivel;  // Estoque do produto
    private double precoProduto;

    private Map<String, Integer> imageMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        imageMap = new HashMap<>();
        imageMap.put("albion", R.drawable.albion);
        imageMap.put("camarosa", R.drawable.camarosa);
        imageMap.put("san_andreas", R.drawable.san_andreas);
        imageMap.put("sweet_charlie", R.drawable.sweet_charlie);

        // Referências dos elementos
        ivProduto = findViewById(R.id.ivProduto);
        tvNomeProduto = findViewById(R.id.tvNomeProduto);
        tvPrecoProduto = findViewById(R.id.tvPrecoProduto);
        tvValidadeProduto = findViewById(R.id.tvValidadeProduto);
        tvQuantidade = findViewById(R.id.tvQuantidade);
        tvMensagemEstoque = findViewById(R.id.tvMensagemEstoque);  // Mensagem de erro
        btnDiminuir = findViewById(R.id.btnDiminuir);
        btnAumentar = findViewById(R.id.btnAumentar);
        btnVoltarProduto = findViewById(R.id.btnVoltarProduto);
        btnAdicionarCarrinho = findViewById(R.id.btnAdicionarCarrinho);

        // Recebe dados do produto da Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String nome = intent.getStringExtra("nome");
        precoProduto = intent.getDoubleExtra("preco", 0);
        String validade = intent.getStringExtra("validade");
        estoqueDisponivel = intent.getIntExtra("estoque", 0);  // Recebe o estoque

        // Formata a data de validade
        String validadeFormatada = formatarData(validade);

        // Exibe os dados do produto
        tvNomeProduto.setText(nome);
        tvPrecoProduto.setText(String.format("Preço: R$ %.2f", precoProduto));
        tvValidadeProduto.setText("Validade: " + validadeFormatada);  // Exibe a data formatada
        Integer imagemId = imageMap.get(nome.toLowerCase().replaceAll(" ", "_"));

        if (imagemId != null) {
            ivProduto.setImageResource(imagemId);
        } else {
            // Caso não encontre a imagem, defina uma imagem padrão
            ivProduto.setImageResource(R.drawable.imagem_padrao);
        }
        atualizarPrecoTotal();

        // Configuração dos botões de quantidade
        btnDiminuir.setOnClickListener(v -> {
            if (quantidade > 1) {
                quantidade--;
                tvQuantidade.setText(String.valueOf(quantidade));
                atualizarPrecoTotal();
            }
        });

        btnAumentar.setOnClickListener(v -> {
            if (quantidade < estoqueDisponivel) {
                quantidade++;
                tvQuantidade.setText(String.valueOf(quantidade));
                atualizarPrecoTotal();
                tvMensagemEstoque.setVisibility(View.GONE); // Oculta a mensagem quando abaixo do limite
            } else {
                // Exibe a mensagem de erro e garante que o botão permanece ativo/inativo corretamente
                tvMensagemEstoque.setText("Quantidade indisponível em estoque");
                tvMensagemEstoque.setVisibility(View.VISIBLE); // Torna a mensagem visível
            }
        });

        // Adiciona o produto ao carrinho
        btnAdicionarCarrinho.setOnClickListener(v -> {
            if (quantidade <= estoqueDisponivel) {
                ProdutoCarrinho produto = new ProdutoCarrinho(id,nome, precoProduto, quantidade);
                Carrinho.adicionarProduto(produto);
                finish();  // Fecha a activity de detalhes do produto
            }
        });

        btnVoltarProduto.setOnClickListener(v -> {finish(); });

    }

    private void atualizarPrecoTotal() {
        double total = quantidade * precoProduto;
        btnAdicionarCarrinho.setText(String.format("Adicionar ao Carrinho - R$ %.2f", total));

        // Verifica se a quantidade excede o estoque
        if (quantidade >= estoqueDisponivel) {
            btnAdicionarCarrinho.setEnabled(false);  // Desativa o botão
            tvMensagemEstoque.setVisibility(View.VISIBLE);
            tvMensagemEstoque.setText("Quantidade indisponível em estoque");
        } else {
            btnAdicionarCarrinho.setEnabled(true);  // Ativa o botão
            tvMensagemEstoque.setVisibility(View.GONE);  // Esconde a mensagem se estiver dentro do limite
        }
    }

    private String formatarData(String data) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd"); // Formato que a data vem
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy"); // Formato desejado

            Date dataValidade = formatoEntrada.parse(data); // Converte a string para Date
            return formatoSaida.format(dataValidade); // Retorna a data formatada
        } catch (Exception e) {
            e.printStackTrace();
            return data; // Caso ocorra um erro, retorna a data original
        }
    }
}