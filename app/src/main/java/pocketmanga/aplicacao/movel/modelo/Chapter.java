package pocketmanga.aplicacao.movel.modelo;

public class Chapter {
    private int IdChapter, PagesNumber, Season;
    private float Number;
    private String Name, ReleaseDate, SrcFolder;
    private boolean OneShot;

    public Chapter(int idChapter, int pagesNumber, int season, float number, String name, String releaseDate, String srcFolder, boolean oneShot) {
        IdChapter = idChapter;
        PagesNumber = pagesNumber;
        Season = season;
        Number = number;
        Name = name;
        ReleaseDate = releaseDate;
        SrcFolder = srcFolder;
        OneShot = oneShot;
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

    public int getSeason() {
        return Season;
    }

    public void setSeason(int season) {
        Season = season;
    }

    public float getNumber() {
        return Number;
    }

    public void setNumber(float number) {
        Number = number;
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
