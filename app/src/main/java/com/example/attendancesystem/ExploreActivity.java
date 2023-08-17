package com.example.attendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancesystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.io.File;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {
    ImageView imgPreview;
    EditText nameBox, staffIDBox;
    Button submitButton, restartButton;
    TextView textPreview;

    private FaceDetector faceDetector;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        imgPreview = findViewById(R.id.imgPreview);
        nameBox = findViewById(R.id.nameBox);
        staffIDBox = findViewById(R.id.staffIDBox);
        submitButton = findViewById(R.id.submitButton);
        textPreview = findViewById(R.id.textPreview);
        restartButton = findViewById(R.id.restartButton);

        String strImgPath = getIntent().getStringExtra("FilePath");
        File img = new File(strImgPath);

        FaceDetectorOptions highAccuracyOpts = new FaceDetectorOptions.Builder().setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE).enableTracking().build();
        faceDetector = FaceDetection.getClient(highAccuracyOpts);

        Bitmap bitmap;

        if(img.exists()){
            bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());

            InputImage inputImage = InputImage.fromBitmap(bitmap,0);
            faceDetector.process(inputImage).addOnSuccessListener(faces-> {
                if(faces.isEmpty()){
                    textPreview.setText("No Face Found");
                    submitButton.setVisibility(View.INVISIBLE);
                    nameBox.setVisibility(View.INVISIBLE);
                    staffIDBox.setVisibility(View.INVISIBLE);
                    restartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openMainActivity();
                        }
                    });
                }
                else {
                    imgPreview.setImageBitmap(bitmap);
                    restartButton.setVisibility(View.INVISIBLE);

                    for (Face face : faces) {
                        if (face.getTrackingId() != null) {
                            int uid = face.getTrackingId();
                            runOnUiThread(() -> Toast.makeText(this, "Face Detected! ID: " + uid, Toast.LENGTH_SHORT).show());
                        }
                    }

                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String n = nameBox.getText().toString();
                            String sid = staffIDBox.getText().toString();

                            Intent intent = new Intent(ExploreActivity.this, InfoActivity.class);
                            intent.putExtra("Name", n);
                            intent.putExtra("StaffID", sid);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

//            imgPreview.setImageBitmap(bitmap);

        }

    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}