package pocketmanga.aplicacao.movel.listeners;

import pocketmanga.aplicacao.movel.modelo.User;

public interface LoginListener {
    void onValidateLogin(String token,String username, int IdUser);
    void onAutoLogin(User user);
}
