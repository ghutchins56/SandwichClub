package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            mainName = name.getString("mainName");
            JSONArray alsoKnownAsJSON = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJSON.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJSON.getString(i));
            }
            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            description = sandwichJson.getString("description");
            image = sandwichJson.getString("image");
            JSONArray ingredientsJSON = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJSON.length(); i++) {
                ingredients.add(ingredientsJSON.getString(i));
            }
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e("JsonUtils", "JSON parsing error");
        }
        return sandwich;
    }
}
