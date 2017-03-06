package sundanllc.thewalker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import static sundanllc.thewalker.GameDatabase.GameEntry.*;
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

    public boolean insertCheckpoint(int walkerid, float lat, float lon, String address,
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
        cv.put(CP_ADD, address);
        cv.put(CP_HINT1, hint1);
        cv.put(CP_HINT2, hint2);
        cv.put(CP_HINT3, hint3);
        cv.put(CP_HINT4, hint4);
        long ret = db.insert(TABLE2_NAME, null, cv);
        return ret != -1;
    }

    public ArrayList<WalkerGame> getGames()
    {
        ArrayList<WalkerGame> games = new ArrayList<WalkerGame>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, title, author, description, thumbnail, eta, time_played FROM game_object";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                WalkerGame wg = new WalkerGame();
                wg.setId(cursor.getInt(0));
                wg.setTitle(cursor.getString(1));
                wg.setAuthor(cursor.getString(2));
                wg.setDescription(cursor.getString(3));
                byte[] thumbnail = cursor.getBlob(4);
                wg.setPicture(BlobFactory.getImage(thumbnail));
                wg.setEta(cursor.getInt(5));
                wg.setTime_played(cursor.getInt(6));
                games.add(wg);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return games;
    }

    public ArrayList<Checkpoint> getCheckpoints(int id)
    {
        ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT cp_latitude, cp_longitude, cp_address, cp_hint1, cp_hint2, cp_hint3, cp_hint4, cp_type FROM cp_object WHERE walker_id = " + id + " ORDER BY cp_type ASC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Checkpoint cp = new Checkpoint();
                cp.setX(cursor.getFloat(0));
                cp.setY(cursor.getFloat(1));
                cp.setAddress(cursor.getString(2));
                cp.setHint1(cursor.getString(3));
                cp.setHint2(cursor.getString(4));
                cp.setHint3(cursor.getString(5));
                cp.setHint4(cursor.getString(6));
                checkpoints.add(cp);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return checkpoints;
    }

    public boolean deleteAll()
    {
        return false;
    }
}
