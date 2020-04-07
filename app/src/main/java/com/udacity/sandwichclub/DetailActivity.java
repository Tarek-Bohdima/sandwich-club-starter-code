package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originLabel = findViewById(R.id.origin_label);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView akaLabel = findViewById(R.id.aka_label);
        TextView akaTv = findViewById(R.id.aka_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        originTv.setText(sandwich.getPlaceOfOrigin());
        akaTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));
        descriptionTv.setText(sandwich.getDescription());

        if (akaTv.getText().toString().isEmpty()) {
            akaLabel.setVisibility(View.GONE);
            akaTv.setVisibility(View.GONE);
        } else {
            akaLabel.setVisibility(View.VISIBLE);
            akaTv.setVisibility(View.VISIBLE);
        }

        if (originTv.getText().toString().isEmpty()) {
            originLabel.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            originLabel.setVisibility(View.VISIBLE);
            originTv.setVisibility(View.VISIBLE);
        }

    }
}
