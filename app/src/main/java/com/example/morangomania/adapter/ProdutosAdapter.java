package com.example.morangomania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.morangomania.R;
import com.example.morangomania.model.Produtos;

import java.util.List;

public class ProdutosAdapter extends BaseAdapter {

    private Context context;
    private List<Produtos> produtos;

    public ProdutosAdapter(Context context, List<Produtos> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        }

        Produtos produto = produtos.get(position);

        ImageView produtoImageView = convertView.findViewById(R.id.produtoImage);
        TextView nomeTextView = convertView.findViewById(R.id.produtoNome);
        TextView precoTextView = convertView.findViewById(R.id.produtoPreco);

        nomeTextView.setText(produto.getNome());
        precoTextView.setText("R$ " + String.format("%.2f", produto.getPreco()));

        return convertView;
    }
}
