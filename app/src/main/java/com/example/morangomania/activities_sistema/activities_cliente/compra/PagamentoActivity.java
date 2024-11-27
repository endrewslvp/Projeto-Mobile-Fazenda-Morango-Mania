// A Activity PagamentoActivity permite que o cliente selecione uma forma de pagamento para concluir a compra.
// Ela exibe o valor total da compra e permite que o cliente escolha entre Cartão de Débito, Cartão de Crédito ou Pix.

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
    // Declaração dos componentes
    private RadioGroup paymentOptionsGroup;
    private TextView totalAmountTextView;
    private Button cancelButton, continueButton;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Recupera os dados passados pela Intent
        Cliente cliente = (Cliente) getIntent().getSerializableExtra("Cliente");
        totalAmount = getIntent().getDoubleExtra("TOTAL_COMPRA", 0.0);

        // Inicializa os componentes da interface
        paymentOptionsGroup = findViewById(R.id.paymentOptionsGroup);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        cancelButton = findViewById(R.id.cancelButton);
        continueButton = findViewById(R.id.continueButton);

        // Exibe o valor total na TextView
        totalAmountTextView.setText(String.format("Total: R$ %.2f", totalAmount));

        // Ação do botão Cancelar (volta para a tela anterior)
        cancelButton.setOnClickListener(v -> finish());

        // Ação do botão Continuar (segue para a próxima tela após selecionar uma forma de pagamento)
        continueButton.setOnClickListener(v -> {
            int selectedId = paymentOptionsGroup.getCheckedRadioButtonId();

            // Verifica se o cliente escolheu uma forma de pagamento
            if (selectedId == -1) {
                Toast.makeText(this, "Selecione uma forma de pagamento", Toast.LENGTH_SHORT).show();
                return;
            }

            String paymentMethod = "";
            if (selectedId == R.id.debitCardOption) paymentMethod = "Cartão de Débito";
            else if (selectedId == R.id.creditCardOption) paymentMethod = "Cartão de Crédito";
            else if (selectedId == R.id.pixOption) paymentMethod = "Pix";

            // Exibe a forma de pagamento escolhida
            Toast.makeText(this, "Forma de pagamento selecionada: " + paymentMethod, Toast.LENGTH_SHORT).show();

            // Cria a Intent para a próxima tela (EscolhaEnderecoActivity)
            Intent intent = new Intent(PagamentoActivity.this, EscolhaEnderecoActivity.class);
            intent.putExtra("Cliente", cliente);
            intent.putExtra("metodoPagamento", paymentMethod);
            intent.putExtra("totalCompra", totalAmount);
            startActivity(intent);
        });

        // Ação do botão de perfil, que leva o cliente à tela de perfil
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(PagamentoActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("Cliente", cliente);
            startActivity(intentProfile);
        });
    }
}
