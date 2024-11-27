package com.example.morangomania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.morangomania.R;
import com.example.morangomania.model.ProdutoCarrinho;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarrinhoAdapter extends ArrayAdapter<ProdutoCarrinho> {

    // Variáveis para o contexto, lista de produtos, componentes de UI e mapa de imagens
    private Context context;
    private List<ProdutoCarrinho> produtosCarrinho;
    private TextView tvTotalCompra;
    private Button btnContinuar;
    private Map<String, Integer> imageMap;

    // Construtor do adaptador que inicializa o contexto, lista e componentes de UI
    public CarrinhoAdapter(Context context, List<ProdutoCarrinho> produtosCarrinho, TextView tvTotalCompra, Button btnContinuar) {
        super(context, 0, produtosCarrinho);  // Chama o construtor da classe pai (ArrayAdapter)
        this.context = context;
        this.produtosCarrinho = produtosCarrinho;
        this.tvTotalCompra = tvTotalCompra;
        this.btnContinuar = btnContinuar;

        // Inicializa o mapa de imagens, associando o nome do produto ao id da imagem
        imageMap = new HashMap<>();
        imageMap.put("albion", R.drawable.albion);
        imageMap.put("camarosa", R.drawable.camarosa);
        imageMap.put("san_andreas", R.drawable.san_andreas);
        imageMap.put("sweet_charlie", R.drawable.sweet_charlie);
    }

    // Método que infla a view de cada item do carrinho e configura os elementos da UI
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProdutoCarrinho produtoCarrinho = getItem(position);  // Obtém o produto na posição atual

        // Se a view for nula, infla uma nova view usando o layout item_carrinho
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_carrinho, parent, false);
        }

        // Inicializa os componentes de UI da view do item
        ImageView ivProduto = convertView.findViewById(R.id.ivProduto);
        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProduto);
        TextView tvPrecoProduto = convertView.findViewById(R.id.tvPrecoProduto);
        TextView tvQuantidade = convertView.findViewById(R.id.tvQuantidade);
        TextView tvPrecoTotal = convertView.findViewById(R.id.tvPrecoTotal);
        ImageButton btnDiminuir = convertView.findViewById(R.id.btnDiminuir);
        ImageButton btnAumentar = convertView.findViewById(R.id.btnAumentar);
        ImageButton btnRemover = convertView.findViewById(R.id.btnRemover);

        // Preenche os dados do produto na UI
        tvNomeProduto.setText(produtoCarrinho.getNome());
        tvPrecoProduto.setText(String.format("Preço: R$ %.2f", produtoCarrinho.getPreco()));
        tvQuantidade.setText(String.valueOf("Quantidade: " + produtoCarrinho.getQuantidade()));
        tvPrecoTotal.setText(String.format("Total: R$ %.2f", produtoCarrinho.getPrecoTotal()));

        // Obtém o id da imagem com base no nome do produto (em minúsculas e substituindo espaços por '_')
        Integer imagemId = imageMap.get(produtoCarrinho.getNome().toLowerCase().replaceAll(" ", "_"));

        // Se encontrar a imagem no mapa, define a imagem no ImageView, caso contrário, usa uma imagem padrão
        if (imagemId != null) {
            ivProduto.setImageResource(imagemId);
        } else {
            ivProduto.setImageResource(R.drawable.imagem_padrao);  // Imagem padrão se o produto não tiver uma imagem específica
        }

        // Configura o comportamento dos botões de aumentar e diminuir quantidade
        btnAumentar.setOnClickListener(v -> {
            produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() + 1);  // Aumenta a quantidade
            atualizarItemCarrinho(position);  // Atualiza o item no carrinho
        });

        btnDiminuir.setOnClickListener(v -> {
            // Só diminui se a quantidade for maior que 1 (não pode ficar negativa)
            if (produtoCarrinho.getQuantidade() > 1) {
                produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() - 1);  // Diminui a quantidade
                atualizarItemCarrinho(position);  // Atualiza o item no carrinho
            }
        });

        // Configura o comportamento do botão para remover o item do carrinho
        btnRemover.setOnClickListener(v -> {
            produtosCarrinho.remove(position);  // Remove o item da lista de produtos no carrinho
            notifyDataSetChanged();  // Notifica que os dados foram atualizados
            atualizarTotalCompra();  // Atualiza o total da compra
        });

        // Atualiza o total da compra após alterações
        atualizarTotalCompra();

        return convertView;  // Retorna a view inflada para exibição
    }

    // Método para atualizar o item do carrinho, recalculando o preço total do produto
    private void atualizarItemCarrinho(int position) {
        ProdutoCarrinho produtoCarrinho = getItem(position);  // Obtém o produto na posição atual
        produtoCarrinho.setPrecoTotal(produtoCarrinho.getPreco() * produtoCarrinho.getQuantidade());  // Calcula o preço total
        notifyDataSetChanged();  // Notifica que os dados foram alterados
        atualizarTotalCompra();  // Atualiza o total da compra
    }

    // Método para atualizar o total da compra no carrinho e habilitar/desabilitar o botão de continuar
    private void atualizarTotalCompra() {
        double totalCompra = 0;  // Inicializa a variável para armazenar o total da compra
        // Percorre todos os produtos no carrinho e soma o preço total de cada um
        for (ProdutoCarrinho produto : produtosCarrinho) {
            totalCompra += produto.getPrecoTotal();
        }

        // Exibe o total da compra no TextView
        tvTotalCompra.setText(String.format("Preço Total: R$ %.2f", totalCompra));

        // Se o total for 0, desabilita o botão de continuar, caso contrário, habilita
        if (totalCompra == 0) {
            btnContinuar.setEnabled(false);
        } else {
            btnContinuar.setEnabled(true);
        }
    }
}
