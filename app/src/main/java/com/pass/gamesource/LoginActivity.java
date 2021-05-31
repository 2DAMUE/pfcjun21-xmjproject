package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_fondo_logoG, iv_fondo_logoS;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final String TAG = "SignInActivity";
    private final int RC_SIGN_IN = 9001;
    public static String userEmail;
    private TextView mStatusTextView;
    private TextInputEditText correo;
    private TextInputEditText pass;
    //    Normal Login
    public static FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        /*
         * implements and starts animations
         */
        iv_fondo_logoG = findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS = findViewById(R.id.iv_fondo_logoS);
        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftlogin);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moverightlogin);
        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_Register).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_Register).setOnClickListener(this);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        // Views
        mStatusTextView = findViewById(R.id.status);
        correo = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_Password);
        //Configuracion de login con Google.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Cliente
        // Cree un GoogleSignInClient con las opciones especificadas por gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    //<---------------------------------Login Normal---------------------------------------------------<
    public void login(String email, String password) {
        Log.d("prueba", email + " : " + password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            userEmail = email;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("prueba", "signInWithEmail:success");
                            updateUIF(currentUser);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("prueba", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
    }

    private void updateUIF(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText(getString(R.string.signed_in_fmt, user.getDisplayName()));
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.disconnect_button).setVisibility(View.VISIBLE);
            Log.d("TAG", currentUser.getEmail());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
        }

    }

    /**
     * Compruebe la cuenta de inicio de sesión de Google existente, si el usuario ya ha iniciado sesión
     */
    @Override
    public void onStart() {
        super.onStart();

        // INICIO on_start_sign_in
        // Compruebe la cuenta de inicio de sesión de Google existente, si el usuario ya ha iniciado sesión
        // GoogleSignInAccount no será nulo.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        // END on_start_sign_in
    }

    /**
     * Conecta con Google y muestra las cuentas del usuario
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    // INICIO en Resultado de actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado devuelto al iniciar Intent desde GoogleSignInClient.getSignInIntent (...);
        if (requestCode == RC_SIGN_IN) {
            // La tarea devuelta de esta llamada siempre se completa, no es necesario adjuntar
            // un oyente
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    //FIN del resultado de la actividad

    /**
     * @param completedTask devuele true o false si se registra con exito
     */
    //INICIO manejar Inicio sesión del Resultado
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Se registró correctamente, muestra la interfaz de usuario autenticada.
            updateUI(account);
        } catch (ApiException e) {
            // El código de estado de ApiException indica el motivo detallado del error.
            // Consulte la referencia de la clase GoogleSignInStatusCodes para obtener más información.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // Final

    /**
     * inicia la sesion de google
     */
    // Inico signIn
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // Final signIn


    /**
     * Cierra sesion de google
     */
    // Inicio acceso revocado
    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Inicio Excluido
                        updateUI(null);
                        // Fin Excluido
                    }
                });
    }
    // Fin acceso revocado

    /**
     * @param account le pasa los datos a ala cuenta si se ha inciado o no la sesion.
     *                comparamos resultado y realizamos acciones segun necesitemos
     */
    //Comprueba si estas conectado
    public void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            userEmail = account.getEmail();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.disconnect_button).setVisibility(View.VISIBLE);


//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);

        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Register:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case R.id.btn_login:
                String correolleno = correo.getText().toString();
                String passlleno = pass.getText().toString();

                if (!correolleno.equals("") && !passlleno.equals("")) {
                    if (passlleno.length() >= 6) {
                        login(correolleno, passlleno);

                    } else {
                        Toast.makeText(LoginActivity.this, "La contraseña debe tener una longitud superior a 6 caracteres",
                                Toast.LENGTH_SHORT).show();
                        //layaout.setBackground(R.drawable.);

                    }
                } else {

                    Toast.makeText(LoginActivity.this, "Email y contraseña no pueden estar vacios",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}