package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    // Create fields to store all TextViews & image view in activity_detail.xml

    private ImageView mSandwichIvImageView;
    private TextView mKnownLabelTextView;
    private TextView mKnownTvTextView;
    private TextView mOriginLabelTextView;
    private TextView mOriginTvTextView;
    private TextView mIngredientLabelTextView;
    private TextView mIngredientTvTextView;
    private TextView mDescriptionLabelTextView;
    private TextView mDescriptionTvTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Use findViewById to get a references to TextViews & imageView
        mSandwichIvImageView = findViewById(R.id.image_iv);
        mKnownLabelTextView = findViewById(R.id.Known_label);
        mKnownTvTextView = findViewById(R.id.also_known_tv);
        mOriginLabelTextView = findViewById(R.id.origin_label);
        mOriginTvTextView = findViewById(R.id.origin_tv);
        mIngredientLabelTextView = findViewById(R.id.ingredients_label);
        mIngredientTvTextView = findViewById(R.id.ingredients_tv);
        mDescriptionLabelTextView = findViewById(R.id.description_label);
        mDescriptionTvTextView = findViewById(R.id.description_tv);


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
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Sandwich sandwich) {

        //load image


            Picasso.with(this)
                    .load(sandwich.getImage())
                    //.placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(mSandwichIvImageView);



        // set Text to mKnownTvTextView
        //we want to display all  alsoKnownAs
        if (sandwich.getAlsoKnownAs().isEmpty()) {

            mKnownTvTextView.setVisibility(View.GONE);
            mKnownLabelTextView.setVisibility(View.GONE);

        } else {
            StringBuilder knownBuilder = new StringBuilder();
            knownBuilder.append(sandwich.getIngredients().get(0));
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {

                knownBuilder.append(" || ");
                knownBuilder.append(sandwich.getAlsoKnownAs().get(i));

            }
            mKnownTvTextView.setText(knownBuilder.toString());
        }

        // set Text to mOriginTvTextView

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTvTextView.setVisibility(View.GONE);
            mOriginLabelTextView.setVisibility(View.GONE);
        } else {
            mOriginTvTextView.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to mIngredientTvTextView
        //we want to display all ingredients

        if (sandwich.getIngredients().isEmpty()) {
            mIngredientTvTextView.setVisibility(View.GONE);
            mIngredientLabelTextView.setVisibility(View.GONE);

        } else {
            StringBuilder ingredientBuilder = new StringBuilder();
            ingredientBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                ingredientBuilder.append(" & ");
                ingredientBuilder.append(sandwich.getIngredients().get(i));
            }
            mIngredientTvTextView.setText(ingredientBuilder.toString());
        }

        // set Text to mDescriptionTvTextView
        if (sandwich.getDescription().isEmpty()) {

            mDescriptionTvTextView.setVisibility(View.GONE);
            mDescriptionLabelTextView.setVisibility(View.GONE);
        }else {
            mDescriptionTvTextView.setText(sandwich.getDescription());
        }

    }
}
