package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);

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
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        originTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < ingredientsList.size(); i++) {
            if (i > 0) {
                ingredients.append(", ");
                ingredients.append(ingredientsList.get(i).toLowerCase());
            } else {
                ingredients.append(ingredientsList.get(i));
            }
        }
        ingredientsTv.setText(ingredients);
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnown = new StringBuilder();
        for (int i = 0; i < alsoKnownList.size(); i++) {
            if (i > 0) alsoKnown.append(", ");
            alsoKnown.append(alsoKnownList.get(i));
        }
        alsoKnownTv.setText(alsoKnown);
    }
}
