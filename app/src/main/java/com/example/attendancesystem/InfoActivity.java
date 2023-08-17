package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendancesystem.R;

public class InfoActivity extends AppCompatActivity {

    TextView totalinfo, nameinfo, staffinfo;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        totalinfo = findViewById(R.id.totalinfo);
        nameinfo = findViewById(R.id.nameinfo);
        staffinfo = findViewById(R.id.staffinfo);
        done = findViewById(R.id.done);

        String name = getIntent().getStringExtra("Name");
        String staffid = getIntent().getStringExtra("StaffID");

        nameinfo.setText(name);
        staffinfo.setText(staffid);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}