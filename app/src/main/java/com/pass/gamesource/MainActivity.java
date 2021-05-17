package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosGratis, ActualizarVideojuegosSteam, ActualizarVideojuegosEpic {
    MainActivity context = this;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AccesoFirebase.obtenerVideojuegosGratis(this);
        AccesoFirebase.obtenerVideojuegosPS(this);
        AccesoFirebase.obtenerVideojuegosSteam(this);
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = findViewById(R.id.myswipe);
        swipeLayout.setOnRefreshListener(mOnRefreshListener);


        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        //Creamos el ArrayList con todos los videojuegos para que se muestren en el Recycler
        ArrayList<Videojuego> listaVideojuegos = new ArrayList<Videojuego>();

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

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            AccesoFirebase.obtenerVideojuegosGratis(context);
            Toast.makeText(MainActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();

            swipeLayout.setRefreshing(false);


        }

    };

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


        Log.d("MENSAJE NORMAL", videojuegos.toString());
    }

    @Override
    public void recuperarVideojuegosEpic(ArrayList<Videojuego> videojuegos) {

        RecyclerView recyclerViewEpic = findViewById(R.id.recyclerMainSoftware);
        RecyclerView.LayoutManager gestorEpic = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptadorEpic = new AdaptadorRecyclerMain(videojuegos, this);
        recyclerViewEpic.setLayoutManager(gestorEpic);
        recyclerViewEpic.setAdapter(adaptadorEpic);

        Log.d("MENSAJE EPIC", videojuegos.toString());
    }

    @Override
    public void recuperarVideojuegosSteam(ArrayList<Videojuego> videojuegos) {


        RecyclerView recyclerViewSteam = findViewById(R.id.recyclerMainSteam);
        RecyclerView.LayoutManager gestorSteam = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptadorSteam = new AdaptadorRecyclerMain(videojuegos, this);
        recyclerViewSteam.setLayoutManager(gestorSteam);
        recyclerViewSteam.setAdapter(adaptadorSteam);

        Log.d("MENSAJE STEAM", videojuegos.toString());

    }
}