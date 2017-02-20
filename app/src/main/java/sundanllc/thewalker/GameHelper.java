package sundanllc.thewalker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static sundanllc.thewalker.GameDatabase.SQL_CREATE_ENTRIES;
import static sundanllc.thewalker.GameDatabase.SQL_DELETE_ENTRIES;

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
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.setVersion(newVersion);
    }
}
