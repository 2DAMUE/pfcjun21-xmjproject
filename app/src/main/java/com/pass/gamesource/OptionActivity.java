package com.pass.gamesource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;


public class OptionActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
       menuLateral();

        /**
         * Declaracion de los botones
         */
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_notificaciones).setOnClickListener(this);
        findViewById(R.id.tv_cuenta).setOnClickListener(this);
        findViewById(R.id.tv_ayuda).setOnClickListener(this);

        findViewById(R.id.img_notificacion).setOnClickListener(this);
        findViewById(R.id.img_user).setOnClickListener(this);
        findViewById(R.id.img_ayuda).setOnClickListener(this);

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                Intent intent1 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent1);
                break;
            case R.id.img_notificacion:
                Intent intent2 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent2);
                break;
            case R.id.img_user:
                Intent intent3 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent3);
                break;
            case R.id.img_ayuda:
                Intent intent4 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent4);
                break;
            case R.id.tv_notificaciones:
                Intent intent5 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent5);
                break;
            case R.id.tv_cuenta:
                Intent intent6 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent6);
                break;
            case R.id.tv_ayuda:
                Intent intent7 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent7);
                break;


            /**
             *navigation Bar
             */

            case R.id.img_Home_Logo:
                Intent intent8 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent8);
                break;
            case R.id.img_Search_Logo:
                Intent intent9 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
                Intent intent10 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent11 = new Intent(OptionActivity.this, SplashScreen.class);
                startActivity(intent11);
                break;
        }


    }

    /**
     * Menu lateral
     */
    private DrawerLayout drawerLayout;

    public void menuLateral() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        drawerLayout.addDrawerListener(this);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.copyright).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OptionActivity.this, getString(R.string.copyright),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_camera:


                break;
            case R.id.nav_gallery:
                Intent intent23 = new Intent(OptionActivity.this.getBaseContext(),
                        MainActivity.class);
                startActivity(intent23);
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .commit();


        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

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