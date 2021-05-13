package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_fondo_logoG, iv_fondo_logoS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         * implements and starts animations
         */
        iv_fondo_logoG = findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS = findViewById(R.id.iv_fondo_logoS);
        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftlogin);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moverightlogin);
        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);

        findViewById(R.id.btn_registrarse).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registrarse:
                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_back:
                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}