package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject(NAME);
            String mainName = nameObject.getString(MAIN_NAME);
            JSONArray akaList = nameObject.getJSONArray(AKA);
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < akaList.length(); i++) {
                alsoKnownAs.add(akaList.getString(i));
            }
            String placeOfOrigin = sandwichObject.getString(PLACE_OF_ORIGIN);
            String description = sandwichObject.getString(DESCRIPTION);
            String image = sandwichObject.getString(IMAGE);
            JSONArray ingredientsList = sandwichObject.getJSONArray(INGREDIENTS);
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsList.length(); i++) {
                ingredients.add(ingredientsList.getString(i));
            }
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
