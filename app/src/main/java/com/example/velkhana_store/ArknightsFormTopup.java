package com.example.velkhana_store;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.example.velkhana_store.Model.Payment;

public class ArknightsFormTopup extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText accid, email, phone;
    private Spinner platform, jumlah;
    private TextView paymentPhone, price;
    private Button uploadImage, submit;
    private ImageView imagePreview;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akpaymentform);

        accid = findViewById(R.id.accid);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        jumlah = findViewById(R.id.jumlah);
        platform = findViewById(R.id.platform);
        paymentPhone = findViewById(R.id.payment_phone);
        price = findViewById(R.id.price);
        uploadImage = findViewById(R.id.upload_image);
        imagePreview = findViewById(R.id.image_preview);
        submit = findViewById(R.id.submit);

        storageReference = FirebaseStorage.getInstance().getReference("Bukti_Pembayaran");
        databaseReference = FirebaseDatabase.getInstance().getReference("Payment");

        setupPlatformSpinner();
        setupJumlahSpinner();

        platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlatform = platform.getSelectedItem().toString();
                Map<String, String> phoneNumbers = new HashMap<>();
                phoneNumbers.put("Telkomsel", "0811-1234-5678");
                phoneNumbers.put("Indosat", "0815-1234-5678");
                phoneNumbers.put("dana", "0857-1234-5678");
                phoneNumbers.put("gopay", "0812-1234-5678");

                String phoneNumber = phoneNumbers.get(selectedPlatform);
                if (phoneNumber != null) {
                    paymentPhone.setText("Please transfer to: " + phoneNumber);
                    paymentPhone.setVisibility(View.VISIBLE);
                } else {
                    paymentPhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                paymentPhone.setVisibility(View.GONE);
            }
        });

        jumlah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> prices = new HashMap<>();
                prices.put("Monthly Card", "Price: Rp 70.671");
                prices.put("Monthly Headhunting Pack", "Price: Rp 381.620");
                prices.put("1 Originium", "Price: Rp 14.050");
                prices.put("6 Originium", "Price: Rp 70.250");
                prices.put("20 Originium", "Price: Rp 210.750");
                prices.put("40 Originium", "Price: Rp 421.500");
                prices.put("66 Originium", "Price: Rp 702.500");
                prices.put("130 Originium", "Price: Rp 1.405.000");

                price.setText(prices.get(jumlah.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                price.setText("");
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadForm();
            }
        });
    }

    private void setupPlatformSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.platforms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platform.setAdapter(adapter);
    }

    private void setupJumlahSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jumlah_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jumlah.setAdapter(adapter);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
        }
    }

    private void uploadForm() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl = task.getResult().toString();
                                            saveFormData(downloadUrl);
                                        } else {
                                            Toast.makeText(ArknightsFormTopup.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ArknightsFormTopup.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFormData(String imageUrl) {
        Integer gameId = 1;
        String accId = accid.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String phone = this.phone.getText().toString().trim();
        String jumlah = this.jumlah.getSelectedItem().toString();
        String platform = this.platform.getSelectedItem().toString();
        String status = "Order Process";

        if (!accId.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !jumlah.isEmpty()) {
            // Create a Payment object
            Payment payment = new Payment(gameId, accId, email, phone, jumlah, platform, imageUrl, status);

            // Push to Firebase Realtime Database
            databaseReference.push().setValue(payment)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ArknightsFormTopup.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                                resetForm();
                            } else {
                                Toast.makeText(ArknightsFormTopup.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetForm() {
        accid.setText("");
        email.setText("");
        phone.setText("");
        jumlah.setSelection(0);
        platform.setSelection(0);
        paymentPhone.setVisibility(View.GONE);
        price.setText("");
        imagePreview.setImageURI(null);
    }

    private String getFileExtension(Uri uri) {
        String extension;
        // Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            // If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(getContentResolver().getType(uri));
        } else {
            // If scheme is a File
            // This will replace white spaces with %20 and also other special characters.
            // This will avoid returning null values on file
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }
}