package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DayActivity extends Activity {
    private  TaskHelper taskHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
         taskHelper = new TaskHelper(DayActivity.this);
        // Hàm dùng để chuyển dữ liệu từ cơ sở dữ liệu lên listview trên màn hình của người dùng
        populateListView();
    }

    private void populateListView() {
        Cursor cursor = taskHelper.getAll(); // Lấy dữ liệu từ cơ sở dữ liệu
        String[] fromFieldNames = new String[] {"_id","name", "startTime", "finishTime" , "date", "isDone"}; // Thiết lập thông tin các trường cần đưa lên
        int[] taskList = new int[] {R.id.taskName, R.id.startTime, R.id.finishTime, R.id.done }; // Thiết lập các id tương ứng với các dữ liệu cần đưa lên
        // Lấy dữ liệu vào con trỏ cursor và gán các thông tin tương ứng
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.row,cursor, fromFieldNames, taskList,0 );
        // Tạo và lấy id Listview để đưa dữ liệu lên
        ListView myTaskList = (ListView) findViewById(R.id.taskList);
        // Gán ListView cho adapter
        myTaskList.setAdapter(myCursorAdapter);
    }
}

