package com.pass.gamesource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        //Creamos el ArrayList con todos los videojuegos para que se muestren en el Recycler
        ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();
        Videojuego v = new Videojuego("No man's Sky", "10.90", "Exploración y aventura a saco paco", "https://store-images.s-microsoft.com/image/apps.15909.68818099466568894.ac2f77eb-933c-43dd-9097-146a94c389a9.2490b939-a2cb-41b7-8816-8792d37a5338");
        Videojuego v2 = new Videojuego("Albion Online", "0", "MMORPG", "https://videoguejos.com/wp-content/uploads/2018/05/Albion-Online.png");
        Videojuego v3 = new Videojuego("Call of duty", "0", "FPS", "https://image.api.playstation.com/vulcan/img/cfn/11307CjjUZ9rA_whmJUghJsG9Hl1-rmnOUTk3-nccj01ZpYMCHrJ8k8kzBrVyp-p-iCPej73TEJAs88ZBeiZ1uirtj0fsa16.png");
        Videojuego v4 = new Videojuego("Fortnite", "0", "Battle royale de acción", "https://cdn2.unrealengine.com/16br-agentjonesy-egs-s2-1200x1600-1200x1600-2531e05bb04f.jpg");
        Videojuego v5 = new Videojuego("Destiny 2", "0", "FPS de rol ambientado en el espacio", "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2017/06/destiny_2_caratula.png?itok=3014kE94");
        Videojuego v6 = new Videojuego("Ark", "0", "Juego de rol ambientado en la prehistoria", "https://cdn2.unrealengine.com/Diesel%2Fproductv2%2Fark%2Fhome%2FEGS_ARKSurvivalEvolved_StudioWildcard_S2-1200x1600-880bbe4ea103bc00a67c12144161bdcfb1c73df1.jpg");
        Videojuego v7 = new Videojuego("cities Skylines", "0", "Juego de contrucción de ciudades", "https://cdn2.unrealengine.com/egs-citiesskylines-colossalorder-s5-1920x1080-689706625.jpg?h=1080&resize=1&w=1920");
        Videojuego v8 = new Videojuego("Asseto Corsa", "0", "Juego de simulación de conducción", "https://image.api.playstation.com/cdn/EP4040/CUSA01797_00/NMcAucyANMnYMNkz6V5vk9f5YXty2mCz.png");
        Videojuego v9 = new Videojuego("Euro Truck Simulator", "0", "Juego de simulación de conducción de camiones", "https://s1.gaming-cdn.com/images/products/309/orig/euro-truck-simulator-2-cover.jpg");

        listaVideojuegos.add(v);
        listaVideojuegos.add(v2);
        listaVideojuegos.add(v3);
        listaVideojuegos.add(v4);
        listaVideojuegos.add(v5);
        listaVideojuegos.add(v6);
        listaVideojuegos.add(v7);
        listaVideojuegos.add(v8);
        listaVideojuegos.add(v9);

        ImageView ivMain = findViewById(R.id.JuegoPrincipal);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/gamesource-9bc51.appspot.com/o/epic_free%2FAlien%3A%20Isolation%20.JPEG?alt=media")
                .centerCrop().into(ivMain);

        Log.d("", v.toString());
        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(listaVideojuegos, this);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);


    }

    public void mostrarAlertDialog(Videojuego v, MainActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view);

        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);
        alert.setView(view2);
        builder.setView(view2);
        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        TextView tvPrecio = view2.findViewById(R.id.precioAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        Glide.with(view).load(v.getUri()).centerCrop().into(imagenAlert);
        tvDescripcion.setText(v.getDescripcion());
        tvNombre.setText(v.getTitulo());
        tvPrecio.setText(v.getPrecio());
        builder.show();
        //alert.show();
    }

    /**
     *navigation Bar
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_Home_Logo:
    Intent intent8 = new Intent(MainActivity.this, MainActivity.class);
    startActivity(intent8);
                break;
            case R.id.img_Search_Logo:
    Intent intent9 = new Intent(MainActivity.this, SearchRecycler.class);
    startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
    Intent intent10 = new Intent(MainActivity.this, MainActivity.class);
    startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
    Intent intent11 = new Intent(MainActivity.this, MainActivity.class);
    startActivity(intent11);
                break;

}


    }


            }