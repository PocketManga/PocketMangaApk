package pocketmanga.aplicacao.movel.modelo;

public class Manga {
    private int IdManga;
    private String Image, Title, AlternativeTitle, OriginalTitle, ReleaseDate, Server, List, Description;
    private boolean Status, Oneshot, R18, Favorite;

    public Manga(int idManga, String image, String title, String alternativeTitle, String originalTitle, String releaseDate, String server,
                 String list, String description, boolean status, boolean oneshot, boolean r18, boolean favorite) {
        IdManga = idManga;
        Image = image;
        Title = title;
        AlternativeTitle = alternativeTitle;
        OriginalTitle = originalTitle;
        ReleaseDate = releaseDate;
        Server = server;
        Description = description;
        Status = status;
        Oneshot = oneshot;
        R18 = r18;
        Favorite = favorite;
        List = list;
    }

    public String getList() {
        return List;
    }

    public void setList(String list) {
        List = list;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public int getIdManga() {
        return IdManga;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAlternativeTitle() {
        return AlternativeTitle;
    }

    public void setAlternativeTitle(String alternativeTitle) {
        AlternativeTitle = alternativeTitle;
    }

    public void setIdManga(int idManga) {
        IdManga = idManga;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public boolean isOneshot() {
        return Oneshot;
    }

    public void setOneshot(boolean oneshot) {
        Oneshot = oneshot;
    }

    public boolean isR18() {
        return R18;
    }

    public void setR18(boolean r18) {
        R18 = r18;
    }
}
