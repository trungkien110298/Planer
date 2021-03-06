package vn.edu.hust.soict.kien_hoang.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;

public class TaskHelper extends SQLiteOpenHelper {
    public SQLiteDatabase db ;
    // Hàm khởi tạo đối tượng UserHelper
    private static final String DATABASE_NAME = "task.db";
    private static final int SCHEMA_VERSION = 1;

    public TaskHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASE CREATE", "DATABASE ONCREATE SQLITEDB TASKHELPER");
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "startTime TEXT, finishTime TEXT, date TEXT, isDone TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
        //onCreate(db);
        //onUpgrade(db, oldVersion, newVersion);
    }
    public Cursor getAll() {
        return (getReadableDatabase()
                .rawQuery("SELECT * FROM tasks ORDER BY _id", null));
    }
    public void insert(String name, Time startTime, Time finishTime, Date date, boolean isDone) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("startTime", startTime.toString());
        cv.put("finishTime", finishTime.toString());
        cv.put("date", date.toString());
        cv.put("isDone", Boolean.toString(isDone));
        // Thêm bản ghi vào cơ sở dữ liệu
        getWritableDatabase().insert("tasks", "name", cv);
    }
    // Lấy ra tên công việc cần thực hiện
    public String getName(Cursor c) {
        return (c.getString(1));
    }
    // Lấy ra thời gian bắt đầu thực hiện công việc từ con trỏ
    public Time getStartTime(Cursor c) {
        String s = c.getString(2);
        Time startTime = Time.valueOf(s);
        return startTime;
    }
    // Lấy ra thời gian kết thúc thực hiện công việc từ con trỏ
    public Time getFinishTime(Cursor c) {
        String s = c.getString(3);
        Time finishTime = Time.valueOf(s);
        return finishTime;
    }
    // Lấy ra ngày thực hiện công việc từ con trỏ
    public Date getDate(Cursor c) {
        String s = c.getString(4);
        Date date = Date.valueOf(s);
        return date;
    }
    // Lấy ra trạng thái công việc
    public boolean getIsDone(Cursor c) {
        return (Boolean.parseBoolean(c.getString(5)));
    }
}
