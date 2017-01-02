package com.example.grzegorzkokoszka.shopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginB = (Button) findViewById(R.id.loginButton);
        Button registerB = (Button) findViewById(R.id.registerRedirectButton);
        Button passwordB = (Button) findViewById(R.id.passwordSendRedirectButton);
        Button guestB = (Button) findViewById(R.id.btnGuest);
        ImageButton guestImgB = (ImageButton) findViewById(R.id.imgBtnGuest);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginT = (EditText) findViewById(R.id.loginText);
                EditText passwordT = (EditText) findViewById(R.id.passwordText);
                if(loginT.getText().toString().equals("") && passwordT.getText().toString().equals("")){
                    //login as user
                    Log.d(TAG,"Logged");
                    User.getPerson().setName("Kokoszka");
                    User.setIsLogged(true);
                    Intent intent = new Intent(MainActivity.this,LayoutActivity.class);
                    startActivity(intent);
                }else{
                    //warning with info
                    User.getPerson().setName("Alojzy");
                    User.setIsLogged(false);
                    Intent intent = new Intent(MainActivity.this,LayoutActivity.class);
                    startActivity(intent);
                    Log.d(TAG,"Wrong Input");
                }
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto register form
                Intent intent = new Intent(MainActivity.this,RegisterForm.class);
                startActivity(intent);
            }
        });

        passwordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resend password
            }
        });

        guestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User user = new User("Grzegorz","Kokoszka",false);
                Intent intent = new Intent(MainActivity.this,LayoutActivity.class);
                //intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        guestImgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LayoutActivity.class));
            }
        });

    }

}
