package com.example.itp4501_assignment;

import static android.content.ContentValues.TAG;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DatabaseFunction {
    SQLiteDatabase db;

    public DatabaseFunction() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501_assignment/gameRecords", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            db.execSQL("CREATE TABLE GameLog(gameID int PRIMARY KEY, playerName text, playDate text, playTime text, moves int, style text);");
            db.close();
        }
        catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void openDatabase() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.itp4501_assignment/gameRecords", null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public ArrayList<Ranking> getAllGameLog(Boolean sortByMoves) {
        ArrayList<Ranking> rankings = new ArrayList<>();

        String sql = "SELECT * FROM GameLog";
        if (sortByMoves)
            sql += " ORDER BY moves DESC, playDate DESC, playTime DESC";

        openDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToPosition(cursor.getCount() + 1);

        //get records form last
        for (int i = 0; cursor.moveToPrevious(); i++) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("playerName"));
            int moves = cursor.getInt(cursor.getColumnIndexOrThrow("moves"));
            String dateTime = cursor.getString(cursor.getColumnIndexOrThrow("playDate")) + " " +
                              cursor.getString(cursor.getColumnIndexOrThrow("playTime"));
            String style = cursor.getString(cursor.getColumnIndexOrThrow("style"));

            //put all records in arrayList
            if (sortByMoves)
                rankings.add(new Ranking(name, moves, i, dateTime, style));
            else
                rankings.add(new Ranking(name, moves, -1, dateTime, style));
        }

        cursor.close();
        db.close();
        return rankings;
    }

    public void addGameRecord(String name, int moves, String style) {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        int newGameID = countRecords() + 1;
        String playDate = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
        String playTime = time.getHour() + ":" + time.getMinute();
        String sql = "INSERT INTO GameLog(gameID, playerName, playDate, playTime, moves, style) VALUES(?, ?, ?, ?, ?, ?);";
        Object[] args = {newGameID, name, playDate, playTime, moves, style};

        openDatabase();
        db.execSQL(sql, args);
        db.close();
    }

    public int countRecords() {
        String sql = "SELECT * FROM GameLog";

        openDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int num = cursor.getCount();
        cursor.close();
        db.close();

        return num;
    }
}
