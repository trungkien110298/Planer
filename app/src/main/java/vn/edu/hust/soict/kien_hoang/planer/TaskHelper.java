package vn.edu.hust.soict.kien_hoang.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "planner.db";
    private static final int SCHEMA_VERSION = 1;

    public TaskHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "startTime TEXT, finishTime TEXT, isDone TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    public Cursor getAll() {
        return (getReadableDatabase()
                .rawQuery("SELECT _id, name, startTime, finishTime FROM users ORDER BY _id", null));
    }


    public void insert(String name, Time startTime, Time finishTime, Date date, boolean isDone) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("startTime", startTime.toString());
        cv.put("finishTime", finishTime.toString());
        cv.put("isDone", Boolean.toString(isDone));
        getWritableDatabase().insert("tasks", "name", cv);
    }

    public String getName(Cursor c) {
        return (c.getString(1));
    }

    public Date getStartTime(Cursor c) {
        String s = c.getString(2);
        DateFormat df = new SimpleDateFormat();

        Date startTime = null;
        try {
            startTime = df.parse(s);
        } catch (ParseException e) {
            return null;
        }
        return startTime;
    }

    public Date getFinishTime(Cursor c) {
        String s = c.getString(2);
        DateFormat df = new SimpleDateFormat();

        Date finishTime = null;
        try {
            finishTime = df.parse(s);
        } catch (ParseException e) {
            return null;
        }
        return finishTime;
    }

    public boolean getIsDone(Cursor c) {
        return (Boolean.parseBoolean(c.getString(3)));
    }
}
