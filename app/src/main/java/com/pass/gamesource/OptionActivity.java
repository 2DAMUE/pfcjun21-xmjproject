package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);






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
        }
    }

}