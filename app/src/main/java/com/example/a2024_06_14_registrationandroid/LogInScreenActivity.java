package com.example.a2024_06_14_registrationandroid;

import android.content.Context;
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

public class LogInScreenActivity extends AppCompatActivity {

    private Button logInButton;
    private Button forgotPasswordButton ;
    private Button signUpButton;
    private TextView informationText;
    private TextView usernameFieldText;
    private TextView passwordFieldText;

    private Context context;
    private final String URL = "http://10.0.2.2:8080/authorizeKlientas";
    private final String INFOTAG = "MyTAG LogInScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        context = this;

        logInButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        signUpButton = findViewById(R.id.sign_up_button);

        informationText = findViewById(R.id.information_log_in_text);
        informationText.setAlpha(0);

        usernameFieldText = findViewById(R.id.login_username_text);
        passwordFieldText = findViewById(R.id.login_password_text);


        logInButton.setOnClickListener(view->logInButtonClicked(view));
        forgotPasswordButton.setOnClickListener(view->forgotPasswordButtonClicked(view));
        signUpButton.setOnClickListener(view->signUpButtonButtonClicked(view));

    }


    public void forgotPasswordButtonClicked(View view){
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);
    }
    public void signUpButtonButtonClicked(View view){

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logInButtonClicked(View view){
        logInButton.setEnabled(false);
        signUpButton.setEnabled(false);
        forgotPasswordButton.setEnabled(false);

        informationText.setText("Please wait...");
        informationText.setAlpha(1);

        Klientas klientas = new Klientas();

        klientas.setUsername(usernameFieldText.getText().toString());
        klientas.setPassword(passwordFieldText.getText().toString());

        Gson gson = new Gson();
        String json = gson.toJson(klientas);

        SendJSonData sendJSonData = new SendJSonData(URL);
        sendJSonData.send(json, new CallBack() {
            @Override
            public void onResponseReceive(String message) {
                Runnable newtask = new Runnable() {
                    @Override
                    public void run() {
                        try{
                            int response = Integer.valueOf(message);
                            if(response < 0){
                                informationText.setText("Invalid password.");
                                clearFields();
                            }else {
                                clearFields();
                                Intent intent = new Intent(context, WelcomeActivity.class);
                                intent.putExtra("UserID",response);
                                startActivity(intent);
                            }
                        }catch (NumberFormatException e){
                            informationText.setText(message);
                            clearFields();
                        }
                    }
                };
                runOnUiThread(newtask);
            }
        });
    }

    private void clearFields(){
        logInButton.setEnabled(true);
        signUpButton.setEnabled(true);
        forgotPasswordButton.setEnabled(true);
        usernameFieldText.setText("");
        passwordFieldText.setText("");
    }




}
