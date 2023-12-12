package com.projeto.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import android.content.Intent;
import android.os.Bundle;

import android.graphics.Color;

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

        ImageView icServicosPix = findViewById(R.id.ic_servicos_pix);
        ImageView icServicosRecarga = findViewById(R.id.ic_servicos_recarga);
        ImageView icSeguros = findViewById(R.id.ic_seguros);
        ImageView icCodigoBarras = findViewById(R.id.ic_codigo_barras);
        ImageView icEmprestimo = findViewById(R.id.ic_emprestimo);
        ImageView icTransferencias = findViewById(R.id.ic_transferias);
        ImageView icCofrinho = findViewById(R.id.ic_cofrinho);

        olho_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSaldoVisibility();
            }
        });

        icServicosPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PixActivity.class));
            }
        });

        icServicosRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Recarga_CelularActivity.class));
            }
        });

        icSeguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SegurosActivity.class));
            }
        });

        icCodigoBarras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PagarActivity.class));
            }
        });

        icEmprestimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmprestimoActivity.class));
            }
        });

        icTransferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransferenciasActivity.class));
            }
        });

        icCofrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CofrinhoActivity.class));
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