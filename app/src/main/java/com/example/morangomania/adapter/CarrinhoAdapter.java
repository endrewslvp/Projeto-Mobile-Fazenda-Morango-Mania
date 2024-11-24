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

import java.util.List;

public class CarrinhoAdapter extends ArrayAdapter<ProdutoCarrinho> {
    private Context context;
    private List<ProdutoCarrinho> produtosCarrinho;
    private TextView tvTotalCompra;
    private Button btnContinuar;

    public CarrinhoAdapter(Context context, List<ProdutoCarrinho> produtosCarrinho, TextView tvTotalCompra, Button btnContinuar) {
        super(context, 0, produtosCarrinho);
        this.context = context;
        this.produtosCarrinho = produtosCarrinho;
        this.tvTotalCompra = tvTotalCompra;
        this.btnContinuar = btnContinuar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProdutoCarrinho produtoCarrinho = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_carrinho, parent, false);
        }

        ImageView ivProduto = convertView.findViewById(R.id.ivProduto);
        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProduto);
        TextView tvPrecoProduto = convertView.findViewById(R.id.tvPrecoProduto);
        TextView tvQuantidade = convertView.findViewById(R.id.tvQuantidade);
        TextView tvPrecoTotal = convertView.findViewById(R.id.tvPrecoTotal);
        ImageButton btnDiminuir = convertView.findViewById(R.id.btnDiminuir);
        ImageButton btnAumentar = convertView.findViewById(R.id.btnAumentar);
        ImageButton btnRemover = convertView.findViewById(R.id.btnRemover);

        // Preenche os dados do produto no carrinho
        ivProduto.setImageBitmap(produtoCarrinho.getImagem());  // Assumindo que o produto tenha uma imagem
        tvNomeProduto.setText(produtoCarrinho.getNome());
        tvPrecoProduto.setText(String.format("R$ %.2f", produtoCarrinho.getPreco()));
        tvQuantidade.setText(String.valueOf(produtoCarrinho.getQuantidade()));
        tvPrecoTotal.setText(String.format("R$ %.2f", produtoCarrinho.getPrecoTotal()));

        // Botões de aumentar e diminuir quantidade
        btnAumentar.setOnClickListener(v -> {
            produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() + 1);
            atualizarItemCarrinho(position);
        });

        btnDiminuir.setOnClickListener(v -> {
            if (produtoCarrinho.getQuantidade() > 1) {
                produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() - 1);
                atualizarItemCarrinho(position);
            }
        });

        // Botão para remover item do carrinho
        btnRemover.setOnClickListener(v -> {
            produtosCarrinho.remove(position);
            notifyDataSetChanged();
            atualizarTotalCompra();
        });

        // Atualiza o total do carrinho
        atualizarTotalCompra();

        return convertView;
    }

    // Atualiza o item específico do carrinho
    private void atualizarItemCarrinho(int position) {
        ProdutoCarrinho produtoCarrinho = getItem(position);
        produtoCarrinho.setPrecoTotal(produtoCarrinho.getPreco() * produtoCarrinho.getQuantidade());
        notifyDataSetChanged();
        atualizarTotalCompra();
    }

    // Atualiza o total da compra
    private void atualizarTotalCompra() {
        double totalCompra = 0;
        for (ProdutoCarrinho produto : produtosCarrinho) {
            totalCompra += produto.getPrecoTotal();
        }
        tvTotalCompra.setText(String.format("Preço Total: R$ %.2f", totalCompra));

        if (totalCompra == 0) {
            btnContinuar.setEnabled(false);
        } else {
            btnContinuar.setEnabled(true);
        }
    }
}

