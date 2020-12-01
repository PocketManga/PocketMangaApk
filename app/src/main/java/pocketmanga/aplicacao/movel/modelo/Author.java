package pocketmanga.aplicacao.movel.modelo;

public class Author {
    private int IdAuthor;
    private String FirstName, LastName;

    public Author(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public int getIdAuthor() {
        return IdAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        IdAuthor = idAuthor;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
