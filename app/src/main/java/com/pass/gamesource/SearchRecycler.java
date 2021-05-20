package com.pass.gamesource;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchRecycler extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosGratis {

    private final SearchRecycler context = this;

    private ArrayList<Videojuego> videojuegosGratis;
    private EditText busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler);
        /*
        //menuLateral();
        /**
         * Declaracion de los botones
         */

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        EditText busqueda = findViewById(R.id.view_search);
        AccesoFirebase.obtenerVideojuegosFiltrado(context, "");

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
                Intent intent4 = new Intent(SearchRecycler.this, SearchRecycler.class);
                startActivity(intent4);
                break;
            case R.id.img_Historial_Logo:
                Intent intent5 = new Intent(SearchRecycler.this, MainActivity.class);
                startActivity(intent5);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent6 = new Intent(SearchRecycler.this, CalendarActivity.class);
                startActivity(intent6);
                break;
        }
    }
    public void mostrarAlertDialog(Videojuego v, SearchRecycler view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);

        alert.setView(view2);
        builder.setView(view2);

        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        imagenAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {

                Intent intent = new Intent(context, VistaWebVideojuego.class);
                intent.putExtra("URL", v.getUrl_origen());
                startActivity(intent);
            }
        });
        Glide.with(view).load(v.getImage_url()).centerCrop().into(imagenAlert);

        tvDescripcion.setText(v.getDescripcion());
        tvNombre.setText(v.getNombre());

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

    @Override
    public void recuperarVideojuegos(ArrayList<Videojuego> videojuegos) {
        RecyclerView recycler = findViewById(R.id.recyclerJuegosActivity);
        RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdaptadorSearch adaptador = new AdaptadorSearch(videojuegos, context);
        recycler.setLayoutManager(gestor);
        recycler.setAdapter(adaptador);
    }
}
