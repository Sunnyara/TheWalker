package sundanllc.thewalker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import static sundanllc.thewalker.GameDatabase.GameEntry.CP_HINT1;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_HINT2;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_HINT3;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_HINT4;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_LAT;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_LONG;
import static sundanllc.thewalker.GameDatabase.GameEntry.CP_TYPE;
import static sundanllc.thewalker.GameDatabase.GameEntry.DESCRIPTION;
import static sundanllc.thewalker.GameDatabase.GameEntry.ETA;
import static sundanllc.thewalker.GameDatabase.GameEntry.GAME_AUTHOR;
import static sundanllc.thewalker.GameDatabase.GameEntry.GAME_TITLE;
import static sundanllc.thewalker.GameDatabase.GameEntry.TABLE2_NAME;
import static sundanllc.thewalker.GameDatabase.GameEntry.TABLE_NAME;
import static sundanllc.thewalker.GameDatabase.GameEntry.THUMBNAIL;
import static sundanllc.thewalker.GameDatabase.GameEntry.TIME_PLAYED;
import static sundanllc.thewalker.GameDatabase.GameEntry.WALKERID;
import static sundanllc.thewalker.GameDatabase.SQL_CREATE_CHECKPOINT;
import static sundanllc.thewalker.GameDatabase.SQL_CREATE_GAMES;

/**
 * Created by Daniel on 2/20/2017.
 */

public class GameHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GameDatabase";

    public GameHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_GAMES);
        db.execSQL(SQL_CREATE_CHECKPOINT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.setVersion(newVersion);
    }

    public boolean insertGame(String game_title, String game_author, String description,
                              Bitmap thumbnail, int eta, int time_played)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GAME_TITLE, game_title);
        cv.put(GAME_AUTHOR, game_author);
        cv.put(DESCRIPTION, description);
        byte[] image = BlobFactory.getBytes(thumbnail);
        cv.put(THUMBNAIL, image);
        cv.put(ETA, eta);
        cv.put(TIME_PLAYED, time_played);
        long ret = db.insert(TABLE_NAME, null, cv);
        return ret != -1;
    }

    public boolean insertCheckpoint(String walkerid, float lat, float lon,
                                    String hint1, String hint2, String hint3,
                                    String hint4, int type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WALKERID, walkerid);
        if(type > 2 || type < 0) type = 0;
        cv.put(CP_TYPE, type);
        cv.put(CP_LAT, lat);
        cv.put(CP_LONG, lon);
        cv.put(CP_HINT1, hint1);
        cv.put(CP_HINT2, hint2);
        cv.put(CP_HINT3, hint3);
        cv.put(CP_HINT4, hint4);
        long ret = db.insert(TABLE2_NAME, null, cv);
        return ret != -1;
    }
}
