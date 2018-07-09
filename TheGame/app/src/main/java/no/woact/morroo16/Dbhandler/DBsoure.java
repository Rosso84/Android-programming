package no.woact.morroo16.Dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/*by Roosbeh Morandi*/

public class DBsoure {

    private SQLiteDatabase database;
    private SQLhelper dbHelper;
    private String[] columns = {dbHelper.ID, dbHelper.WINNER_NAME, dbHelper.WIN_TIME, dbHelper.COUNT_WIN};


    public DBsoure(Context context) {
        dbHelper = new SQLhelper(context);
    }

    public void open() throws SQLException { //lånt fra forelesnings eksempelkoder
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public WinnerData addNewWinner(String winner, String time, int countWin) {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(dbHelper.WINNER_NAME, winner);
        contentvalues.put(dbHelper.WIN_TIME, time);
        contentvalues.put(dbHelper.COUNT_WIN, countWin);
        long insertId = database.insert(SQLhelper.TABLE_HIGHSCORELIST, null, contentvalues);

        Cursor cursor = database.query(SQLhelper.TABLE_HIGHSCORELIST, columns,
                SQLhelper.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        WinnerData wd = new WinnerData();
        wd.setId(cursor.getLong(0));
        wd.setName(cursor.getString(1));
        wd.setTime(cursor.getString(2));
        cursor.close();
        return wd;
    }

    public List<WinnerData> getAllWinnerData() {
        List<WinnerData> winnerList = new ArrayList<>();
        Cursor cursor = database.query(SQLhelper.TABLE_HIGHSCORELIST, columns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WinnerData wd = new WinnerData();
            wd.setId(cursor.getLong(0));
            wd.setName(cursor.getString(1));
            wd.setTime(cursor.getString(2));
            wd.setCountWin(cursor.getInt(3));
            winnerList.add(wd);
            cursor.moveToNext();
        }
        cursor.close();
        return winnerList;
    }

    public boolean nameExists(String name) {
        Cursor cursor = null;
        String empName = "";
        try {
            cursor = database.rawQuery("SELECT name " +
                    "FROM "+ SQLhelper.TABLE_HIGHSCORELIST +
                    "  WHERE name = ?", new String[] {name + ""});
            if(cursor.getCount() > 0) {
                return true;
                 }
            return false;
        }finally {
            cursor.close();
        }
    }

    public void incrementWins(String name, int wins) {
        String increment = "1";
        String winner = name;
        String[] values = new String[]{increment, winner};
        database.execSQL("UPDATE " + SQLhelper.TABLE_HIGHSCORELIST +
                " SET " + SQLhelper.COUNT_WIN + " = " + SQLhelper.COUNT_WIN + " + ?" +
                " WHERE " + SQLhelper.WINNER_NAME + " = ?", values);
    }

    public void clearTableHigscoreList() {
        database.delete(SQLhelper.TABLE_HIGHSCORELIST,null,null);
    }


}
