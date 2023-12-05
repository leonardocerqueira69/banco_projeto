package com.projeto.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private boolean saldoVisivel = true;
    private TextView text_saldo;
    private ImageView olho_saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_saldo = findViewById(R.id.text_saldo);
        olho_saldo = findViewById(R.id.olho_saldo);

        olho_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSaldoVisibility();
            }
        });

        getWindow().setStatusBarColor(Color.BLACK);
    }
    private void toggleSaldoVisibility(){
        saldoVisivel = !saldoVisivel;

        if(saldoVisivel) {
            text_saldo.setText("R$00,00");

            olho_saldo.setImageResource(R.drawable.ic_olho_saldo_user);
        } else {
            text_saldo.setText("******");


        }
    }
}