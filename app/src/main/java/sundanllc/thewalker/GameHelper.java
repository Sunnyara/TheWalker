package sundanllc.thewalker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;
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

    public long insertGame(WalkerGame game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        byte[] image = BlobFactory.getBytes(game.getPicture());
        cv.put(GAME_TITLE, game.getTitle());
        cv.put(GAME_AUTHOR, game.getAuthor());
        cv.put(DESCRIPTION, game.getDescription());
        cv.put(THUMBNAIL, image);
        cv.put(ETA, game.getEta());
        cv.put(TIME_PLAYED, game.getTime_played());
        int creator;
        if (game.isCreator()) creator = 1;
        else creator = 0;
        cv.put(CREATOR, creator);
        long ret = db.insert(TABLE_NAME, null, cv);
        return ret;
    }

    public boolean deleteGame(int gameID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] id = {Integer.toString(gameID)};
        db.delete("cp_object", "walker_id = ?", id);
        return db.delete("game_object", "id = ?", id) == 0;
    }

    public boolean insertCheckpoint(Checkpoint cp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WALKERID, cp.getId());
        if(cp.getType() > 2 || cp.getType() < 0) cp.setType(1);
        cv.put(CP_TYPE, cp.getType());
        cv.put(CP_LAT, cp.getX());
        cv.put(CP_LONG, cp.getY());
        cv.put(CP_ADD, cp.getAddress());
        cv.put(CP_HINT1, cp.getHint1());
        cv.put(CP_HINT2, cp.getHint2());
        cv.put(CP_HINT3, cp.getHint3());
        cv.put(CP_HINT4, cp.getHint4());
        long ret = db.insertOrThrow(TABLE2_NAME, null, cv);
        return ret != -1;
    }

    public WalkerGame getGame(int id)
    {
        WalkerGame wg = new WalkerGame();
        SQLiteDatabase db = this.getWritableDatabase();
        String  query = "SELECT id, title, author, description, thumbnail, eta, time_played FROM game_object WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            wg.setId(cursor.getInt(0));
            wg.setTitle(cursor.getString(1));
            wg.setAuthor(cursor.getString(2));
            wg.setDescription(cursor.getString(3));
            byte[] thumbnail = cursor.getBlob(4);
            wg.setPicture(BlobFactory.getImage(thumbnail));
            wg.setEta(cursor.getInt(5));
            wg.setTime_played(cursor.getInt(6));
        }
        return wg;
    }

    public ArrayList<WalkerGame> getGamesAsCreator()
    {
        ArrayList<WalkerGame> games = new ArrayList<WalkerGame>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, title, author, description, thumbnail, eta, time_played FROM game_object WHERE is_creator = 1";
        Cursor cursor;
        try
        {
            cursor = db.rawQuery(query, null);
        }
        catch (Exception e)
        {
            return games;
        }
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

    public ArrayList<WalkerGame> getGames()
    {
        ArrayList<WalkerGame> games = new ArrayList<WalkerGame>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, title, author, description, thumbnail, eta, time_played FROM game_object";
        Cursor cursor;
        try
        {
            cursor = db.rawQuery(query, null);
        }
        catch (Exception e)
        {
            return games;
        }
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

    public ArrayList<WalkerGame> searchGames(boolean title, boolean author, boolean desc, String search)
    {
        ArrayList<WalkerGame> games = new ArrayList<WalkerGame>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, title, author, description, thumbnail, eta, time_played FROM game_object";
        if (title || author || desc) query += " WHERE";
        if (title)
        {
            query += " title LIKE " + "'%" + search + "%'";
        }
        if (author)
        {
            if (title) query += " OR";
            query += " author LIKE " + "'%" + search + "%'";
        }
        if (desc)
        {
            if (title || author) query += " OR";
            query += " description LIKE " + "'%" + search + "%'";
        }
        Cursor cursor = db.rawQuery(query, null);
        /*try
        {
            cursor = db.rawQuery(query, null);
        }
        catch (Exception e)
        {
            return games;
        }*/
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
        String query = "SELECT cp_latitude, cp_longitude, cp_hint_1, cp_hint_2, cp_hint_3, cp_hint_4, cp_type, cp_address FROM cp_object WHERE walker_id = " + id + " ORDER BY cp_type ASC";
        Cursor cursor;
        try
        {
            cursor = db.rawQuery(query, null);
        }
        catch (Exception a)
        {
            return checkpoints;
        }
        if (cursor.moveToFirst())
        {
            do
            {
                Checkpoint cp = new Checkpoint();
                cp.setType(cursor.getInt(6));
                //cp.setId(cursor.getInt(2));
                cp.setX(cursor.getFloat(0));
                cp.setY(cursor.getFloat(1));
                cp.setHint1(cursor.getString(2));
                cp.setHint2(cursor.getString(3));
                cp.setHint3(cursor.getString(4));
                cp.setAddress(cursor.getString(7));
                cp.setHint4(cursor.getString(5));

                checkpoints.add(cp);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return checkpoints;
    }

    public boolean deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        File path = new File(db.getPath());
        return SQLiteDatabase.deleteDatabase(path);
    }
}
