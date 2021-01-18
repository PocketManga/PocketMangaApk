package pocketmanga.aplicacao.movel.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginJsonParser {
    public static String parserJsonToken(String response) {
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
    public static String parserJsonUsername(String response) {
        String username = null;

        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                username = login.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static int parserJsonIdUser(String response) {
        int idUser = 0;

        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                idUser = login.getInt("idUser");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return idUser;
    }
}
