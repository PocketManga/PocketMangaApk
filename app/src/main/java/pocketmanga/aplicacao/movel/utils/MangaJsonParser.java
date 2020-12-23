package pocketmanga.aplicacao.movel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Manga;

public class MangaJsonParser {

    public static ArrayList<Manga> parserJsonMangas(JSONArray response) {
        ArrayList<Manga> mangas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject manga = (JSONObject) response.get(i);

                int idManga = manga.getInt("IdManga");
                String image = manga.getString("Image");
                String title = manga.getString("Title");
                String alternativeTitle = manga.getString("AlternativeTitle");
                String originalTitle = manga.getString("OriginalTitle");
                String releaseDate = manga.getString("ReleaseDate");
                String server = manga.getString("Server");
                String list = manga.getString("List");
                String description = manga.getString("Description");
                boolean status = manga.getBoolean("Status");
                boolean oneshot = manga.getBoolean("Oneshot");
                boolean r18 = manga.getBoolean("R18");
                boolean favorite = manga.getBoolean("Favorite");

                Manga auxManga = new Manga(idManga, image, title, alternativeTitle, originalTitle, releaseDate, server, list, description, status, oneshot, r18, favorite);
                mangas.add(auxManga);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mangas;
    }

    public static Manga parserJsonManga(String response) {
        Manga auxManga = null;

        try {
            JSONObject manga = new JSONObject(response);

            int idManga = manga.getInt("IdManga");
            String image = manga.getString("Image");
            String title = manga.getString("Title");
            String alternativeTitle = manga.getString("AlternativeTitle");
            String originalTitle = manga.getString("OriginalTitle");
            String releaseDate = manga.getString("ReleaseDate");
            String server = manga.getString("Server");
            String list = manga.getString("List");
            String description = manga.getString("Description");
            boolean status = manga.getBoolean("Status");
            boolean oneshot = manga.getBoolean("Oneshot");
            boolean r18 = manga.getBoolean("R18");
            boolean favorite = manga.getBoolean("Favorite");

            auxManga = new Manga(idManga, image, title, alternativeTitle, originalTitle, releaseDate, server, list, description, status, oneshot, r18, favorite);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxManga;
    }

    public static String parserJsonLogin(String response) {
        String token = null;

        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;

    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }
}
