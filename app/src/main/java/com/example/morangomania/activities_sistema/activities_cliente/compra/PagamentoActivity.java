package com.example.morangomania.activities_sistema.activities_cliente.compra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.morangomania.R;
import com.example.morangomania.activities_sistema.activities_cliente.cadastro_e_login.UserProfileActivity;
import com.example.morangomania.model.Cliente;

public class PagamentoActivity extends AppCompatActivity {
    private RadioGroup paymentOptionsGroup;
    private TextView totalAmountTextView;
    private Button cancelButton, continueButton;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        Cliente cliente =(Cliente) getIntent().getSerializableExtra("Cliente");

        // Inicializa componentes
        paymentOptionsGroup = findViewById(R.id.paymentOptionsGroup);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        cancelButton = findViewById(R.id.cancelButton);
        continueButton = findViewById(R.id.continueButton);

        // Recupera o valor total do Intent
        double totalAmount = getIntent().getDoubleExtra("TOTAL_COMPRA", 0.0);

        // Define o valor total na TextView
        totalAmountTextView.setText(String.format("Total: R$ %.2f", totalAmount));

        // Botão Cancelar
        cancelButton.setOnClickListener(v -> finish());

        // Botão Continuar (lógica já existente)
        continueButton.setOnClickListener(v -> {
            int selectedId = paymentOptionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Selecione uma forma de pagamento", Toast.LENGTH_SHORT).show();
                return;
            }

            String paymentMethod = "";
            if (selectedId == R.id.debitCardOption) paymentMethod = "Cartão de Débito";
            else if (selectedId == R.id.creditCardOption) paymentMethod = "Cartão de Crédito";
            else if (selectedId == R.id.pixOption) paymentMethod = "Pix";

            // Exibe a forma de pagamento selecionada (pode adicionar lógica para próxima etapa)
            Toast.makeText(this, "Forma de pagamento selecionada: " + paymentMethod, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(PagamentoActivity.this, EscolhaEnderecoActivity.class);
            intent.putExtra("Cliente",cliente);
            intent.putExtra("metodoPagamento", paymentMethod);
            intent.putExtra("totalCompra", totalAmount);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(PagamentoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente",cliente);
            startActivity(intentProfile);
        });

    }
}