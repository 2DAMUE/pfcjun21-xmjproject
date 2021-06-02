package com.pass.gamesource;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegoDestacado, ActualizarVideojuegosGratis, ActualizarVideojuegosSteam, ActualizarVideojuegosEpic {
    MainActivity context = this;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Llamada a los métodos de Firebase para que se construyan los Recycler
        AccesoFirebase.obtenerVideojuegosGratis(this);
        AccesoFirebase.obtenerVideojuegosPS(this);
        AccesoFirebase.obtenerVideojuegosSteam(this);
        AccesoFirebase.obtenerVideojuegoDestacado(this);

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = findViewById(R.id.myswipe);
        swipeLayout.setOnRefreshListener(mOnRefreshListener);

        //Inicialización de los botones del bottom app bar
        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);
        findViewById(R.id.btn_fab).setOnClickListener(this);

    }

    /**
     * @param videojuego El videojuego a mostrar en el AlertDialog
     * @param view       Objeto MainActivity para poder realizar la construcción del alertDialog
     */
    public void mostrarAlertDialog(Videojuego videojuego, MainActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);

        alert.setView(view2);
        builder.setView(view2);

        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        Button btnComparte = view2.findViewById(R.id.buttonComparteJuego);
        Button btnVerJuego = view2.findViewById(R.id.buttonVerJuego);
        ImageButton btnFavorito = view2.findViewById(R.id.btnFavorito);

        btnFavorito.setOnClickListener(v -> {
            btnFavorito.setImageResource(R.drawable.btn_favorites_border_foreground);
            AccesoFirebase.aniadirJuegoFavorito(videojuego, "hola");
        });

        btnVerJuego.setOnClickListener(vista -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videojuego.getUrl_origen()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            // intent.putExtra("URL", v.getUrl_origen());
            startActivity(intent);
        });
        btnComparte.setOnClickListener(vista -> {
            Intent compartir = new Intent(Intent.ACTION_SEND);
            compartir.setType("text/plain");
            compartir.putExtra(Intent.EXTRA_SUBJECT, "GameSource App");
            compartir.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_messageGame) + videojuego.getUrl_origen());
            startActivity(Intent.createChooser(compartir, "Compartir vía"));
        });

        Glide.with(view).load(videojuego.getImage_url()).centerCrop().into(imagenAlert);

        tvDescripcion.setText(videojuego.getDescripcion());
        tvNombre.setText(videojuego.getNombre());

        //builder.show();
        alert.show();
    }

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            AccesoFirebase.obtenerVideojuegosGratis(context);
            AccesoFirebase.obtenerVideojuegosPS(context);
            AccesoFirebase.obtenerVideojuegosSteam(context);
            Toast.makeText(MainActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();

            swipeLayout.setRefreshing(false);


        }

    };

    /**
     * navigation Bar
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_Home_Logo:
                Toast.makeText(this, getString(R.string.here),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_Search_Logo:
                Intent intent9 = new Intent(MainActivity.this, SearchRecycler.class);
                startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
                Intent intent10 = new Intent(MainActivity.this, FavoritosActivity.class);
                startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent11 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent11);
                break;
            case R.drawable.scrim:
                mostrarAlertDialog(new Videojuego("Alien Isolation", "Juego de terror en el que seremos perseguidos por unos peligrosos alienígenas que quieren acabar con nosotros", "https://firebasestorage.googleapis.com/v0/b/gamesource-9bc51.appspot.com/o/epic_free%2FAlien%3A%20Isolation%20.JPEG?alt=media"), this);
                break;
            case R.id.btn_fab:
                Intent intent25 = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent25);
                break;

        }


    }
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Aquí iniciamos todos los métodos de recuperación e inicialización de los Recycler
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void recuperarVideojuegos(ArrayList<Videojuego> videojuegos) {


        RecyclerView recyclerViewSteam = findViewById(R.id.recyclerMainSteam);
        RecyclerView.LayoutManager gestorSteam = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptadorSteam = new AdaptadorRecyclerMain(videojuegos, this);
        recyclerViewSteam.setLayoutManager(gestorSteam);
        recyclerViewSteam.setAdapter(adaptadorSteam);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        AdaptadorRecyclerMain adaptador = new AdaptadorRecyclerMain(videojuegos, this);
        recyclerView.setLayoutManager(gestor);
        recyclerView.setAdapter(adaptador);

        Log.d("MENSAJE STEAM", videojuegos.toString());

    }

    @Override
    public void recuperarVideojuego(Videojuego v) {
        ImageView ivMain = findViewById(R.id.JuegoPrincipal);
        Glide.with(this)
                .load(v.getImage_url())
                .centerCrop().into(ivMain);
    }
}