package com.example.morangomania.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangomania.DAO.ProdutosDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Venda;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.VendaViewHolder> {
    private List<Venda> listaVendas;

    public VendaAdapter(List<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    @Override
    public VendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venda, parent, false);
        return new VendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendaViewHolder holder, int position) {
        Venda venda = listaVendas.get(position);
        holder.txtTotalCompra.setText(String.format("Total: R$ %.2f", venda.getTotalCompra()));
        holder.txtMetodoPagamento.setText("Pagamento: " + venda.getMetodoPagamento());
        holder.txtEnderecoEntrega.setText("Endereço: " + venda.getEndereco());

        // Obter os nomes e quantidades dos produtos em uma thread separada
        new Thread(() -> {
            StringBuilder produtosInfo = new StringBuilder();
            try {
                ProdutosDAO produtosDAO = new ProdutosDAO();

                for (Map.Entry<Integer, Integer> entry : venda.getProdutosComprados().entrySet()) {
                    int idProduto = entry.getKey();
                    int quantidade = entry.getValue();
                    String nomeProduto = produtosDAO.obterNomeProdutoPorID(idProduto);

                    if (nomeProduto != null) {
                        produtosInfo.append(nomeProduto)
                                .append(" (x").append(quantidade).append("), ");
                    }
                }

                // Remover a última vírgula
                if (produtosInfo.length() > 0) {
                    produtosInfo.setLength(produtosInfo.length() - 2);
                }

                // Atualiza a UI na thread principal
                holder.txtProdutos.post(() -> holder.txtProdutos.setText("Produtos: " + produtosInfo.toString()));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();

        // Código de compra agora aparece por último
        holder.txtCodigoCompra.setText("Código: " + venda.getCodigoCompra());
    }

    @Override
    public int getItemCount() {
        return listaVendas.size();
    }

    public class VendaViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCodigoCompra;
        public TextView txtTotalCompra;
        public TextView txtMetodoPagamento;
        public TextView txtEnderecoEntrega;
        public TextView txtProdutos;  // Lista de produtos com quantidade

        public VendaViewHolder(View itemView) {
            super(itemView);

            txtTotalCompra = itemView.findViewById(R.id.txtTotalCompra);
            txtMetodoPagamento = itemView.findViewById(R.id.txtMetodoPagamento);
            txtEnderecoEntrega = itemView.findViewById(R.id.txtEnderecoEntrega);
            txtProdutos = itemView.findViewById(R.id.txtProdutos);
            txtCodigoCompra = itemView.findViewById(R.id.txtCodigoCompra);
        }
    }

}


