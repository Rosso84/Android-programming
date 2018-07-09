package no.woact.morroo16.Dbhandler;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*By Roosbeh Morandi*/

public class SQLhelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "tictac.db";
    public static final String TABLE_HIGHSCORELIST = "HighScoreList";


    public static final String ID = "id";
    public static final String WINNER_NAME = "name";
    public static final String WIN_TIME = "time";
    public static final String COUNT_WIN = "countWin";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_HIGHSCORELIST +
            " ("
            + ID + " integer primary key autoincrement, "
            + WINNER_NAME + " text not null,"
            + WIN_TIME + " text not null,"
            + COUNT_WIN + " integer not null" +
            " );";

    public SQLhelper(Context context) throws SQLException {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase mSQLiteDatabase, int i, int i1) {
        mSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORELIST);
        onCreate(mSQLiteDatabase);
    }

}
