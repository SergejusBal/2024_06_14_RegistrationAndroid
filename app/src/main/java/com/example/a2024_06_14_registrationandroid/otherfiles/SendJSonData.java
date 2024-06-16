package com.example.a2024_06_14_registrationandroid.otherfiles;

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendJSonData {

    private String response;
    private OkHttpClient client;
    private CallBack callBack;
    private final String URL;
    private final String INFOTAG = "MyTAG SendJSonData";


    public SendJSonData(String URl) {
        this.URL = URl;
    }


    public void send(String JSonStringToSend, CallBack callBack){

        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, JSonStringToSend);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Runnable newTask = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String message = response.body().string();
                    callBack.onResponseReceive(message);
                } catch (SocketTimeoutException e){
                    callBack.onResponseReceive("Connection failed.");
                    Log.d(INFOTAG, e.getMessage());
                } catch (IOException e) {
                    Log.d(INFOTAG, e.getMessage());
                }

            }
        };
        Thread thread = new Thread(newTask);
        thread.start();
    }

}
