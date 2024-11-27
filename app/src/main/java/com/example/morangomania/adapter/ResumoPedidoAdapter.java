package com.example.morangomania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.morangomania.R;
import com.example.morangomania.model.ProdutoCarrinho;

import java.util.List;

public class ResumoPedidoAdapter extends ArrayAdapter<ProdutoCarrinho> {

    // Construtor do adaptador que recebe o contexto e a lista de produtos no carrinho
    public ResumoPedidoAdapter(Context context, List<ProdutoCarrinho> produtos) {
        super(context, 0, produtos); // Chama o construtor da classe pai (ArrayAdapter)
    }

    // Método responsável por retornar a view para cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Verifica se a view já foi inflada, caso contrário, infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_resumo_pedido, parent, false);
        }

        // Obtém o produto na posição atual da lista
        ProdutoCarrinho produto = getItem(position);

        // Inicializa os componentes da interface que serão preenchidos com os dados do produto
        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProdutoResumo);
        TextView tvQuantidade = convertView.findViewById(R.id.tvQuantidadeResumo);
        TextView tvPrecoTotal = convertView.findViewById(R.id.tvPrecoTotalResumo);

        // Preenche os componentes com os dados do produto
        tvNomeProduto.setText(produto.getNome()); // Nome do produto
        tvQuantidade.setText("Qtd: " + produto.getQuantidade()); // Quantidade do produto
        tvPrecoTotal.setText(String.format("R$ %.2f", produto.getPrecoTotal())); // Preço total do produto (formatado)

        // Retorna a view configurada
        return convertView;
    }
}
