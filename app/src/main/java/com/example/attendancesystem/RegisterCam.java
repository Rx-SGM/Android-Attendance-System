package com.example.attendancesystem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

public class RegisterCam extends AppCompatActivity {


    PreviewView previewView;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if(result) startCamera();
        else runOnUiThread(() -> Toast.makeText(this, "Please give camera access!!", Toast.LENGTH_SHORT).show());
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cam);

        previewView = findViewById(R.id.camPreview);

        if(ContextCompat.checkSelfPermission(RegisterCam.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        else
            startCamera();
    }

    private int aspectRatio(int width, int height) {
        double previewRatio =  (double) Math.max(width, height) / Math.min(width, height);
        if(Math.abs(previewRatio - 4.0/3.0) <= Math.abs(previewRatio - 16.0/9.0)) return AspectRatio.RATIO_4_3;
        else return  AspectRatio.RATIO_16_9;
    }

    private void startCamera() {
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(()->{
            try {
                ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) listenableFuture.get();
                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

                CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();

                processCameraProvider.unbindAll();

                Camera camera = processCameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                );

//                FaceDetectorOptions realTimeOpts =
//                        new FaceDetectorOptions.Builder()
//                                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
//                                .build();
//
//
//
//                assert bitmap != null;
//                InputImage image = InputImage.fromBitmap(bitmap, 0);
//                FaceDetector detector = (FaceDetector) FaceDetection.getClient(realTimeOpts);

//                detector.process(image).addOnSuccessListener(new OnSuccessListener<List<Face>>() {
//                    @Override
//                    public void onSuccess(List<Face> faces) {
//                        if(!faces.isEmpty()){
//                            Toast.makeText(MainActivity.this, "Face Detected!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        e.printStackTrace();
//                    }
//                });


//                toggleFlash.setOnClickListener(view -> toggleFlash(camera));
//
//                capture.setOnClickListener(view -> {
//                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                        activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    else
//                        captureImage(imageCapture);
//                });

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(RegisterCam.this, OnLogin.class);
        startActivity(intent);
        finish();
    }
}