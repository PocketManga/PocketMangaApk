package pocketmanga.aplicacao.movel.listeners;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Category;
import pocketmanga.aplicacao.movel.modelo.Server;

public interface ServersListener {
    void onRefreshServersList(ArrayList<Server> ServersList);
}
