package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancesystem.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AdminLogin extends AppCompatActivity {

    EditText username, password;
    MaterialButton loginbtn;
    RelativeLayout mainLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        mainLayout =(RelativeLayout) findViewById(R.id.mainLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.start();

        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
                    Toast.makeText(AdminLogin.this,"LOGIN SUCCESSFUL!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminLogin.this, OnLogin.class);
                    startActivity(intent);
                    finish();

                }else
                    //incorrect
                    Toast.makeText(AdminLogin.this,"LOGIN FAILED!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(getCurrentFocus()!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AdminLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}