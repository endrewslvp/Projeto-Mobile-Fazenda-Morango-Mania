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

    public ResumoPedidoAdapter(Context context, List<ProdutoCarrinho> produtos) {
        super(context, 0, produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_resumo_pedido, parent, false);
        }

        ProdutoCarrinho produto = getItem(position);

        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProdutoResumo);
        TextView tvQuantidade = convertView.findViewById(R.id.tvQuantidadeResumo);
        TextView tvPrecoTotal = convertView.findViewById(R.id.tvPrecoTotalResumo);

        tvNomeProduto.setText(produto.getNome());
        tvQuantidade.setText("Qtd: " + produto.getQuantidade());
        tvPrecoTotal.setText(String.format("R$ %.2f", produto.getPrecoTotal()));

        return convertView;
    }
}

