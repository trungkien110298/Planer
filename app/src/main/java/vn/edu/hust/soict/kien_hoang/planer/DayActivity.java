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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        populateListView();
    }

    private void populateListView() {
//        Log.d("asdasfasfasfasasdasd", "asdasfasfasfasasdasd");
//        TaskHelper taskHelper = new TaskHelper(DayActivity.this);
//        Log.d("abcdef123a", "1");
//        Cursor cursor = taskHelper.getAll();
//        Log.d("2", "2");
//        String[] fromFieldNames = new String[] {"_id","name", "startTime", "finishTime" , "date", "isDone"};
//        Log.d("3", "3");
//        int[] taskList = new int[] {R.id.taskName, R.id.startTime, R.id.finishTime, R.id.done };
//        Log.d("4", "4");
//        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.row,cursor, fromFieldNames, taskList,0 );
//        Log.d("5", "5");
//        ListView myTaskList = (ListView) findViewById(R.id.taskList);
//        Log.d("6", "6");
//        myTaskList.setAdapter(myCursorAdapter);
    }
}

