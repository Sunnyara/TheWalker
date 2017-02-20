package sundanllc.thewalker;

import android.provider.BaseColumns;

/**
 * Created by Sunnara on 2/20/2017.
 */

public final class GameDatabase {
    private GameDatabase() {}

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "object";
        public static final String ID = "id";
        public static final String GAME_TITLE = "title";
        public static final String GAME_AUTHOR = "author";
        public static final String DESCRIPTION = "description";
        public static final String THUMBNAIL = "thumbnail";
        public static final String ETA = "eta";
        public static final String TIME_PLAYED = "time_played";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE" + GameEntry.TABLE_NAME + "(" +
                    GameEntry.ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameEntry.GAME_TITLE + "TEXT,"  +
                    GameEntry.GAME_AUTHOR + "TEXT," +
                    GameEntry.GAME_TITLE + "TEXT," +
                    GameEntry.DESCRIPTION + "TEXT," +
                    GameEntry.THUMBNAIL + "TEXT," +
                    GameEntry.ETA + "TIME" +
                    GameEntry.TIME_PLAYED + "INT )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME;


}