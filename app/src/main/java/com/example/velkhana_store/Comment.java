package com.example.velkhana_store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.velkhana_store.Model.Comments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment extends AppCompatActivity {

    private EditText registerUsername, registerComments;
    private Button submitButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        registerUsername = findViewById(R.id.registerUsername);
        registerComments = findViewById(R.id.registerComments);
        submitButton = findViewById(R.id.submitButton);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("comments");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerComment();
            }
        });
    }

    private void registerComment() {
        String name = registerUsername.getText().toString().trim();
        String comments = registerComments.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(comments)) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(new Date());

        // Create a new comment object with timestamp
        Comments comment = new Comments(name, comments, timestamp);

        // Save the comment to Firebase Database
        databaseReference.push().setValue(comment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Comment.this, "Comment registered successfully", Toast.LENGTH_SHORT).show();
                        registerUsername.setText("");
                        registerComments.setText("");
                    } else {
                        Toast.makeText(Comment.this, "Failed to register comment", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
