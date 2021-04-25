package com.pass.gamesource;

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
        Videojuego v = new Videojuego("No man's Sky", "10.90", "Exploración y aventura a saco paco", "https://store-images.s-microsoft.com/image/apps.15909.68818099466568894.ac2f77eb-933c-43dd-9097-146a94c389a9.2490b939-a2cb-41b7-8816-8792d37a5338");
        Videojuego v2 = new Videojuego("Albion Online", "0", "MMORPG", "https://videoguejos.com/wp-content/uploads/2018/05/Albion-Online.png");
        Videojuego v3 = new Videojuego("Call of duty", "0", "FPS", "https://image.api.playstation.com/vulcan/img/cfn/11307CjjUZ9rA_whmJUghJsG9Hl1-rmnOUTk3-nccj01ZpYMCHrJ8k8kzBrVyp-p-iCPej73TEJAs88ZBeiZ1uirtj0fsa16.png");
        Videojuego v4 = new Videojuego("Fortnite", "0", "Battle royale de acción", "https://cdn2.unrealengine.com/16br-agentjonesy-egs-s2-1200x1600-1200x1600-2531e05bb04f.jpg");
        Videojuego v5 = new Videojuego("Destiny 2", "0", "FPS de rol ambientado en el espacio", "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2017/06/destiny_2_caratula.png?itok=3014kE94");
        Videojuego v6 = new Videojuego("Ark", "0", "Juego de rol ambientado en la prehistoria", "https://cdn2.unrealengine.com/Diesel%2Fproductv2%2Fark%2Fhome%2FEGS_ARKSurvivalEvolved_StudioWildcard_S2-1200x1600-880bbe4ea103bc00a67c12144161bdcfb1c73df1.jpg");
        listaVideojuegos.add(v);
        listaVideojuegos.add(v2);
        listaVideojuegos.add(v3);
        listaVideojuegos.add(v4);
        listaVideojuegos.add(v5);
        listaVideojuegos.add(v6);

        ImageView ivMain = findViewById(R.id.JuegoPrincipal);

        Log.d("", v.toString());
        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(listaVideojuegos);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);

    }
}