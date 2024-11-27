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
    private List<Venda> listaVendas; // Lista de vendas que será exibida na RecyclerView

    // Construtor que recebe a lista de vendas
    public VendaAdapter(List<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    // Cria uma nova instância de view holder para cada item na lista
    @Override
    public VendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla o layout do item de venda
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venda, parent, false);
        return new VendaViewHolder(view); // Retorna o view holder para o item
    }

    // Popula a view holder com os dados da venda
    @Override
    public void onBindViewHolder(@NonNull VendaViewHolder holder, int position) {
        Venda venda = listaVendas.get(position);

        // Exibe o total da compra
        holder.txtTotalCompra.setText(String.format("Total: R$ %.2f", venda.getTotalCompra()));

        // Exibe o método de pagamento
        holder.txtMetodoPagamento.setText("Pagamento: " + venda.getMetodoPagamento());

        // Exibe o endereço de entrega
        holder.txtEnderecoEntrega.setText("Endereço: " + venda.getEndereco());

        // Inicia uma thread para buscar os nomes dos produtos comprados
        new Thread(() -> {
            StringBuilder produtosInfo = new StringBuilder(); // StringBuilder para armazenar os dados dos produtos
            try {
                ProdutosDAO produtosDAO = new ProdutosDAO(); // Instância do DAO para acessar os dados dos produtos

                // Para cada produto comprado (usando o mapa de produtos e quantidades)
                for (Map.Entry<Integer, Integer> entry : venda.getProdutosComprados().entrySet()) {
                    int idProduto = entry.getKey(); // ID do produto
                    int quantidade = entry.getValue(); // Quantidade do produto
                    String nomeProduto = produtosDAO.obterNomeProdutoPorID(idProduto); // Obtém o nome do produto a partir do ID

                    // Se o nome do produto não for nulo, adiciona ao StringBuilder
                    if (nomeProduto != null) {
                        produtosInfo.append(nomeProduto)
                                .append(" (x").append(quantidade).append("), "); // Adiciona nome e quantidade
                    }
                }

                // Remove a última vírgula
                if (produtosInfo.length() > 0) {
                    produtosInfo.setLength(produtosInfo.length() - 2);
                }

                // Atualiza o campo de produtos na UI principal (Thread principal)
                holder.txtProdutos.post(() -> holder.txtProdutos.setText("Produtos: " + produtosInfo.toString()));

            } catch (SQLException e) {
                e.printStackTrace(); // Trata possíveis erros de SQL
            }
        }).start();

        // Exibe o código da compra
        holder.txtCodigoCompra.setText("Código: " + venda.getCodigoCompra());
    }

    // Retorna o número total de itens na lista de vendas
    @Override
    public int getItemCount() {
        return listaVendas.size();
    }

    // ViewHolder que mantém as referências das views de cada item
    public class VendaViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCodigoCompra;
        public TextView txtTotalCompra;
        public TextView txtMetodoPagamento;
        public TextView txtEnderecoEntrega;
        public TextView txtProdutos; // Texto para exibir os produtos comprados

        // Construtor que recebe o item de view e inicializa as views
        public VendaViewHolder(View itemView) {
            super(itemView);

            // Inicializa as views do item
            txtTotalCompra = itemView.findViewById(R.id.txtTotalCompra);
            txtMetodoPagamento = itemView.findViewById(R.id.txtMetodoPagamento);
            txtEnderecoEntrega = itemView.findViewById(R.id.txtEnderecoEntrega);
            txtProdutos = itemView.findViewById(R.id.txtProdutos);
            txtCodigoCompra = itemView.findViewById(R.id.txtCodigoCompra);
        }
    }
}
