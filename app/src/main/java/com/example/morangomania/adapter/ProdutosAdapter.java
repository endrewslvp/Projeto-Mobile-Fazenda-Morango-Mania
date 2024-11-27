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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutosAdapter extends BaseAdapter {

    // Variáveis para o contexto, lista de produtos e mapa de imagens
    private Context context;
    private List<Produtos> produtos;
    private Map<String, Integer> imageMap;

    // Construtor do adaptador
    public ProdutosAdapter(Context context, List<Produtos> produtos) {
        this.context = context;
        this.produtos = produtos;

        // Inicializa o mapa de imagens com os produtos e suas imagens associadas
        imageMap = new HashMap<>();
        imageMap.put("albion", R.drawable.albion);
        imageMap.put("camarosa", R.drawable.camarosa);
        imageMap.put("san_andreas", R.drawable.san_andreas);
        imageMap.put("sweet_charlie", R.drawable.sweet_charlie);
    }

    // Retorna a quantidade de itens na lista de produtos
    @Override
    public int getCount() {
        return produtos.size();
    }

    // Retorna o item da lista na posição especificada
    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    // Retorna o id do item na posição especificada, que no caso é o id do produto
    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    // Método responsável por criar e retornar a view de cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Se a view for nula, inflamos uma nova view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        }

        // Obtém o produto na posição atual
        Produtos produto = produtos.get(position);

        // Inicializa os componentes de UI (ImageView e TextViews)
        ImageView produtoImageView = convertView.findViewById(R.id.produtoImage);
        TextView nomeTextView = convertView.findViewById(R.id.produtoNome);
        TextView precoTextView = convertView.findViewById(R.id.produtoPreco);

        // Define o nome do produto no TextView
        nomeTextView.setText(produto.getNome());

        // Define o preço do produto no TextView, formatado para 2 casas decimais
        precoTextView.setText("R$ " + String.format("%.2f", produto.getPreco()));

        // Obtém o id da imagem com base no nome do produto (convertido para minúsculas e substituindo espaços por "_")
        Integer imagemId = imageMap.get(produto.getNome().toLowerCase().replaceAll(" ", "_"));

        // Se a imagem for encontrada, define a imagem do produto
        if (imagemId != null) {
            produtoImageView.setImageResource(imagemId);
        } else {
            // Caso não encontre a imagem, define uma imagem padrão
            produtoImageView.setImageResource(R.drawable.imagem_padrao);
        }

        // Retorna a view configurada para o item da lista
        return convertView;
    }
}
