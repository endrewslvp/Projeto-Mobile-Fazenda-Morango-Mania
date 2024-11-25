package com.example.morangomania;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangomania.DAO.ProdutosDAO;
import com.example.morangomania.DAO.VendaDAO;
import com.example.morangomania.adapter.VendaAdapter;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Venda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPreparing;
    private RecyclerView recyclerViewFinalized;
    private VendaAdapter preparingAdapter;
    private VendaAdapter finalizedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        recyclerViewPreparing = findViewById(R.id.recyclerViewPreparing);
        recyclerViewFinalized = findViewById(R.id.recyclerViewFinalized);



        // Configuração dos RecyclerViews
        recyclerViewPreparing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFinalized.setLayoutManager(new LinearLayoutManager(this));

        // Dados simulados
        List<Venda> preparingSales = null;
        try {
            preparingSales = getPreparingSales();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Venda> finalizedSales = null;
        try {
            finalizedSales = getFinalizedSales();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adaptadores
        preparingAdapter = new VendaAdapter(preparingSales);
        finalizedAdapter = new VendaAdapter(finalizedSales);

        recyclerViewPreparing.setAdapter(preparingAdapter);
        recyclerViewFinalized.setAdapter(finalizedAdapter);
    }

    private List<Venda> getPreparingSales() throws SQLException {
        List<Venda> sales = new ArrayList<>();
        sales.add(new Venda("Venda 1", "A preparar"));
        sales.add(new Venda("Venda 2", "A preparar"));
        return sales;
    }

    private List<Venda> getFinalizedSales() throws SQLException {
        // Exemplo de dados para "Finalizado"
        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        List<Venda> sales = new VendaDAO().obterVendas(cliente.getId(),"Finalizado");
        return sales;
    }
}
