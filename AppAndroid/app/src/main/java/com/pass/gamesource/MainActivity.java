package com.pass.gamesource;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();
        Videojuego v = new Videojuego("No man's Sky", "10.90", "Exploración y aventura a saco paco", Uri.parse("https://s1.gaming-cdn.com/images/products/414/271x377/no-mans-sky-cover.jpg"));
        listaVideojuegos.add(v);
        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(this);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(listaVideojuegos);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);

    }
}