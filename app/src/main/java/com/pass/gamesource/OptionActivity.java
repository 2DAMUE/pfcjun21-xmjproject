package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author GameSource
 */
public class OptionActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_notificacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        img_notificacion = findViewById(R.id.img_notificacion);
        /**
         * Declaracion de los botones
         */
        findViewById(R.id.tv_notificaciones).setOnClickListener(this);
        findViewById(R.id.tv_ayuda).setOnClickListener(this);
        findViewById(R.id.img_notificacion).setOnClickListener(this);
        findViewById(R.id.img_comparte_app).setOnClickListener(this);
        findViewById(R.id.img_opina_option).setOnClickListener(this);
        findViewById(R.id.img_help).setOnClickListener(this);


        /**
         * appBar
         */
        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

        //menuLateral();
    }


    boolean notificacion = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notificacion:
            case R.id.tv_notificaciones:
                if (notificacion) {
                    img_notificacion.setImageResource(R.drawable.img_notificacion_foreground);
                    notificacion = false;
                } else {
                    img_notificacion.setImageResource(R.drawable.logonotificatioff_foreground);
                    notificacion = true;
                }

                break;
            case R.id.tv_ayuda:
                Intent intent3 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent3);
                break;

            case R.id.img_comparte_app:
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "GameSource App");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_message));
                startActivity(Intent.createChooser(compartir, "Compartir v??a"));
                break;

            case R.id.img_opina_option:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_help:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
                break;


            /**
             *navigation Bar
             */

            case R.id.img_Home_Logo:
                Intent intent21 = new Intent(OptionActivity.this, MainActivity.class);
                startActivity(intent21);
                break;
            case R.id.img_Search_Logo:
                Intent intent22 = new Intent(OptionActivity.this, SearchRecycler.class);
                startActivity(intent22);
                break;
            case R.id.img_Historial_Logo:
                Intent intent23 = new Intent(OptionActivity.this, FavoritosActivity.class);
                startActivity(intent23);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent24 = new Intent(OptionActivity.this, CalendarActivity.class);
                startActivity(intent24);
                break;
        }


    }

    /**
     * Menu lateral
     * inicializacion
     */
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;

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
//                Toast.makeText(OptionActivity.this, getString(R.string.copyright),
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
//                startActivity(Intent.createChooser(compartir, "Compartir v??a"));
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
}