package com.pass.gamesource;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

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
                Intent intent8 = new Intent(OptionActivity.this, MainActivity.class);
                startActivity(intent8);
                break;
            case R.id.img_Search_Logo:
                Intent intent9 = new Intent(OptionActivity.this, SearchRecycler.class);
                startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
                Intent intent10 = new Intent(OptionActivity.this, MainActivity.class);
                startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent11 = new Intent(OptionActivity.this, CalendarActivity.class);
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

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}

