package pocketmanga.aplicacao.movel.modelo;

public class Category {
    private int IdCategory, NumMangas;
    private String Name, NameUrl;

    public Category(int idCategory, int numMangas, String name, String nameUrl) {
        IdCategory = idCategory;
        NumMangas = numMangas;
        Name = name;
        NameUrl = nameUrl;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public int getNumMangas() {
        return NumMangas;
    }

    public void setNumMangas(int numMangas) {
        NumMangas = numMangas;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNameUrl() {
        return NameUrl;
    }

    public void setNameUrl(String nameUrl) {
        NameUrl = nameUrl;
    }
}
