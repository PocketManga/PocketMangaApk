package pocketmanga.aplicacao.movel.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Server;

public class ServerJsonParser {

    public static ArrayList<Server> parserJsonServers(JSONArray response) {
        ArrayList<Server> servers = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject server = (JSONObject) response.get(i);

                int idServer = server.getInt("IdServer");
                String name = server.getString("Name");
                String code = server.getString("Code");

                Server auxServer= new Server(idServer, name, code);
                servers.add(auxServer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servers;
    }
}
