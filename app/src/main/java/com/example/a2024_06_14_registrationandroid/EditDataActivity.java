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

public class EditDataActivity extends AppCompatActivity {

    private Button backButton;
    private Button updateButton;
    private TextView informationText;
    private TextView nameTextField;
    private TextView surnameTextField;
    private TextView phoneNumberTextField;
    private TextView cityTextField;

    private String URL = "http://10.0.2.2:8080/setKlientoInfo";
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        backButton = findViewById(R.id.edit_data_back_button);
        updateButton = findViewById(R.id.edit_data_update_button);

        backButton.setOnClickListener(view -> backButtonClicked(view));
        updateButton.setOnClickListener(view -> updateButtonClicked(view));

        informationText = findViewById(R.id.edit_data_informationText);
        informationText.setAlpha(0);


        nameTextField = findViewById(R.id.edit_data_name);
        surnameTextField = findViewById(R.id.edit_data_surname);
        phoneNumberTextField = findViewById(R.id.edit_data_phone_number);
        cityTextField = findViewById(R.id.edit_data_city);

        userID = getIntent().getIntExtra("UserID",0);

    }


    public void backButtonClicked(View view){

        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void updateButtonClicked(View view){

        backButton.setEnabled(false);
        updateButton.setEnabled(false);

        informationText.setText("Please wait...");
        informationText.setAlpha(1);

        Klientas klientas = new Klientas();

        klientas.setId(userID);
        klientas.setName(nameTextField.getText().toString());
        klientas.setSurname(surnameTextField.getText().toString());
        klientas.setPhoneNumber(phoneNumberTextField.getText().toString());
        klientas.setCity(cityTextField.getText().toString());


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
                        nameTextField.setText("");
                        surnameTextField.setText("");
                        phoneNumberTextField.setText("");
                        cityTextField.setText("");
                        backButton.setEnabled(true);
                        updateButton.setEnabled(true);
                    }
                };
                runOnUiThread(newtask);
            }
        });




    }
}
