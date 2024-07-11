package com.example.velkhana_store;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.velkhana_store.Model.Item;

public class DetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView imageView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTextView = findViewById(R.id.nameTextView);
        imageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        // Get the passed item
        Item item = (Item) getIntent().getSerializableExtra("item");

        // Set the item details
        if (item != null) {
            nameTextView.setText(item.getName());
            Glide.with(this).load(item.getImageUrl()).into(imageView);
            descriptionTextView.setText(item.getDescription());
        }
    }
}
