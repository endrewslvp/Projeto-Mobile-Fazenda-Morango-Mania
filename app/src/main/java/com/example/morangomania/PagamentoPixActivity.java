package com.example.morangomania;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import com.example.morangomania.R;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PagamentoPixActivity extends AppCompatActivity {

    private String codigoPix = "00020126360014BR.GOV.BCB.PIX"; // Exemplo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_pix);

        ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);
        TextView codigoPixTextView = findViewById(R.id.codigoPixTextView);
        Button copiarCodigoButton = findViewById(R.id.copiarCodigoButton);

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
    }
}
