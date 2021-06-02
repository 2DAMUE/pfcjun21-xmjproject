package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        menuLateral();

        /*
         * appBar
         */
        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notificacion:
                Intent intent2 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent2);
                break;
            case R.id.img_user:
                Intent intent3 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent3);
                break;
            case R.id.img_ayuda:
                Intent intent4 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent4);
                break;
            case R.id.tv_notificaciones:
                Intent intent5 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent5);
                break;
            case R.id.tv_cuenta:
                Intent intent6 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent6);
                break;
            case R.id.tv_ayuda:
                Intent intent7 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent7);
                break;
            case R.id.img_Home_Logo:
                Intent intent8 = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent8);
                break;
            case R.id.img_Search_Logo:
                Intent intent9 = new Intent(CalendarActivity.this, SearchRecycler.class);
                startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
                Intent intent10 = new Intent(CalendarActivity.this, FavoritosActivity.class);
                startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent11 = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent11);
                break;

        }


    }

    /**
     * Menu lateral
     * inicializacion
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public void menuLateral() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


/**1
 *  Tarjea de forma predeterminada un iten del menu, desactivar para no iniciar el evento del iten seleccionado
 */
//        MenuItem menuItem = navigationView.getMenu().getItem(0);
//        onNavigationItemSelected(menuItem);
//        menuItem.setChecked(true);

        drawerLayout.addDrawerListener(this);
/**
 *  mensaje al clickear en el copyright
 */
        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.copyright).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CalendarActivity.this, getString(R.string.copyright),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Comportamiento al abrir o cerrar el menu
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Botones de navegacion y sus funciones
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            ###################### Account #############################
            case R.id.signIn:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(OptionActivity.this.getBaseContext(),
//                        MainActivity.class);
//                startActivity(intent1);
                break;
            case R.id.signUp:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
//                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
//                        MainActivity.class);
//                startActivity(intent3);
                break;
//                 ###################### Tools #############################
            case R.id.option:
                Toast.makeText(this, getString(R.string.sameActivity),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.notification:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OptionActivity.this.getBaseContext(),
//                        MainActivity.class);
//                startActivity(intent);
                break;
//                          ###################### Social #############################
            case R.id.share:
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "GameSource App");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_message));
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
                break;
            case R.id.rate:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
//                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
//                        MainActivity.class);
//                startActivity(intent3);
                break;
            case R.id.info:
                Toast.makeText(this, getString(R.string.coming_soon),
                        Toast.LENGTH_SHORT).show();
//                Intent intent3 = new Intent(OptionActivity.this.getBaseContext(),
//                        MainActivity.class);
//                startActivity(intent3);
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }


        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

    /**
     * Metodos auto implementados
     * onDrawerSlide cambia la posocion del menu
     * onDrawerOpened accion cuando el menu se abre
     * onDrawerClosed accion cuando el menu se cierra
     * onDrawerStateChanged cambia de estado puede ser STATE_IDLE, STATE_DRAGGING or ST
     *
     * @param drawerView
     * @param slideOffset
     */
    @Override
    public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull @NotNull View drawerView) {


    }

    @Override
    public void onDrawerClosed(@NonNull @NotNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

}