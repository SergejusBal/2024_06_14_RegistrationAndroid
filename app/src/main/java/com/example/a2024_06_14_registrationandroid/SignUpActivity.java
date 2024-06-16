package com.example.a2024_06_14_registrationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2024_06_14_registrationandroid.otherfiles.CallBack;
import com.example.a2024_06_14_registrationandroid.otherfiles.Klientas;
import com.example.a2024_06_14_registrationandroid.otherfiles.SendJSonData;
import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {


    private Button backToLogInButton;
    private Button signUpButton;
    private TextView emailFieldText;
    private TextView usernameFieldText;
    private TextView informationText;
    private String URL = "http://10.0.2.2:8080/createKlientas";
    private final String INFOTAG = "MyTAG SignUpScreen";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backToLogInButton = findViewById(R.id.back_from_register_button);
        signUpButton = findViewById(R.id.register_user_button);

        informationText = findViewById(R.id.information_text_register);
        informationText.setAlpha(0);

        emailFieldText = findViewById(R.id.register_email);
        usernameFieldText = findViewById(R.id.register_username);

        backToLogInButton.setOnClickListener(view -> backButtonClicked(view));
        signUpButton.setOnClickListener(view -> signUpButtonClicked(view));

    }


    public void backButtonClicked(View view){

        Intent intent = new Intent(this, LogInScreenActivity.class);
        startActivity(intent);
    }

    public void signUpButtonClicked(View view){
        backToLogInButton.setEnabled(false);
        signUpButton.setEnabled(false);
        informationText.setText("Please wait...");
        informationText.setAlpha(1);

        Klientas klientas = new Klientas();

        klientas.setEmail(emailFieldText.getText().toString());
        klientas.setUsername(usernameFieldText.getText().toString());


        Gson gson = new Gson();
        String json = gson.toJson(klientas);

        SendJSonData sendJSonData = new SendJSonData(URL);
        sendJSonData.send(json, new CallBack() {
            @Override
            public void onResponseReceive(String message) {
                Runnable newtask = new Runnable() {
                      @Override
                      public void run() {
                          informationText.setText(message);
                          emailFieldText.setText("");
                          usernameFieldText.setText("");
                          backToLogInButton.setEnabled(true);
                          signUpButton.setEnabled(true);
                          }
                      };
                runOnUiThread(newtask);
            }
        });

    }




}
