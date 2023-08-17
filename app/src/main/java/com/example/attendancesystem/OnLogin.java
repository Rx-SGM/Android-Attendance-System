package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class OnLogin extends AppCompatActivity {

    ProgressBar progressBar;
    int counter = 0;
    Button register, train, list_btn;

    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_login);

        register = findViewById(R.id.reg_btn);
        train = findViewById(R.id.train_btn);
        progressBar = findViewById(R.id.progressBar);
        list_btn = findViewById(R.id.list_btn);

        RelativeLayout relativeLayout = findViewById(R.id.rel_layout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnLogin.this, RegisterCam.class);
                startActivity(intent);
                finish();
            }
        });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                progressBar.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar.setProgress(counter);
                        if(counter == 100){
                            timer.cancel();
                            runOnUiThread(() -> Toast.makeText(OnLogin.this, "Training Successful!", Toast.LENGTH_SHORT).show());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                };
                timer.schedule(timerTask,100,100);
            }
        });


        String url = "http://192.168.105.39:5006/punch";


// Request a string response from the provided URL.

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(OnLogin.this);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("pohcha_response");
                                try {
                                    String staffname = response.getString("staffname");
                                    Toast.makeText(OnLogin.this, staffname, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    System.out.println("pohcha_exception");
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Toast.makeText(OnLogin.this, "Nope", Toast.LENGTH_SHORT).show();
                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        Map<String, String> params = new HashMap<>();
                        params.put("staffnum","12345");
                        params.put("punchtime","2023-07-14 14:32:10");
                        params.put("deviceid","123");
                        return params;
                    }
                };

                queue.add(request);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(OnLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}