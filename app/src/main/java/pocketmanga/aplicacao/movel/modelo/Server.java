package pocketmanga.aplicacao.movel.modelo;

public class Server {
    private int IdServer;
    private String Name, Code;

    public Server(int idServer, String name, String code) {
        IdServer = idServer;
        Name = name;
        Code = code;
    }

    public int getIdServer() {
        return IdServer;
    }

    public void setIdServer(int idServer) {
        IdServer = idServer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
