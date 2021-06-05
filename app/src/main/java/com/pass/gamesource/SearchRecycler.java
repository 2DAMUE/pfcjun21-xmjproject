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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchRecycler extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosFavoritos, ActualizarVideojuegosGratis {

    private final SearchRecycler context = this;

    ArrayList<String> favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritos = new ArrayList<>();
        setContentView(R.layout.activity_search_recycler);
        /*
         * Declaracion de los botones
         */

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);
        findViewById(R.id.btn_fab).setOnClickListener(this);

        EditText busqueda = findViewById(R.id.view_search);
        AccesoFirebase.obtenerVideojuegosFiltrado(context, "");

        //Creamos el Listener que se activará a cada caracter que escribamos en el campo de búsqueda
        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MENSAJE", s.toString());
                AccesoFirebase.obtenerVideojuegosFiltrado(context, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                Intent intent3 = new Intent(SearchRecycler.this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.img_Search_Logo:
                Toast.makeText(this, getString(R.string.here),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_Historial_Logo:
                Intent intent5 = new Intent(SearchRecycler.this, FavoritosActivity.class);
                startActivity(intent5);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent6 = new Intent(SearchRecycler.this, CalendarActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_fab:
                Intent intent25 = new Intent(SearchRecycler.this, OptionActivity.class);
                startActivity(intent25);
                break;
        }
    }

    /**
     * Método que mostrará el AlertDialog correspondiente a cada videojuego
     *
     * @param videojuego El videojuego cuyos datos se deben mostrar
     * @param view       la instanciación de la clase en la que se quiere mostrar el AlertDialog
     */
    public void mostrarAlertDialog(Videojuego videojuego, SearchRecycler view) {
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
        if (videojuego.isFavorito())
            btnFavorito.setImageResource(R.drawable.btn_favorites_filled_foreground);

        btnFavorito.setOnClickListener(v -> {

            if (!videojuego.isFavorito()) {
                btnFavorito.setImageResource(R.drawable.btn_favorites_filled_foreground);
                AccesoFirebase.aniadirJuegoFavorito(videojuego);
                videojuego.setFavorito(true);
            } else {
                btnFavorito.setImageResource(R.drawable.btn_favorites_border_foreground);
                AccesoFirebase.eliminarJuegoFavorito(videojuego);
                videojuego.setFavorito(false);
            }
        });

        btnIrAJuego.setOnClickListener(vista -> {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videojuego.getUrl_origen()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
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

    /**
     * Menu lateral
     * inicializacion
     */
    private DrawerLayout drawerLayout;

//    public void menuLateral() {
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.app_name,
//                R.string.app_name);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//
//        navigationView = findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//
///**1
// *  Tarjea de forma predeterminada un iten del menu, desactivar para no iniciar el evento del iten seleccionado
// */
////        MenuItem menuItem = navigationView.getMenu().getItem(0);
////        onNavigationItemSelected(menuItem);
////        menuItem.setChecked(true);
//
//        drawerLayout.addDrawerListener(this);
///**
// *  mensaje al clickear en el copyright
// */
//        View header = navigationView.getHeaderView(0);
//        header.findViewById(R.id.copyright).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SearchRecycler.this, getString(R.string.copyright),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * Comportamiento al abrir o cerrar el menu
//     */
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    /**
//     * Botones de navegacion y sus funciones
//     *
//     * @param menuItem
//     * @return
//     */
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
////            ###################### Account #############################
//            case R.id.signIn:
//                Toast.makeText(this, getString(R.string.coming_soon),
//                        Toast.LENGTH_SHORT).show();
////                Intent intent1 = new Intent(OptionActivity.this.getBaseContext(),
////                        MainActivity.class);
////                startActivity(intent1);
//                break;
//            case R.id.signUp:
//                Toast.makeText(this, getString(R.string.coming_soon),
//                        Toast.LENGTH_SHORT).show();
////                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
////                        MainActivity.class);
////                startActivity(intent3);
//                break;
////                 ###################### Tools #############################
//            case R.id.option:
//                Toast.makeText(this, getString(R.string.sameActivity),
//                        Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.notification:
//                Toast.makeText(this, getString(R.string.coming_soon),
//                        Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(OptionActivity.this.getBaseContext(),
////                        MainActivity.class);
////                startActivity(intent);
//                break;
////                          ###################### Social #############################
//            case R.id.share:
//                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
//                compartir.setType("text/plain");
//                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "GameSource App");
//                compartir.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_message));
//                startActivity(Intent.createChooser(compartir, "Compartir vía"));
//                break;
//            case R.id.rate:
//                Toast.makeText(this, getString(R.string.coming_soon),
//                        Toast.LENGTH_SHORT).show();
////                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
////                        MainActivity.class);
////                startActivity(intent3);
//                break;
//            case R.id.info:
//                Toast.makeText(this, getString(R.string.coming_soon),
//                        Toast.LENGTH_SHORT).show();
////                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
////                        MainActivity.class);
////                startActivity(intent3);
//                break;
//            default:
//                throw new IllegalArgumentException("menu option not implemented!!");
//        }
//
//
//        drawerLayout.closeDrawer(GravityCompat.START);
//
//        return true;
//
//    }
//
//    /**
//     * Metodos auto implementados
//     * onDrawerSlide cambia la posocion del menu
//     * onDrawerOpened accion cuando el menu se abre
//     * onDrawerClosed accion cuando el menu se cierra
//     * onDrawerStateChanged cambia de estado puede ser STATE_IDLE, STATE_DRAGGING or ST
//     *
//     * @param drawerView
//     * @param slideOffset
//     */
//    @Override
//    public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {
//
//    }
//
//    @Override
//    public void onDrawerOpened(@NonNull @NotNull View drawerView) {
//
//
//    }
//
//    @Override
//    public void onDrawerClosed(@NonNull @NotNull View drawerView) {
//
//    }
//
//    @Override
//    public void onDrawerStateChanged(int newState) {
//
//    }

    /**
     * Método que maneja la llegada de los datos desde Firebase
     *
     * @param videojuegos ArrayList de los videojuegos que osn obtenidos de Firebase
     */
    @Override
    public void recuperarVideojuegos(ArrayList<Videojuego> videojuegos) {

        for (Videojuego videojuego : videojuegos) {
            if (favoritos.contains(videojuego.getNombre()))
                videojuego.setFavorito(true);
            else
                videojuego.setFavorito(false);

        }
        //Iniciamos el Recycler una vez hayan llegado los videojuegos ya que sin datos, se quedaría vacío
        RecyclerView recycler = findViewById(R.id.recyclerJuegosActivity);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdaptadorSearch adaptador = new AdaptadorSearch(videojuegos, context);
        recycler.setLayoutManager(gestor);
        recycler.setAdapter(adaptador);
    }

    /**
     * Obtención de los videojuegos favoritos para poder determinar si un videojuego es favorito
     * para cambiar el botón que lo señala de acuerdo a si lo es o no
     *
     * @param videojuegos ArrayList de los videojuegos que osn obtenidos de Firebase
     */
    @Override
    public void obtenerVideojuegosFavoritos(ArrayList<Videojuego> videojuegos) {
        for (Videojuego videojuego : videojuegos) {
            favoritos.add(videojuego.getNombre());
        }
        AccesoFirebase.obtenerVideojuegosGratis(this);
    }
}
