package com.pass.gamesource;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosGratis {
    MainActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AccesoFirebase.obtenerVideojuegosGratis(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        //Creamos el ArrayList con todos los videojuegos para que se muestren en el Recycler

/*
        listaVideojuegos.add(new Videojuego("No man's Sky", "Exploración y aventura a saco paco", "https://store-images.s-microsoft.com/image/apps.15909.68818099466568894.ac2f77eb-933c-43dd-9097-146a94c389a9.2490b939-a2cb-41b7-8816-8792d37a5338"));
        listaVideojuegos.add(new Videojuego("Albion Online", "MMORPG", "https://videoguejos.com/wp-content/uploads/2018/05/Albion-Online.png"));
        listaVideojuegos.add(new Videojuego("Call of duty", "FPS", "https://image.api.playstation.com/vulcan/img/cfn/11307CjjUZ9rA_whmJUghJsG9Hl1-rmnOUTk3-nccj01ZpYMCHrJ8k8kzBrVyp-p-iCPej73TEJAs88ZBeiZ1uirtj0fsa16.png"));
        listaVideojuegos.add(new Videojuego("Fortnite", "Battle royale de acción", "https://cdn2.unrealengine.com/16br-agentjonesy-egs-s2-1200x1600-1200x1600-2531e05bb04f.jpg"));
        listaVideojuegos.add(new Videojuego("Destiny 2", "FPS de rol ambientado en el espacio", "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2017/06/destiny_2_caratula.png?itok=3014kE94"));
        listaVideojuegos.add(new Videojuego("Ark", "Juego de rol ambientado en la prehistoria", "https://cdn2.unrealengine.com/Diesel%2Fproductv2%2Fark%2Fhome%2FEGS_ARKSurvivalEvolved_StudioWildcard_S2-1200x1600-880bbe4ea103bc00a67c12144161bdcfb1c73df1.jpg"));
        listaVideojuegos.add(new Videojuego("cities Skylines", "Juego de contrucción de ciudades", "https://cdn2.unrealengine.com/egs-citiesskylines-colossalorder-s5-1920x1080-689706625.jpg?h=1080&resize=1&w=1920"));
        listaVideojuegos.add(new Videojuego("Asseto Corsa", "Juego de simulación de conducción", "https://image.api.playstation.com/cdn/EP4040/CUSA01797_00/NMcAucyANMnYMNkz6V5vk9f5YXty2mCz.png"));
        listaVideojuegos.add(new Videojuego("Euro Truck Simulator", "Juego de simulación de conducción de camiones", "https://s1.gaming-cdn.com/images/products/309/orig/euro-truck-simulator-2-cover.jpg"));
*/
        ImageView ivMain = findViewById(R.id.JuegoPrincipal);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/gamesource-9bc51.appspot.com/o/epic_free%2FAlien%3A%20Isolation%20.JPEG?alt=media")
                .centerCrop().into(ivMain);

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

        Glide.with(view).load(v.getImage_url()).centerCrop().into(imagenAlert);

        tvDescripcion.setText(v.getDescripcion());
        tvNombre.setText(v.getNombre());

        //builder.show();
        alert.show();
    }

    /**
     * navigation Bar
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
                Intent intent11 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent11);
                break;
            case R.drawable.scrim:
                mostrarAlertDialog(new Videojuego("Alien Isolation", "Juego de terror en el que seremos perseguidos por unos peligrosos alienígenas que quieren acabar con nosotros", "https://firebasestorage.googleapis.com/v0/b/gamesource-9bc51.appspot.com/o/epic_free%2FAlien%3A%20Isolation%20.JPEG?alt=media"), this);
                break;

        }

    }

    @Override
    public void recuperarVideojuegos(ArrayList<Videojuego> videojuegos) {

        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(videojuegos, this);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);
        Log.d("MENSAJE", videojuegos.toString());
    }
}