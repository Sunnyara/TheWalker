package sundanllc.thewalker;

import android.provider.BaseColumns;

/**
 * Created by Sunnara on 2/20/2017.
 */

public final class GameDatabase {
    private GameDatabase() {}

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "game_object";
        public static final String GAME_ID = "id";
        public static final String GAME_TITLE = "title";
        public static final String GAME_AUTHOR = "author";
        public static final String DESCRIPTION = "description";
        public static final String THUMBNAIL = "thumbnail";
        public static final String ETA = "eta";
        public static final String TIME_PLAYED = "time_played";

        public static final String TABLE2_NAME = "checkpoint_object";
        public static final String CP_ID = "checkpoint_id";
        public static final String WALKERID = "walker_id";
        public static final String CP_LAT = "checkpoint_latitude";
        public static final String CP_LONG = "checkpoint_longitude";
        public static final String CP_HINT1 = "checkpoint_hint_1";
        public static final String CP_HINT2 = "checkpoint_hint_2";
        public static final String CP_HINT3 = "checkpoint_hint_3";
        public static final String CP_HINT4 = "checkpoint_hint_4";
        public static final String CP_TYPE = "checkpoint_type";



    }

    public static final String SQL_CREATE_GAMES =
            "CREATE TABLE" + GameEntry.TABLE_NAME + "(" +
                    GameEntry.GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameEntry.GAME_TITLE + " TEXT,"  +
                    GameEntry.GAME_AUTHOR + " TEXT," +
                    GameEntry.GAME_TITLE + " TEXT," +
                    GameEntry.DESCRIPTION + " TEXT," +
                    GameEntry.THUMBNAIL + " BLOB," +
                    GameEntry.ETA + " INT" +
                    GameEntry.TIME_PLAYED + " INT)";


    public static final String SQL_CREATE_CHECKPOINT =
            "CREATE TABLE" + GameEntry.TABLE2_NAME + "(" +
                    GameEntry.CP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameEntry.CP_TYPE + " INT, " +
                    GameEntry.WALKERID + " INT," +
                    GameEntry.CP_LAT + " FLOAT," +
                    GameEntry.CP_LONG + " FLOAT," +
                    GameEntry.CP_HINT1 + " TEXT," +
                    GameEntry.CP_HINT2 + " TEXT," +
                    GameEntry.CP_HINT3 + " TEXT," +
                    GameEntry.CP_HINT4 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME;


}