package pocketmanga.aplicacao.movel.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.listeners.CategoriesListener;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.listeners.LoginListener;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.listeners.ServersListener;
import pocketmanga.aplicacao.movel.utils.CategoryJsonParser;
import pocketmanga.aplicacao.movel.utils.ChapterJsonParser;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;
import pocketmanga.aplicacao.movel.utils.LoginJsonParser;
import pocketmanga.aplicacao.movel.utils.MangaJsonParser;
import pocketmanga.aplicacao.movel.utils.ServerJsonParser;
import pocketmanga.aplicacao.movel.utils.UserJsonParser;

public class SingletonGestorPocketManga {
    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD =2 ;
    private static final int REMOVER_BD =3 ;
    private static final String MANGAS_KEY = "mangas";

    private static SingletonGestorPocketManga instance = null;

    private ArrayList<Manga> mangas;
    private ArrayList<Chapter> chapters;
    private ArrayList<Category> categories;
    private ArrayList<Server> servers;
    private User user;

    private MangaBDHelper mangasBD;
    private ChapterBDHelper chaptersBD;

    private static RequestQueue volleyQueue = null;

    private static final String BASE_URL = "http://192.168.137.1/PocketManga/backend/web/";
    private static final String mUrlAPIMangas = "api/manga/";
    private static final String mUrlAPIChapters = "api/manga/";
    private static final String mUrlAPICategories = "api/category/";
    private static final String mUrlAPIServer = "api/server";
    private static final String mUrlAPIUser = "api/user/";

    private MangasListener mangasListener;
    private ChaptersListener chaptersListener;
    private CategoriesListener categoriesListener;
    private ServersListener serversListener;
    private LoginListener loginListener;

    public static synchronized SingletonGestorPocketManga getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorPocketManga(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    public SingletonGestorPocketManga(Context context) {
        mangas = new ArrayList<>();
        chapters = new ArrayList<>();
        mangasBD = new MangaBDHelper(context);
        chaptersBD = new ChapterBDHelper(context);
    }

    public void setMangasListener(MangasListener mangasListener) {
        this.mangasListener = mangasListener;
    }

    public void setServersListener(ServersListener serversListener) {
        this.serversListener = serversListener;
    }

    public void setChaptersListener(ChaptersListener chaptersListener) {
        this.chaptersListener = chaptersListener;
    }

    public void setCategoriesListener(CategoriesListener categoriesListener) {
        this.categoriesListener = categoriesListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener=loginListener;
    }

    public Manga getManga(int id){
        for(Manga manga : mangas) {
            if (manga.getIdManga() == id)
                return manga;
        }
        return null;
    }

    public Chapter getChapter(int id){
        for(Chapter chapter : chapters) {
            if (chapter.getIdChapter() == id)
                return chapter;
        }
        return null;
    }

    public Category getCategory(int id){
        for(Category category : categories) {
            if (category.getIdCategory() == id)
                return category;
        }
        return null;
    }

    public Server getServer(int id){
        for(Server server : servers) {
            if (server.getIdServer() == id)
                return server;
        }
        return null;
    }

    public String getBaseUrl(){
        return BASE_URL;
    }

    public User getUser(){
        return user;
    }

    public ArrayList<Manga> getMangas(){
        return mangas;
    }

    public ArrayList<Chapter> getChapters(){
        return chapters;
    }

    public void getChapterImages(Chapter chapter){
        chaptersListener.onRefreshChapterImages(chapter);
    }

    public ArrayList<Server> getServers(){
        return servers;
    }

    /*********************************** Métodos de acesso à BD ***********************************/

    /** Manga **/

    public void addMangaBD(Manga manga) {
        mangasBD.addMangaBD(manga);
    }

    // Library

    public void getLibraryMangasBD(int List) {
        mangas = new ArrayList<>();
        String list = "NoList";
        switch (List) {
            case 1:
                list = "ToRead";
                break;
            case 2:
                list = "Reading";
                break;
            case 3:
                list = "Completed";
                break;
        }
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if(manga.getList() != null)
                if (manga.getList().equals(list))
                    mangas.add(manga);
        }
        if(mangasListener!=null)
            mangasListener.onRefreshMangasList(mangas);
    }

