package pocketmanga.aplicacao.movel.modelo;

public class User {
    private int IdUser, Server_Id;
    private String Username, Email, UrlPhoto;
    private boolean ChapterShow, MangaShow, Theme;

    public User(int idUser, int server_Id, String username, String email, String urlPhoto, boolean chapterShow, boolean mangaShow, boolean theme) {
        IdUser = idUser;
        Server_Id = server_Id;
        Username = username;
        Email = email;
        UrlPhoto = urlPhoto;
        ChapterShow = chapterShow;
        MangaShow = mangaShow;
        Theme = theme;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getServer_Id() {
        return Server_Id;
    }

    public void setServer_Id(int server_Id) {
        Server_Id = server_Id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUrlPhoto() {
        return UrlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        UrlPhoto = urlPhoto;
    }

    public boolean isChapterShow() {
        return ChapterShow;
    }

    public void setChapterShow(boolean chapterShow) {
        ChapterShow = chapterShow;
    }

    public boolean isMangaShow() {
        return MangaShow;
    }

    public void setMangaShow(boolean mangaShow) {
        MangaShow = mangaShow;
    }

    public boolean isTheme() {
        return Theme;
    }

    public void setTheme(boolean theme) {
        Theme = theme;
    }
}
