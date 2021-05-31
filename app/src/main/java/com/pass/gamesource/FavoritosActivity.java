package com.pass.gamesource;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosFavoritos {

    private final FavoritosActivity context = this;

    private ArrayList<Videojuego> videojuegosGratis;
    private EditText busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        /*
        //menuLateral();
        /**
         * Declaracion de los botones
         */

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        //EditText busqueda = findViewById(R.id.view_search);
        //TODO: implementar usuario activo
        AccesoFirebase.obtenerVideojuegosFavoritos(context);
/*
        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MENSAJE", s.toString());
                // AccesoFirebase.obtenerVideojuegosFiltrado(context, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/

    }


    /**
     * Oyente de botones
     *
     * @param v coger el parametro del botn especificado arriba.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
             *navigation Bar
             */

            case R.id.img_Home_Logo:
                Intent intent3 = new Intent(FavoritosActivity.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.img_Search_Logo:
                Intent intent4 = new Intent(FavoritosActivity.this, SearchRecycler.class);
                startActivity(intent4);
                break;
            case R.id.img_Historial_Logo:
                Intent intent5 = new Intent(FavoritosActivity.this, MainActivity.class);
                startActivity(intent5);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent6 = new Intent(FavoritosActivity.this, CalendarActivity.class);
                startActivity(intent6);
                break;
        }
    }


    /**
     * Menu lateral
     * inicializacion
     */
    private DrawerLayout drawerLayout;

    public void mostrarAlertDialog(Videojuego v, FavoritosActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);

        alert.setView(view2);
        builder.setView(view2);

        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        Button btnIrAJuego = view2.findViewById(R.id.buttonVerJuego);
        Button btnComparte = view2.findViewById(R.id.buttonComparteJuego);
        ImageButton btnFavorito = view2.findViewById(R.id.btnFavorito);
        btnFavorito.setImageResource(R.drawable.btn_favorites_filled_foreground);
        btnFavorito.setOnClickListener(v1 -> {
            btnFavorito.setImageResource(R.drawable.btn_favorites_border_foreground);
            AccesoFirebase.eliminarJuegoFavorito(v);
            AccesoFirebase.obtenerVideojuegosFavoritos(context);
        });

        btnIrAJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(v.getUrl_origen()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                // intent.putExtra("URL", v.getUrl_origen());
                startActivity(intent);
            }
        });

        btnComparte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "GameSource App");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_messageGame) + v.getUrl_origen());
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });
        Glide.with(view).load(v.getImage_url()).centerCrop().into(imagenAlert);

        tvDescripcion.setText(v.getDescripcion());
        tvNombre.setText(v.getNombre());

        //builder.show();
        alert.show();
    }

    @Override
    public void obtenerVideojuegosFavoritos(ArrayList<Videojuego> videojuegos) {
        RecyclerView recyclerViewEpic = findViewById(R.id.recyclerJuegosActivityFavoritos);
        RecyclerView.LayoutManager gestorEpic = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdaptadorFavoritos adaptadorEpic = new AdaptadorFavoritos(videojuegos, this);
        recyclerViewEpic.setLayoutManager(gestorEpic);
        recyclerViewEpic.setAdapter(adaptadorEpic);
    }
}