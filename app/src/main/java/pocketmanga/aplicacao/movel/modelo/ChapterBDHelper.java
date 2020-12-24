package pocketmanga.aplicacao.movel.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ChapterBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="pmanga";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="chapters";

    private static final String ID_CHAPTER = "IdChapter";
    private static final String PAGES_NUMBER_CHAPTER = "PagesNumber";
    private static final String SEASON_CHAPTER = "Season";
    private static final String NUMBER_CHAPTER = "Number";
    private static final String NAME_CHAPTER = "Name";
    private static final String RELEASE_DATE_CHAPTER = "ReleaseDate";
    private static final String SRC_FOLDER_CHAPTER = "SrcFolder";
    private static final String URL_IMAGE_CHAPTER = "UrlImage";
    private static final String ONESHOT_CHAPTER = "OneShot";
    private static final String READED_CHAPTER = "Readed";
    private static final String MANGA_ID = "MangaId";

    private final SQLiteDatabase db;

    public ChapterBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //código sql de criação de tabela
        String createTableChapter="CREATE TABLE "+TABLE_NAME+"( "+
                ID_CHAPTER+" INTEGER PRIMARY KEY, "+
                PAGES_NUMBER_CHAPTER+" INTEGER NOT NULL, "+
                SEASON_CHAPTER+" INTEGER NOT NULL, "+
                NUMBER_CHAPTER+" FLOAT(8,4) NOT NULL, "+
                NAME_CHAPTER+" TEXT, "+
                RELEASE_DATE_CHAPTER+" TEXT NOT NULL, "+
                SRC_FOLDER_CHAPTER+" TEXT NOT NULL, "+
                URL_IMAGE_CHAPTER+" TEXT, "+
                ONESHOT_CHAPTER+" BOOL NOT NULL, "+
                READED_CHAPTER+" BOOL NOT NULL, "+
                MANGA_ID+" INTEGER NOT NULL);";
        db.execSQL(createTableChapter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableChapter="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(deleteTableChapter);
        this.onCreate(db);
    }

    /*********************************************CRUD*********************************************/

    /**
     * INSERT
     * @param chapter
     * @return
     */
    public void addChapterBD(Chapter chapter){
        ContentValues values=new ContentValues();

        values.put(ID_CHAPTER,chapter.getIdChapter());
        values.put(PAGES_NUMBER_CHAPTER,chapter.getPagesNumber());
        values.put(SEASON_CHAPTER,chapter.getSeason());
        values.put(NUMBER_CHAPTER,chapter.getNumber());
        values.put(NAME_CHAPTER,chapter.getName());
        values.put(RELEASE_DATE_CHAPTER,chapter.getReleaseDate());
        values.put(SRC_FOLDER_CHAPTER,chapter.getSrcFolder());
        values.put(URL_IMAGE_CHAPTER,chapter.getUrlImage());
        values.put(ONESHOT_CHAPTER,chapter.isOneShot());
        values.put(READED_CHAPTER,chapter.isReaded());
        values.put(MANGA_ID,chapter.getMangaId());

        this.db.insert(TABLE_NAME,null,values);
    }

    /**
     * UPDATE
     * @param chapter
     * @return
     */
    public boolean updateChapterBD(Chapter chapter) {
        ContentValues values=new ContentValues();

        values.put(PAGES_NUMBER_CHAPTER,chapter.getPagesNumber());
        values.put(SEASON_CHAPTER,chapter.getSeason());
        values.put(NUMBER_CHAPTER,chapter.getNumber());
        values.put(NAME_CHAPTER,chapter.getName());
        values.put(RELEASE_DATE_CHAPTER,chapter.getReleaseDate());
        values.put(SRC_FOLDER_CHAPTER,chapter.getSrcFolder());
        values.put(URL_IMAGE_CHAPTER,chapter.getUrlImage());
        values.put(ONESHOT_CHAPTER,chapter.isOneShot());
        values.put(READED_CHAPTER,chapter.isReaded());
        values.put(MANGA_ID,chapter.getMangaId());

        int nRows=this.db.update(TABLE_NAME,values, "idChapter = ?", new String[]{chapter.getIdChapter()+""});

        return (nRows>0);
    }

    public void deleteAllChaptersBD(){
        this.db.delete(TABLE_NAME,null,null);
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    public boolean deleteChapterBD(int id){
        int nRows=this.db.delete(TABLE_NAME,"idChapter = ?", new String[]{id+""});
        return (nRows>0);
    }

    /**
     * SELECT
     * pode-se usar rawQuery mas é mais susceptivel de SQL injection
     * this.db.rawQuery("SELECT * FROM ... WHERE ...".null)
     * com o método .query podemos colocar null (para todas as colunas)
     * @return
     */
    public ArrayList<Chapter> getAllChaptersBD(){
        ArrayList<Chapter> chapters=new ArrayList<>();
        Cursor cursor=this.db.query(TABLE_NAME,new String[]{ID_CHAPTER,PAGES_NUMBER_CHAPTER,SEASON_CHAPTER,NUMBER_CHAPTER,NAME_CHAPTER,RELEASE_DATE_CHAPTER,SRC_FOLDER_CHAPTER,URL_IMAGE_CHAPTER,ONESHOT_CHAPTER,READED_CHAPTER,MANGA_ID},
                null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                Chapter auxChapter=new Chapter(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(10),
                        cursor.getDouble(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.isNull(8),
                        cursor.isNull(9));
                chapters.add(auxChapter);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return chapters;
    }
}
