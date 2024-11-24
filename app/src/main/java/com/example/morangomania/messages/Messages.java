package com.example.morangomania.messages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Messages {
    public static void showMessageBox(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title); // Define o título
        builder.setMessage(message); // Define a mensagem

        // Botão de confirmação (positivo)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fecha a caixa de diálogo
            }
        });

        // Cria e exibe a caixa de diálogo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
