package com.example.morangomania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button buttonNavigate = findViewById(R.id.btn_login);
            buttonNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(in);
                }
            });


          ;

            Button buttonNavigate1 = findViewById(R.id.btn_pagar_pagamento);
            buttonNavigate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in1 = new Intent(MainActivity.this, CadastroActivity.class);
                    startActivity(in1);
                }
            });
        }


    }