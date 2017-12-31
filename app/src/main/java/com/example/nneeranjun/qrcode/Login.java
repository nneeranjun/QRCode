package com.example.nneeranjun.qrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText password;
    EditText email;
    ProgressBar spinner;
    AlertDialog.Builder alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        alertDialog = new AlertDialog.Builder(getApplicationContext());
    }

    public void login(View view){
        spinner.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            spinner.setVisibility(View.GONE);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            spinner.setVisibility(View.GONE);
                            alertDialog.setTitle("Error").setMessage("Invalid information").create();

                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }
    public void goToSignUp(View view){
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);

    }
}

