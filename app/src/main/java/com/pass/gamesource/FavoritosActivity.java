package com.pass.gamesource;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosFavoritos {

    private final FavoritosActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);
        findViewById(R.id.btn_fab).setOnClickListener(this);

        EditText busqueda = findViewById(R.id.view_search_favoritos);
        AccesoFirebase.obtenerVideojuegosFavoritos(context);

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MENSAJE", s.toString());
                AccesoFirebase.obtenerFavoritosFiltrado(context, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    /**
     * Oyente de botones
     *
     * @param v coger el parametro del bot??n especificado arriba.
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
                Toast.makeText(this, "Ya est??s aqu??", Toast.LENGTH_LONG).show();
                break;
            case R.id.img_Calendar_Logo:
                Intent intent6 = new Intent(FavoritosActivity.this, CalendarActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_fab:
                Intent intent25 = new Intent(FavoritosActivity.this, OptionActivity.class);
                startActivity(intent25);
                break;
        }
    }

    /**
     * Este m??todo se encarga de mostrar el Aler Dialog correspondiente a cada videojuego
     *
     * @param v    el videojuego clickado
     * @param view la instanciaci??n de la misma clase
     */
    public void mostrarAlertDialog(Videojuego v, FavoritosActivity view) {
        //Inicializaci??n del builder del AlerDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);

        alert.setView(view2);
        builder.setView(view2);
        //Inicializaci??n de los elementos de la vista
        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        ImageView ivPlataforma = view2.findViewById(R.id.imgPlataforma);
        Button btnIrAJuego = view2.findViewById(R.id.buttonVerJuego);
        Button btnComparte = view2.findViewById(R.id.buttonComparteJuego);
        ImageButton btnFavorito = view2.findViewById(R.id.btnFavorito);
        switch (v.getPlataforma()) {
            case "ps":
                ivPlataforma.setImageResource(R.mipmap.logo_ps_foreground);
                break;
            case "pc":
                ivPlataforma.setImageResource(R.mipmap.logo_pc_foreground);
                break;
            case "switch":
                ivPlataforma.setImageResource(R.mipmap.logo_switch_foreground);
                break;
        }
        //Manejo del aspecto del bot??n de favoritos

        btnFavorito.setImageResource(R.drawable.btn_favorites_filled_foreground);
        btnFavorito.setOnClickListener(v1 -> {
            btnFavorito.setImageResource(R.drawable.btn_favorites_border_foreground);
            AccesoFirebase.eliminarJuegoFavorito(v);
            AccesoFirebase.obtenerVideojuegosFavoritos(context);
        });

        btnIrAJuego.setOnClickListener(vista -> {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(v.getUrl_origen()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        });

        btnComparte.setOnClickListener(vista -> {
            Intent compartir = new Intent(Intent.ACTION_SEND);
            compartir.setType("text/plain");
            compartir.putExtra(Intent.EXTRA_SUBJECT, "GameSource App");
            compartir.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_messageGame) + v.getUrl_origen());
            startActivity(Intent.createChooser(compartir, "Compartir v??a"));
        });
        Glide.with(view).load(v.getImage_url()).centerCrop().into(imagenAlert);

        tvDescripcion.setText(v.getDescripcion());
        tvNombre.setText(v.getNombre());

        alert.show();
    }

    /**
     * M??todo para manejar la llegada de los datos desde Firebase
     *
     * @param videojuegos El arraylist de objetos que se obtienen de Firebase
     */
    @Override
    public void obtenerVideojuegosFavoritos(ArrayList<Videojuego> videojuegos) {
        RecyclerView recyclerViewEpic = findViewById(R.id.recyclerJuegosActivityFavoritos);
        RecyclerView.LayoutManager gestorEpic = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdaptadorFavoritos adaptadorEpic = new AdaptadorFavoritos(videojuegos, this);
        recyclerViewEpic.setLayoutManager(gestorEpic);
        recyclerViewEpic.setAdapter(adaptadorEpic);
    }
}