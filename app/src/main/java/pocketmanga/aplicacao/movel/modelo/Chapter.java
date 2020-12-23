package pocketmanga.aplicacao.movel.modelo;

public class Chapter {
    private int IdChapter, PagesNumber, Season, MangaId;
    private double Number;
    private String Name, ReleaseDate, SrcFolder, UrlImage;
    private boolean OneShot, Readed;

    public Chapter(int idChapter, int pagesNumber, int season, int mangaId, double number, String name, String releaseDate, String srcFolder, String urlImage, boolean oneShot, boolean readed) {
        IdChapter = idChapter;
        PagesNumber = pagesNumber;
        Season = season;
        Number = number;
        MangaId = mangaId;
        Name = name;
        ReleaseDate = releaseDate;
        SrcFolder = srcFolder;
        UrlImage = urlImage;
        OneShot = oneShot;
        Readed = readed;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public void setUrlImage(String urlImage) {
        UrlImage = urlImage;
    }

    public boolean isReaded() {
        return Readed;
    }

    public void setReaded(boolean readed) {
        Readed = readed;
    }

    public void setIdChapter(int idChapter) {
        IdChapter = idChapter;
    }

    public int getIdChapter() {
        return IdChapter;
    }

    public int getPagesNumber() {
        return PagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        PagesNumber = pagesNumber;
    }

    public int getMangaId() {
        return MangaId;
    }

    public void setMangaId(int mangaId) {
        MangaId = mangaId;
    }

    public void setNumber(double number) {
        Number = number;
    }

    public int getSeason() {
        return Season;
    }

    public void setSeason(int season) {
        Season = season;
    }

    public double getNumber() {
        return Number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getSrcFolder() {
        return SrcFolder;
    }

    public void setSrcFolder(String srcFolder) {
        SrcFolder = srcFolder;
    }

    public boolean isOneShot() {
        return OneShot;
    }

    public void setOneShot(boolean oneShot) {
        OneShot = oneShot;
    }
}
