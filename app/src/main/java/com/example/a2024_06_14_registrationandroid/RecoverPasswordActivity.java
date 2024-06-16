package com.example.a2024_06_14_registrationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.example.a2024_06_14_registrationandroid.otherfiles.CallBack;
import com.example.a2024_06_14_registrationandroid.otherfiles.Klientas;
import com.example.a2024_06_14_registrationandroid.otherfiles.SendJSonData;
import com.google.gson.Gson;

public class RecoverPasswordActivity extends AppCompatActivity {

    Button backToLogInButton;
    Button resetPasswordButton;
    TextView informationText;
    TextView emailTextField;
    private final String URL = "http://10.0.2.2:8080/resetPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        backToLogInButton = findViewById(R.id.back_from_password_recovery_button);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        backToLogInButton.setOnClickListener(view -> backButtonClicked(view));
        resetPasswordButton.setOnClickListener(view -> resetPasswordButtonClicked(view));


        informationText = findViewById(R.id.information_password_recovery);
        informationText.setAlpha(0);

        emailTextField = findViewById(R.id.password_recovery_email_text);

    }


    public void backButtonClicked(View view){

        Intent intent = new Intent(this, LogInScreenActivity.class);
        startActivity(intent);
    }

    public void resetPasswordButtonClicked(View view){
        backToLogInButton.setEnabled(false);
        resetPasswordButton.setEnabled(false);

        informationText.setText("Please wait...");
        informationText.setAlpha(1);

        Klientas klientas = new Klientas();

        klientas.setEmail(emailTextField.getText().toString());



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
                        emailTextField.setText("");
                        backToLogInButton.setEnabled(true);
                        resetPasswordButton.setEnabled(true);
                    }
                };
                runOnUiThread(newtask);
            }
        });




    }
}
