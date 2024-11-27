package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.DAO.VendaDAO;
import com.example.morangomania.R;
import com.example.morangomania.model.Carrinho;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;
import com.example.morangomania.model.ProdutoCarrinho;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.sql.SQLException;
import java.util.List;

public class PagamentoPixActivity extends AppCompatActivity {

    private String codigoPix = "00020126360014BR.GOV.BCB.PIX"; // Exemplo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_pix);

        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);
        TextView codigoPixTextView = findViewById(R.id.codigoPixTextView);
        Button copiarCodigoButton = findViewById(R.id.copiarCodigoButton);
        Button btnConfirmarPagamento = findViewById(R.id.btnConfirmarPagamento);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");
        Endereco enderecoEntrega = (Endereco) getIntent().getSerializableExtra("EnderecoEntrega");
        List<ProdutoCarrinho> produtosCarrinho = Carrinho.getItensCarrinho();
        String metodoPagamento = getIntent().getStringExtra("metodoPagamento");
        double totalCompra = getIntent().getDoubleExtra("totalCompra", 0.0);

        codigoPixTextView.setText(codigoPix);

        // Gera QR Code
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(codigoPix, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        copiarCodigoButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(codigoPix);
            // Mostra mensagem ou Toast ao copiar
        });

        btnConfirmarPagamento.setOnClickListener(v -> {
            // Salvar no banco de dados
            VendaDAO vendaDAO = new VendaDAO();
            try {
                vendaDAO.registrarVenda(cliente,produtosCarrinho,enderecoEntrega,metodoPagamento);
                Carrinho.limparCarrinho();
                Intent intent = new Intent(PagamentoPixActivity.this, ConfirmacaoCompraActivity.class);
                intent.putExtra("EnderecoEntrega",enderecoEntrega);
                intent.putExtra("Cliente",cliente);
                startActivity(intent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
