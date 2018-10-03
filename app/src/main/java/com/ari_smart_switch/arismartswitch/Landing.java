package com.ari_smart_switch.arismartswitch;

import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class Landing extends AppCompatActivity {

    ProgressBar progressBar;
    Button buttonSigninRedirector,buttonSignupRedirector;


    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);


        buttonSigninRedirector = (Button) findViewById(R.id.buttonSigninRedirector);
        buttonSignupRedirector = (Button) findViewById(R.id.buttonSignupRedirector);

        buttonSigninRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
            }
        });

        buttonSignupRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }











}