package com.pass.gamesource;

import android.os.Bundle;
import android.util.Log;

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
        Videojuego v = new Videojuego("No man's Sky", "10.90", "Exploración y aventura a saco paco");
        Videojuego v2 = new Videojuego("Albion Online", "0", "MMORPG");
        listaVideojuegos.add(v);
        listaVideojuegos.add(v2);
        Log.d("", v.toString());
        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        LinearLayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(listaVideojuegos);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);

    }
}