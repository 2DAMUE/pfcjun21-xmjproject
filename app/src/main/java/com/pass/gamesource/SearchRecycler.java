package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchRecycler extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Videojuego> listaJuegos;
    private RecyclerView recyclerViewJuegos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler);

/**
 * Declaracion del array y el Recycler
 */
        listaJuegos = new ArrayList<>();
        recyclerViewJuegos = findViewById(R.id.recyclerJuegosActivity);
        recyclerViewJuegos.setLayoutManager(new LinearLayoutManager(this));

        loadGames();
        /**
         * Pasamos el array con los datos al adaptador del Recycler
         */
        AdaptadorSearch adapter = new AdaptadorSearch(this, listaJuegos);
        recyclerViewJuegos.setAdapter(adapter);

        /**
         * Declaracion de los botones
         */
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.img_setting).setOnClickListener(this);

    }

    /**
     * Cargamos el array de donde el Recycler leera los datos
     */
    private void loadGames() {
        listaJuegos.add(new Videojuego("Final Fantasy VII", "Juego de fantasya VII RPG", "3/04/1985", R.drawable.finalfantasyvii));
        listaJuegos.add(new Videojuego("RocketLeague", "RocketLeague", "3/04/1985", R.drawable.rocketleague));
        listaJuegos.add(new Videojuego("Kena", "Kena", "3/04/1985", R.drawable.kena));
        listaJuegos.add(new Videojuego("GTAV", "GTAV", "3/04/1985", R.drawable.gtav));
        listaJuegos.add(new Videojuego("HoodOutlaws", "HoodOutlaws", "3/04/1985", R.drawable.hoodoutlaws));
        listaJuegos.add(new Videojuego("Final Fantasy VII", "Juego de fantasya VII RPG", "3/04/1985", R.drawable.finalfantasyvii));
        listaJuegos.add(new Videojuego("LostWords", "LostWords", "3/04/1985", R.drawable.lostwords));
        listaJuegos.add(new Videojuego("RocketLeague", "RocketLeague", "3/04/1985", R.drawable.rocketleague));
        listaJuegos.add(new Videojuego("Kena", "Kena", "3/04/1985", R.drawable.kena));
        listaJuegos.add(new Videojuego("GTAV", "GTAV", "3/04/1985", R.drawable.gtav));
        listaJuegos.add(new Videojuego("Final Fantasy VII", "Juego de fantasya VII RPG", "3/04/1985", R.drawable.finalfantasyvii));
        listaJuegos.add(new Videojuego("RocketLeague", "RocketLeague", "3/04/1985", R.drawable.rocketleague));
        listaJuegos.add(new Videojuego("Kena", "Kena", "3/04/1985", R.drawable.kena));
        listaJuegos.add(new Videojuego("GTAV", "GTAV", "3/04/1985", R.drawable.gtav));
        listaJuegos.add(new Videojuego("HoodOutlaws", "HoodOutlaws", "3/04/1985", R.drawable.hoodoutlaws));
        listaJuegos.add(new Videojuego("Final Fantasy VII", "Juego de fantasya VII RPG", "3/04/1985", R.drawable.finalfantasyvii));
        listaJuegos.add(new Videojuego("LostWords", "LostWords", "3/04/1985", R.drawable.lostwords));
        listaJuegos.add(new Videojuego("RocketLeague", "RocketLeague", "3/04/1985", R.drawable.rocketleague));
        listaJuegos.add(new Videojuego("Kena", "Kena", "3/04/1985", R.drawable.kena));
        listaJuegos.add(new Videojuego("GTAV", "GTAV", "3/04/1985", R.drawable.gtav));

    }

    /**
     * Oyente de botones
     *
     * @param v coger el parametro del botn especificado arriba.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                Intent intent1 = new Intent(SearchRecycler.this, SplashScreen.class);
                startActivity(intent1);
                break;
            case R.id.img_setting:
                Intent intent2 = new Intent(SearchRecycler.this, SplashScreen.class);
                startActivity(intent2);
                break;


            /**
             *navigation Bar
             */

            case R.id.img_Home_Logo:
                Intent intent3 = new Intent(SearchRecycler.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.img_Search_Logo:
                Intent intent4 = new Intent(SearchRecycler.this, SearchRecycler.class);
                startActivity(intent4);
                break;
            case R.id.img_Historial_Logo:
                Intent intent5 = new Intent(SearchRecycler.this, MainActivity.class);
                startActivity(intent5);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent6 = new Intent(SearchRecycler.this, MainActivity.class);
                startActivity(intent6);
                break;
        }
    }
}