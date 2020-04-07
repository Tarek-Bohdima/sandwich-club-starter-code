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


    ImageView ingredientsIv;
    TextView originLabel;
    TextView originTv;
    TextView akaLabel;
    TextView akaTv;
    TextView ingredientsTv;
    TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        initialize();

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

    // Using findViewById, we get a reference to our TextView from xml.
    private void initialize() {
        ingredientsIv = findViewById(R.id.image_iv);

        originLabel = findViewById(R.id.origin_label);
        originTv = findViewById(R.id.origin_tv);

        akaLabel = findViewById(R.id.aka_label);
        akaTv = findViewById(R.id.aka_tv);

        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        originTv.setText(sandwich.getPlaceOfOrigin());
        akaTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));
        descriptionTv.setText(sandwich.getDescription());

        if (akaTv.getText().toString().isEmpty()) {
            hideAka();
        } else {
            showAka();
        }

        if (originTv.getText().toString().isEmpty()) {
            hideOrigin();
        } else {
            showOrigin();
        }

    }

    // a method to show place of Origin label and text
    private void showOrigin() {
        originLabel.setVisibility(View.VISIBLE);
        originTv.setVisibility(View.VISIBLE);
    }

    // a method to hide place of Origin label and text
    private void hideOrigin() {
        originLabel.setVisibility(View.GONE);
        originTv.setVisibility(View.GONE);
    }

    // a method to show the "Also Known As" label and text
    private void hideAka() {
        akaLabel.setVisibility(View.GONE);
        akaTv.setVisibility(View.GONE);
    }

    // a method to hide the "Also Known As" label and text
    private void showAka() {
        akaLabel.setVisibility(View.VISIBLE);
        akaTv.setVisibility(View.VISIBLE);
    }

}
