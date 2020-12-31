package pocketmanga.aplicacao.movel.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Category;
import pocketmanga.aplicacao.movel.modelo.User;

public class UserJsonParser {

    public static User parserJsonUser(JSONObject response) {
        User user = null;
        try {
            int idUser = response.getInt("IdUser");
            int server_Id = response.getInt("Server_Id");
            String username = response.getString("Username");
            String email = response.getString("Email");
            String urlPhoto = response.getString("UrlPhoto");
            boolean chapterShow = response.getBoolean("ChapterShow");
            boolean mangaShow = response.getBoolean("MangaShow");
            boolean theme = response.getBoolean("Theme");

            user = new User(idUser, server_Id, username, email, urlPhoto, chapterShow, mangaShow, theme);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
