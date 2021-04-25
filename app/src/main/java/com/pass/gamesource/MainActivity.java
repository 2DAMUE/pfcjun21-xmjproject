package com.pass.gamesource;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos el ArrayList con todos los videojuegos para que se muestren en el Recycler
        ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();
        Videojuego v = new Videojuego("No man's Sky", "10.90", "Exploración y aventura a saco paco","https://www.minecraft.net/content/dam/games/minecraft/key-art/Games_Subnav_Minecraft-300x465.jpg");
        Videojuego v2 = new Videojuego("Albion Online", "0", "MMORPG","https://www.minecraft.net/content/dam/games/minecraft/key-art/Games_Subnav_Minecraft-300x465.jpg");
        listaVideojuegos.add(v);
        listaVideojuegos.add(v2);

        ImageView ivMain = findViewById(R.id.JuegoPrincipal);

        Log.d("", v.toString());
        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(listaVideojuegos);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);

    }
}