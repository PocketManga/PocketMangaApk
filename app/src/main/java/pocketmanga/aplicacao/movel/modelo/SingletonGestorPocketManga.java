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
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.listeners.LoginListener;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.utils.ChapterJsonParser;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;
import pocketmanga.aplicacao.movel.utils.LoginJsonParser;
import pocketmanga.aplicacao.movel.utils.MangaJsonParser;

public class SingletonGestorPocketManga {
    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD =2 ;
    private static final int REMOVER_BD =3 ;
    private static final String MANGAS_KEY = "mangas";

    private static SingletonGestorPocketManga instance = null;

    private ArrayList<Manga> mangas;
    private ArrayList<Chapter> chapters;
    //private ArrayList<Category> categories;
    //private ArrayList<Author> authors;

    private MangaBDHelper mangasBD;
    private ChapterBDHelper chaptersBD;

    private static RequestQueue volleyQueue = null;

    private static final String BASE_URL = "http://192.168.137.1/PocketManga/backend/web/";
    private static final String mUrlAPIMangas = "api/manga/all/";
    private static final String mUrlAPIChapters = "api/manga/";
    private static final String mUrlAPILogin="api/user/login";
    //private static final String mUrlAPICategories="category/all";

    private MangasListener mangasListener;
    private ChaptersListener chaptersListener;
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
    }

    public void setMangasListener(MangasListener mangasListener) {
        this.mangasListener = mangasListener;
    }

    public void setChaptersListener(ChaptersListener chaptersListener) {
        this.chaptersListener = chaptersListener;
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

    public void getChapterImages(Chapter chapter){
        chaptersListener.onRefreshChapterImages(chapter);
    }

    /*********************************** Métodos de acesso à BD ***********************************/

    /** Manga **/

    public ArrayList<Manga> getMangasBD() {
        mangas = mangasBD.getAllMangasBD();
        return mangas;
    }

    public void addMangaBD(Manga manga) {
        mangasBD.addMangaBD(manga);
    }

    public void addMangasBD(ArrayList<Manga> mangas) {
        mangasBD.deleteAllMangasBD();
        for (Manga mg : mangas)
            addMangaBD(mg);
    }

    public void deleteMangaBD(int id) {
        Manga mg = getManga(id);

        if (mg != null)
            mangasBD.deleteMangaBD(id);
    }

    public void updateMangaBD(Manga manga) {
        Manga mg = getManga(manga.getIdManga());

        if (mg != null) {
            if (mangasBD.updateMangaBD(manga)) {
                mg.setImage(manga.getImage());
                mg.setTitle(manga.getTitle());
                mg.setAlternativeTitle(manga.getAlternativeTitle());
                mg.setOriginalTitle(manga.getOriginalTitle());
                mg.setReleaseDate(manga.getReleaseDate());
                mg.setServer(manga.getServer());
                mg.setDescription(manga.getDescription());
                mg.setStatus(manga.isStatus());
                mg.setOneshot(manga.isOneshot());
                mg.setR18(manga.isR18());
                mg.setFavorite(manga.isFavorite());
                mg.setList(manga.getList());
            }
        }
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

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

            if(mangasListener!=null)
                mangasListener.onRefreshMangasList(mangasBD.getAllMangasBD());
        } else {
            String url = BASE_URL+mUrlAPIMangas+"latestUpdates/1";
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    mangas = MangaJsonParser.parserJsonMangas(response);
                    //addMangasBD(mangas);

                    if(mangasListener!=null)
                        mangasListener.onRefreshMangasList(mangas);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getAllChaptersAPI(final Context context, final int manga_id) {
        if (!ConnectionJsonParser.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

            if(chaptersListener!=null)
                chaptersListener.onRefreshChaptersList(chaptersBD.getAllChaptersBD());
        } else {
            String url = BASE_URL+mUrlAPIChapters+manga_id+"/chapters/1";
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
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void loginAPI(final String username, final String password, final Context context){
        String url = BASE_URL+mUrlAPILogin;
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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener=loginListener;
    }
}
