package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class VistaWebVideojuego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_web_videojuego);

        WebView vistaWeb = findViewById(R.id.vistaWeb);
        Intent i = getIntent();
        vistaWeb.loadUrl(i.getStringExtra("URL"));
    }
}