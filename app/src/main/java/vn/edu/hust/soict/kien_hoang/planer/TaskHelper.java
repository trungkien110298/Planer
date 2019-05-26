package vn.edu.hust.soict.kien_hoang.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.sql.Time;

public class TaskHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "planner.db";
    private static final int SCHEMA_VERSION = 1;

    public TaskHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "startTime TEXT, finishTime TEXT, date TEXT, isDone TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getAll() {
        return (getReadableDatabase()
                .rawQuery("SELECT _id, name, startTime, finishTime, date, isDone FROM tasks ORDER BY _id", null));
    }



    public void insert(String name, Time startTime, Time finishTime, Date date, boolean isDone) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("startTime", startTime.toString());
        cv.put("finishTime", finishTime.toString());
        cv.put("date", date.toString());
        cv.put("isDone", Boolean.toString(isDone));
        getWritableDatabase().insert("tasks", "name", cv);
    }

    public String getName(Cursor c) {
        return (c.getString(1));
    }

    public Time getStartTime(Cursor c) {
        String s = c.getString(2);
        Time startTime = Time.valueOf(s);
        return startTime;
    }

    public Time getFinishTime(Cursor c) {
        String s = c.getString(3);
        Time finishTime = Time.valueOf(s);
        return finishTime;
    }

    public Date getDate(Cursor c) {
        String s = c.getString(4);
        Date date = Date.valueOf(s);
        return date;
    }
    public boolean getIsDone(Cursor c) {
        return (Boolean.parseBoolean(c.getString(5)));
    }
}
