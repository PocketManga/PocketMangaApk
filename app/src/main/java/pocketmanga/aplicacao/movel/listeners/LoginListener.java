package pocketmanga.aplicacao.movel.listeners;

public interface LoginListener {
    void onValidateLogin(String token,String email, int IdUser);
}
