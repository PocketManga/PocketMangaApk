package pocketmanga.aplicacao.movel.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Category;
import pocketmanga.aplicacao.movel.modelo.Manga;

public class CategoryJsonParser {

    public static ArrayList<Category> parserJsonCategories(JSONArray response) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject category = (JSONObject) response.get(i);

                int idCategory = category.getInt("IdCategory");
                int numMangas = category.getInt("NumMangas");
                String name = category.getString("Name");
                String nameUrl = category.getString("NameUrl");

                Category auxCategory = new Category(idCategory, numMangas, name, nameUrl);
                categories.add(auxCategory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
