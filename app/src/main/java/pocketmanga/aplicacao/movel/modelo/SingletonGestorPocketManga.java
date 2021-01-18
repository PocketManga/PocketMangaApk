package pocketmanga.aplicacao.movel.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pocketmanga.aplicacao.movel.activitys.MenuMainActivity;
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
    private static final String mUrlAPIFavorite = "api/favorite/";
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

    public void refreshSelectedChapters(ArrayList<Chapter> selectedChapters) {
        if(chaptersListener != null)
            chaptersListener.onRefreshSelectedChaptersList(selectedChapters);
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

    public void getChapterImages(Chapter chapter){
        chaptersListener.onRefreshChapterImages(chapter);
    }

    public ArrayList<Server> getServers(){
        return servers;
    }

    public void autoLogin(User newUser){
        user = newUser;
        if(loginListener != null)
            loginListener.onAutoLogin(newUser);
    }

    public void setSharedPreferences(Context context, User newUser){
        SharedPreferences sharedPrefUser = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();

        editor.putString(MenuMainActivity.ID_USER,newUser.getIdUser()+"");
        editor.putString(MenuMainActivity.USERNAME,newUser.getUsername());
        editor.putString(MenuMainActivity.URL_PHOTO,newUser.getUrlPhoto());
        editor.putString(MenuMainActivity.MANGA_SHOW,newUser.isMangaShow()+"");
        editor.putString(MenuMainActivity.CHAPTER_SHOW,newUser.isChapterShow()+"");
        editor.putString(MenuMainActivity.THEME,newUser.isTheme()+"");
        editor.putString(MenuMainActivity.EMAIL,newUser.getEmail());
        editor.putString(MenuMainActivity.SERVER_ID,newUser.getServer_Id()+"");

        editor.apply();
        user = newUser;
    }

    /*********************************** Métodos de acesso à BD ***********************************/

    /** Manga **/
    public void getAllMangasBD() {
        mangas = new ArrayList<>();
        mangas = mangasBD.getAllMangasBD();
        if(mangasListener!=null)
            mangasListener.onRefreshMangasList(mangas);
    }

    public Manga getMangaBD(int id){
        for(Manga manga : mangasBD.getAllMangasBD()) {
            if (manga.getIdManga() == id)
                return manga;
        }
        return null;
    }

    public void removeExtrasMangasBD() {
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if(!manga.isFavorite() && !manga.isDownload() && manga.getList() == null){
                mangasBD.deleteMangaBD(manga.getIdManga());
            }
        }
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
        Manga mangaBD = getMangaBD(manga.getIdManga());
        if(mangaBD != null) {
            mangaBD.setList(manga.getList());
            mangasBD.updateMangaBD(mangaBD);
        }
        else {
            mangasBD.addMangaBD(manga);
        }
    }

    public void updateLibraryMangasAPI() {
        for (Manga mangaBD: mangasBD.getAllMangasBD()) {
            for (Manga manga: mangas) {
                if (mangaBD.getIdManga() == manga.getIdManga()) {
                    manga.setList(mangaBD.getList());
                }
            }
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

    public void addDownloadMangaBD(Manga manga, ArrayList<Chapter> DChapters) {
        Manga mangaBD  = getMangaBD(manga.getIdManga());

        if(mangaBD!=null) {
            mangaBD.setDownload(true);
            mangasBD.updateMangaBD(mangaBD);
        }
        else {
            mangasBD.addMangaBD(manga);
        }

        for (Chapter chapter: DChapters) {
            addDownloadChapterBD(chapter);
        }
    }

    public void removeDownloadMangaBD(Manga manga) {
        Manga mangaBD = getMangaBD(manga.getIdManga());
        if (mangaBD != null){
            chaptersBD.deleteChapterBDFromManga(manga.getIdManga());
            mangaBD.setDownload(false);
            mangasBD.updateMangaBD(mangaBD);
        }
        removeExtrasMangasBD();
    }

    public void getDownloadMangasBD() {
        mangas = new ArrayList<>();
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (manga.isDownload()) {
                mangas.add(manga);
            }
        }
        if(mangasListener!=null)
            mangasListener.onRefreshMangasList(mangas);
    }

    public int getCountDownloadMangasBD() {
        ArrayList<Manga> downloaded = new ArrayList<>();
        for (Manga manga : mangasBD.getAllMangasBD()) {
            if (manga.isDownload())
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

    public void addFavoriteMangaBD(Manga manga) {
        Manga mangaBD = getMangaBD(manga.getIdManga());

        if(mangaBD!=null) {
            mangaBD.setFavorite(true);
            mangasBD.updateMangaBD(mangaBD);
        }
        else {
            mangasBD.addMangaBD(manga);
        }
    }

    public void updateFavoriteMangaBD(Manga manga) {
        Manga mangaBD = getMangaBD(manga.getIdManga());

        if(mangaBD!=null) {
            mangaBD.setFavorite(manga.isFavorite());
            mangasBD.updateMangaBD(mangaBD);
        }
        else {
            mangasBD.addMangaBD(manga);
        }
    }

    public void updateAllFavoriteMangasBD() {
        for (Manga mangaBD: mangasBD.getAllMangasBD()) {
            mangaBD.setFavorite(false);
            mangasBD.updateMangaBD(mangaBD);
        }
        for (Manga manga: mangas) {
            if(manga.isFavorite())
                addFavoriteMangaBD(manga);
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

    public Chapter getChapterBD(int id){
        for(Chapter chapter : chaptersBD.getAllChaptersBD()) {
            if (chapter.getIdChapter() == id)
                return chapter;
        }
        return null;
    }

    public void getChaptersBD(int idManga) {
        chapters = new ArrayList<>();
        for (Chapter chapter: chaptersBD.getAllChaptersBD()) {
            if(chapter.getMangaId() == idManga)
                chapters.add(chapter);
        }
        if(chaptersListener != null)
            chaptersListener.onRefreshChaptersList(chapters);
    }

    public void addDownloadChapterBD(Chapter chapter) {
        chapter.setDownload(true);
        Chapter chapterBD  = getChapterBD(chapter.getIdChapter());
        if(chapterBD!=null)
            chaptersBD.updateChapterBD(chapter);
        else
            chaptersBD.addChapterBD(chapter);
    }

    public void removeDownloadChapterBD(Chapter chapter) {
        chaptersBD.deleteChapterBD(chapter.getIdChapter());
    }

    /************** Métodos de acesso à API ******************************/

    public void getAllMangasAPI(final Context context) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();

            if(mangasListener!=null)
                mangasListener.onRefreshMangasList(mangasBD.getAllMangasBD());
        } else {
            final String url = BASE_URL+mUrlAPIMangas+"all/latestUpdates/"+user.getIdUser();
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mangas = MangaJsonParser.parserJsonMangas(response);

                    updateAllFavoriteMangasBD();
                    updateLibraryMangasAPI();

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
            final String url = BASE_URL+mUrlAPICategories+idCategory+"/mangas/"+user.getIdUser();
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
            final String url = BASE_URL+mUrlAPIChapters+manga_id+"/chapters/"+user.getIdUser();
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
            final String url = BASE_URL+mUrlAPICategories+"all/"+user.getIdUser();
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
                    setSharedPreferences(context, user);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(url);
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
            /** Params not working on the normal way, so used this way to replace **/
            String params = "?IdUser="+newUser.getIdUser()+"&Theme="+newUser.isTheme()+"&ChapterShow="+newUser.isChapterShow()+"&MangaShow="+newUser.isMangaShow()+"&Server_Id="+newUser.getServer_Id();
            final String url = BASE_URL+mUrlAPIUser+"update"+params;
            StringRequest req = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Update User API", Toast.LENGTH_SHORT).show();
                }
            })/* {
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
            }/**/;
            volleyQueue.add(req);
        }
    }

    public void createFavoriteMangaAPI(final Context context, final Manga newManga){
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIFavorite+"create";
            System.out.println(url);
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Create Favorite Manga API", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("IdUser",user.getIdUser()+"");
                    params.put("IdManga",newManga.getIdManga()+"");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void deleteFavoriteMangaAPI(final Context context, final Manga oldManga){
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "No connection to the internet", Toast.LENGTH_SHORT).show();
        } else {
            final String url = BASE_URL+mUrlAPIFavorite+"delete/user/"+user.getIdUser()+"/manga/"+oldManga.getIdManga();
            System.out.println(url);
            StringRequest req = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, (error.getMessage()!= null)?error.getMessage():"Error!! => Delete Favorite Manga API", Toast.LENGTH_SHORT).show();
                }
            });
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
