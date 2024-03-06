package com.spenditure.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spenditure.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.spenditure.presentation.transaction.CreateTransactionActivity;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ImageCaptureActivity extends AppCompatActivity {

    // Camera permission variables
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.CAMERA};

    // Image variables
    private PreviewView previewView;
    private ImageButton captureButton;
    private Button confirmButton;
    private ImageView imageView;
    private ImageCapture imageCapture;

    // Captured image as byte array
    private byte[] imageBytes;
    // Is the camera being previewed?
    private boolean previewMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        previewView = findViewById(R.id.previewview_camera);
        captureButton = findViewById(R.id.button_take_image);
        confirmButton = findViewById(R.id.button_confirm);
        imageView = findViewById(R.id.imageview_image);

        if(cameraPermissionsGranted()){
            startCamera();
        } else {
            // Request camera permissions from the user
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        captureButton.setOnClickListener(view -> {
            if (previewMode) {
                takePhoto();

                // Change the button visibilities
                captureButton.setVisibility(View.GONE);
                confirmButton.setVisibility(View.VISIBLE);
            }
        });

        confirmButton.setOnClickListener(view -> {
            try {
                // Return to the Create Transaction activity that called this one
                Intent goBackIntent = new Intent();
                // Use a bundle because the byte arrays are really big
                Bundle bundle = new Bundle();
                bundle.putByteArray("imageBytes", imageBytes);
                goBackIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK, goBackIntent);
                finish();
            } catch (Exception e) {
                Toast.makeText(ImageCaptureActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Check if permission has been granted to use the camera
    private boolean cameraPermissionsGranted(){
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (cameraPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Capture an image and save it as a byte array
    private void takePhoto() {
        // Capture the image
        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                // Convert the image into bytes
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                imageBytes = new byte[buffer.remaining()];
                buffer.get(imageBytes);

                processCapturedImage();

                // Stop the previewing
                previewView.setVisibility(View.GONE);
                previewMode = false;

                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                // Handle capture error
                Toast.makeText(ImageCaptureActivity.this, "Image capture error " + exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Initialize the camera and all it's required settings
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(ImageCaptureActivity.this);

        cameraProviderFuture.addListener(() -> {
                    try {
                        ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                        Preview preview = new Preview.Builder().build();

                        // Select back camera as a default
                        CameraSelector cameraSelector = new CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build();

                        // Attach the preview use case to the view
                        preview.setSurfaceProvider(previewView.getSurfaceProvider());

                        // Set up image capture use case
                        imageCapture = new ImageCapture.Builder().build();

                        // Unbind any previous use cases before binding new ones
                        cameraProvider.unbindAll();

                        // Bind use cases to camera
                        Camera camera = cameraProvider.bindToLifecycle(
                                this, cameraSelector, preview, imageCapture);

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, ContextCompat.getMainExecutor(this)
        );
    }

    // Display the captured image as a bitmap
    private void processCapturedImage() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Compress the bitmap to reduce byte[] size
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, os);
        imageBytes = os.toByteArray();

        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
        imageView.setRotation(270);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}