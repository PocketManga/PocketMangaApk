package pocketmanga.aplicacao.movel.modelo;

public class Category {
    private int IdCategory;
    private String Name;

    public Category(String name) {
        Name = name;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
