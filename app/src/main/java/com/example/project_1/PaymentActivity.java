package com.example.project_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PaymentActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        MaterialButton downloadImageBtn = findViewById(R.id.downloadImageButton);
        MaterialButton completeTransactionBtn = findViewById(R.id.completeTransactionButton);
        ImageView qrCodeImage = findViewById(R.id.qrCodeImage);

        // Handle transaction completion
        completeTransactionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, FinishedTransactionActivity.class);
            startActivity(intent);
        });

        // Handle image download
        downloadImageBtn.setOnClickListener(v -> {
            // Check permissions for saving to storage (improved)
            if (checkStoragePermission()) {
                saveImageToGallery(qrCodeImage);
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void saveImageToGallery(ImageView imageView) {
        try {
            // Extract the bitmap from the ImageView
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable == null) {
                Toast.makeText(this, "No image to save!", Toast.LENGTH_SHORT).show();
                return;
            }
            Bitmap bitmap = drawable.getBitmap();

            // Use ContextCompat to get the public directory for saving (improved)
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (directory == null) {
                Toast.makeText(this, "Failed to get storage directory.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create directory (improved)
            directory = new File(directory, "payment");
            if (!directory.exists() && !directory.mkdirs()) {
                Toast.makeText(this, "Failed to create directory.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create the file
            String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
            File imageFile = new File(directory, fileName);

            // Write the bitmap to the file
            try (OutputStream outputStream = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                Toast.makeText(this, "QR Code saved to gallery!", Toast.LENGTH_SHORT).show();

                // Add the image to the gallery (MediaScanner)
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(imageFile);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Image Save", "saveImageToGallery: " + e.getMessage());
            Toast.makeText(this, "Failed to save image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Save the image after permission is granted
                ImageView qrCodeImage = findViewById(R.id.qrCodeImage);
                saveImageToGallery(qrCodeImage);
            } else {
                Toast.makeText(this, "Permission denied. Unable to save image.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
