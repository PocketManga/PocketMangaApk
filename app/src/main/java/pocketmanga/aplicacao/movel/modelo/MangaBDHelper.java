package pocketmanga.aplicacao.movel.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MangaBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="pmanga";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="mangas";

    private static final String ID_MANGA = "IdManga";
    private static final String IMAGE_MANGA = "Image";
    private static final String TITLE_MANGA = "Title";
    private static final String ALTERNATIVE_TITLE_MANGA = "AlternativeTitle";
    private static final String ORIGINAL_TITLE_MANGA = "OriginalTitle";
    private static final String RELEASE_DATE_MANGA = "ReleaseDate";
    private static final String SERVER_MANGA = "Server";
    private static final String LIST_MANGA = "List";
    private static final String DESCRIPTION_MANGA = "Description";
    private static final String STATUS_MANGA = "Status";
    private static final String ONESHOT_MANGA = "Oneshot";
    private static final String R18_MANGA = "R18";
    private static final String FAVORITE_MANGA = "Favorite";

    private final SQLiteDatabase db;

    public MangaBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //código sql de criação de tabela
        String createTableManga="CREATE TABLE "+TABLE_NAME+"( "+
                ID_MANGA+" INTEGER PRIMARY KEY, "+
                IMAGE_MANGA+" TEXT NOT NULL, "+
                TITLE_MANGA+" TEXT NOT NULL, "+
                ALTERNATIVE_TITLE_MANGA+" TEXT NOT NULL, "+
                ORIGINAL_TITLE_MANGA+" TEXT NOT NULL, "+
                RELEASE_DATE_MANGA+" TEXT NOT NULL, "+
                SERVER_MANGA+" TEXT NOT NULL, "+
                LIST_MANGA+" TEXT, "+
                DESCRIPTION_MANGA+" TEXT NOT NULL, "+
                STATUS_MANGA+" BOOL NOT NULL, "+
                ONESHOT_MANGA+" BOOL NOT NULL, "+
                R18_MANGA+" BOOL NOT NULL, "+
                FAVORITE_MANGA+" BOOL NOT NULL);";
        db.execSQL(createTableManga);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableManga="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(deleteTableManga);
        this.onCreate(db);
    }

    /*********************************************CRUD*********************************************/

    /**
     * INSERT
     * @param manga
     * @return
     */
    public void addMangaBD(Manga manga){
        ContentValues values=new ContentValues();

        values.put(ID_MANGA,manga.getIdManga());
        values.put(IMAGE_MANGA,manga.getImage());
        values.put(TITLE_MANGA,manga.getTitle());
        values.put(ALTERNATIVE_TITLE_MANGA,manga.getAlternativeTitle());
        values.put(ORIGINAL_TITLE_MANGA,manga.getOriginalTitle());
        values.put(RELEASE_DATE_MANGA,manga.getReleaseDate());
        values.put(SERVER_MANGA,manga.getServer());
        values.put(LIST_MANGA,manga.getDescription());
        values.put(DESCRIPTION_MANGA,manga.isStatus());
        values.put(STATUS_MANGA,manga.isOneshot());
        values.put(ONESHOT_MANGA,manga.isR18());
        values.put(R18_MANGA,manga.isFavorite());
        values.put(FAVORITE_MANGA,manga.getList());

        this.db.insert(TABLE_NAME,null,values);
    }

    /**
     * UPDATE
     * @param manga
     * @return
     */
    public boolean updateMangaBD(Manga manga) {
        ContentValues values=new ContentValues();

        values.put(IMAGE_MANGA,manga.getImage());
        values.put(TITLE_MANGA,manga.getTitle());
        values.put(ALTERNATIVE_TITLE_MANGA,manga.getAlternativeTitle());
        values.put(ORIGINAL_TITLE_MANGA,manga.getOriginalTitle());
        values.put(RELEASE_DATE_MANGA,manga.getReleaseDate());
        values.put(SERVER_MANGA,manga.getServer());
        values.put(LIST_MANGA,manga.getDescription());
        values.put(DESCRIPTION_MANGA,manga.isStatus());
        values.put(STATUS_MANGA,manga.isOneshot());
        values.put(ONESHOT_MANGA,manga.isR18());
        values.put(R18_MANGA,manga.isFavorite());
        values.put(FAVORITE_MANGA,manga.getList());

        int nRows=this.db.update(TABLE_NAME,values, "idManga = ?", new String[]{manga.getIdManga()+""});

        return (nRows>0);
    }

    public void deleteAllMangasBD(){
        this.db.delete(TABLE_NAME,null,null);
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    public boolean deleteMangaBD(int id){
        int nRows=this.db.delete(TABLE_NAME,"idManga = ?", new String[]{id+""});
        return (nRows>0);
    }

    /**
     * SELECT
     * pode-se usar rawQuery mas é mais susceptivel de SQL injection
     * this.db.rawQuery("SELECT * FROM ... WHERE ...".null)
     * com o método .query podemos colocar null (para todas as colunas)
     * @return
     */
    public ArrayList<Manga> getAllMangasBD(){
        ArrayList<Manga> mangas=new ArrayList<>();
        Cursor cursor=this.db.query(TABLE_NAME,new String[]{ID_MANGA,IMAGE_MANGA,TITLE_MANGA,ALTERNATIVE_TITLE_MANGA,ORIGINAL_TITLE_MANGA,RELEASE_DATE_MANGA,SERVER_MANGA,
                        LIST_MANGA,DESCRIPTION_MANGA,STATUS_MANGA,ONESHOT_MANGA,R18_MANGA,FAVORITE_MANGA},
                null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                Manga auxManga=new Manga(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.isNull(9),
                        cursor.isNull(10),
                        cursor.isNull(11),
                        cursor.isNull(12));
                mangas.add(auxManga);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return mangas;
    }
}
