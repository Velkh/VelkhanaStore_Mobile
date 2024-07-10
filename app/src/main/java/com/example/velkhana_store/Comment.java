package com.example.velkhana_store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.velkhana_store.Model.Comments;
import com.example.velkhana_store.databinding.ActivityCommentsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment extends AppCompatActivity {

    private EditText registerUsername, registerComments;
    private Button submitButton;
    private DatabaseReference databaseReference;
    private ActivityCommentsBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_comments:
                    replaceFragment(new CommentFragment());
                    break;
                case R.id.nav_logout:
                    // Handle logout action
                    // Example: startActivity(new Intent(this, LoginActivity.class));
                    // finish();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.comment:
                    replaceFragment(new CommentFragment());
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layin, fragment);
        fragmentTransaction.commit();
    }
}
