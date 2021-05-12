package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_fondo_logoG, iv_fondo_logoS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * implements and starts animations
         */
        iv_fondo_logoG = findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS = findViewById(R.id.iv_fondo_logoS);
        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftlogin);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moverightlogin);
        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notificacion:
                Intent intent2 = new Intent(LoginActivity.this, SplashScreen.class);
                startActivity(intent2);
                break;
        }
    }
}