package com.example.velkhana_store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.velkhana_store.Model.Comments;

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

        databaseReference = FirebaseDatabase.getInstance().getReference("comments");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerComms();
            }
        });
    }

    private void registerComms() {
        String username = registerUsername.getText().toString().trim();
        String comments = registerComments.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(comments)) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        Comments comms = new Comments(username, comments);
        databaseReference.child(username).setValue(comms);

        Toast.makeText(this, "Comment registered successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
