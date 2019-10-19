package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String JSONObject) {
        try {
            JSONObject JsonObject = new JSONObject(JSONObject);

            JSONObject name = JsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");
            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = FormListFromJson(JSONArrayAlsoKnownAs);

            String Origin = JsonObject.getString("placeOfOrigin");

            String description = JsonObject.getString("description");

            String image = JsonObject.getString("image");

            JSONArray JSONArrayIngredients = JsonObject.getJSONArray("ingredients");
            List<String> ingredients = FormListFromJson(JSONArrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, Origin, description, image, ingredients);

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }
    private static List<String> FormListFromJson(JSONArray arr) throws JSONException {

        if(arr==null){
            return null;
        }
        List<String> newList = new ArrayList<>(arr.length());

        for (int i = 0; i < arr.length(); i++) {
            newList.add(arr.getString(i));
        }

        return newList;
    }
}
