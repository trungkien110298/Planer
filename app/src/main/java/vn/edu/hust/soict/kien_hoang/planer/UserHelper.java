package vn.edu.hust.soict.kien_hoang.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "planner.db"; // Tên cơ sở dữ liệu
    private static final int SCHEMA_VERSION = 1; // Số phiên bản của cơ sở dữ liệu

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    } // Hàm khởi tạo đối tượng UserHelper

    // Ghi đè phương thức onCreate của lớp SQLiteHelper (Tạo bảng mới users)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);");
    }
    // Ghi đè phương thức onUpgrade của lớp SQLiteHelper (Thay đổi thông tin users)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no-op, since will not be called until 2nd schema
        // version exists
    }

    // Lấy ra con trỏ tới toàn bộ dữ liệu được truy vấn ra
    public Cursor getAll() {
        return (getReadableDatabase()
                .rawQuery("SELECT _id, username, password FROM users ORDER BY _id", null));
    }
    // Lấy ra con trỏ trở tới bản ghi có dữ liệu là tên người dùng
    public Cursor getByUsername(String username) {
        Cursor c = null;
        try {
            // Gọi hàm truy vấn để lấy dữ liệu  từ cơ sở dữ liệu
            c = getReadableDatabase()
                    .rawQuery("SELECT _id, username, password FROM users WHERE username = ?", new String[]{username});
        } catch (SQLiteException e) { // Bắt ngoại lệ khi truy vấn có lỗi
            return null;
        }
        return c;
    }
    //Thêm mới bản ghi sau khi người dùng đăng ký tài khoản
    public void insert(String username, String password) {
        // Thêm thông tin vào trong cơ sở dữ liệu sử dụng ContentValues
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        // Thêm bản ghi vào cơ sở dữ liệu
        getWritableDatabase().insert("users", "username", cv);
    }
    // Lấy ra tên tài khoản người dùng để so sánh
    public String getUsername(Cursor c) {
        return (c.getString(1));
    }

    // Lấy ra tên tài khoản người dùng để so sánh
    public String getPassword(Cursor c) {
        return (c.getString(2));
    }
}