    public void updateLibraryMangaBD(Manga manga) {
        boolean existe = false;
        for (Manga mangaBD: mangasBD.getAllMangasBD()) {
            if(mangaBD.getIdManga() == manga.getIdManga()){
                existe = true;
                mangasBD.updateMangaBD(manga);
            }
        }
        if (!existe) {
            mangasBD.addMangaBD(manga);
        }
    }

    public int getCountLibraryMangasBD(int List) {
        ArrayList<Manga> library = new ArrayList<>();
        String list = "NoList";
        switch (List) {
            case 1:
                list = "ToRead";
                break;
            case 2:
                list = "Reading";
                break;
            case 3:
                list = "Completed";
                break;
        }
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if(manga.getList() != null)
                if (manga.getList().equals(list))
                    library.add(manga);
        }
        return library.size();
    }

    // Download

    public void getDownloadMangasBD() {
        mangas = new ArrayList<>();
        ArrayList<Integer> idMangas = new ArrayList<>();

        for (Chapter chapter : chaptersBD.getAllChaptersBD()) {
            if (!idMangas.contains(chapter.getMangaId()))
                idMangas.add(chapter.getMangaId());
        }
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (idMangas.contains(manga.getIdManga()))
                mangas.add(manga);
        }
        if(mangasListener!=null)
            mangasListener.onRefreshMangasList(mangas);
    }

    public int getCountDownloadMangasBD() {
        ArrayList<Manga> downloaded = new ArrayList<>();
        ArrayList<Integer> idMangas = new ArrayList<>();

        for (Chapter chapter : chaptersBD.getAllChaptersBD()) {
            if (!idMangas.contains(chapter.getMangaId()))
                idMangas.add(chapter.getMangaId());
        }
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (idMangas.contains(manga.getIdManga()))
                downloaded.add(manga);
        }
        return downloaded.size();
    }

    public int getCountDownloadChaptersBD() {
        return chaptersBD.getAllChaptersBD().size();
    }

    // Favorite

    public void getFavoriteMangasBD() {
        mangas = new ArrayList<>();
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (manga.isFavorite())
                mangas.add(manga);
        }
        if(mangasListener!=null)
            mangasListener.onRefreshMangasList(mangas);
    }

    public void addFavoriteMangaBD(Manga favoriteManga) {
        boolean existe = false;
        for (Manga mangaBD: mangasBD.getAllMangasBD()) {
            if(mangaBD.getIdManga() == favoriteManga.getIdManga()){
                existe = true;
                //mangaBD.setFavorite(true);
                mangasBD.updateMangaBD(favoriteManga);
            }
        }
        if (!existe) {
            mangasBD.addMangaBD(favoriteManga);
        }
    }

    public void removeAllFavoriteMangasBD() {
        for (Manga mangaBD: mangasBD.getAllMangasBD()) {
            mangaBD.setFavorite(false);
            mangasBD.updateMangaBD(mangaBD);
        }
    }

    public int getCountFavoriteMangasBD() {
        ArrayList<Manga> favorites = new ArrayList<>();
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (manga.isFavorite())
                favorites.add(manga);
        }
        return favorites.size();
    }

    /** Chapter **/

    public ArrayList<Chapter> getChaptersBD() {
        chapters = chaptersBD.getAllChaptersBD();
        return chapters;
    }

    public void addChapterBD(Chapter chapter) {
        chaptersBD.addChapterBD(chapter);
    }

    public void addChaptersBD(ArrayList<Chapter> chapters) {
        chaptersBD.deleteAllChaptersBD();
        for (Chapter ch : chapters)
            addChapterBD(ch);
    }

    public void deleteChapterBD(int id) {
        Chapter ch = getChapter(id);

        if (ch != null)
            chaptersBD.deleteChapterBD(id);
    }

    public void updateChapterBD(Chapter chapter) {
        Chapter ch = getChapter(chapter.getIdChapter());

        if (ch != null) {
            if (chaptersBD.updateChapterBD(chapter)) {
                ch.setPagesNumber(chapter.getPagesNumber());
                ch.setSeason(chapter.getSeason());
                ch.setNumber(chapter.getNumber());
                ch.setName(chapter.getName());
                ch.setReleaseDate(chapter.getReleaseDate());
                ch.setSrcFolder(chapter.getSrcFolder());
                ch.setUrlImage(chapter.getUrlImage());
                ch.setOneShot(chapter.isOneShot());
                ch.setReaded(chapter.isReaded());
                ch.setMangaId(chapter.getMangaId());
            }
        }
    }

    /************** Métodos de acesso à API ******************************/

    public void getAllMangasAPI(final Context context) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();

            if(mangasListener!=null)
                mangasListener.onRefreshMangasList(mangasBD.getAllMangasBD());
        } else {
            final String url = BASE_URL+mUrlAPIMangas+"all/latestUpdates/1";
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mangas = MangaJsonParser.parserJsonMangas(response);
                    removeAllFavoriteMangasBD();
                    for (Manga manga: mangas) {
                        if(manga.isFavorite())
                            addFavoriteMangaBD(manga);
                    }

                    if(mangasListener!=null)
                        mangasListener.onRefreshMangasList(mangas);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => All Mangas API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getCategoryMangasAPI(final Context context, int idCategory) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPICategories+idCategory+"/mangas/1";
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mangas = MangaJsonParser.parserJsonMangas(response);

                    if(mangasListener!=null)
                        mangasListener.onRefreshMangasList(mangas);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Category Mangas API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getAllChaptersAPI(final Context context, final int manga_id) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIChapters+manga_id+"/chapters/1";
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    chapters = ChapterJsonParser.parserJsonChapters(response);

                    if(chaptersListener!=null)
                        chaptersListener.onRefreshChaptersList(chapters);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => All Chapters API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getAllCategoriesAPI(final Context context) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPICategories+"all/1";
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    categories = CategoryJsonParser.parserJsonCategories(response);

                    if(categoriesListener!=null)
                        categoriesListener.onRefreshCategoriesList(categories);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => All Categories API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getAllServersAPI(final Context context) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIServer;
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    servers = ServerJsonParser.parserJsonServers(response);

                    if(serversListener!=null)
                        serversListener.onRefreshServersList(servers);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => All Categories API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getUserAPI(final Context context, final int IdUser) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIUser+"get/"+IdUser;
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    user = UserJsonParser.parserJsonUser(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => User API", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void updateUserAPI(final Context context, final User newUser){
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIUser+"update";
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Update User API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("IdUser",newUser.getIdUser()+"");
                    params.put("Theme",newUser.isTheme()+"");
                    params.put("ChapterShow",newUser.isChapterShow()+"");
                    params.put("MangaShow",newUser.isMangaShow()+"");
                    params.put("Server_Id",newUser.getServer_Id()+"");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void updateFavoriteMangaAPI(final Context context, final Manga newManga){
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIMangas+"update";
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Update Favorite Manga API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    System.out.println("IdUser = "+user.getIdUser());
                    System.out.println("IdManga = "+newManga.getIdManga());
                    System.out.println("Favorite = "+newManga.isFavorite());
                    params.put("IdUser",user.getIdUser()+"");
                    params.put("IdManga",newManga.getIdManga()+"");
                    params.put("Favorite",newManga.isFavorite()+"");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void loginAPI(final String username, final String password, final Context context){
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIUser+"login";
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int IdUser = LoginJsonParser.parserJsonIdUser(response);
                    String token = LoginJsonParser.parserJsonToken(response);
                    String Username = LoginJsonParser.parserJsonUsername(response);
                    if(loginListener!=null)
                        loginListener.onValidateLogin(token,Username, IdUser);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Login API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("username",username);
                    params.put("password",password);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }
}
