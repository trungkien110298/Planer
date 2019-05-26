package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DayActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);

    }

    private void populateListView() {
        TaskHelper taskHelper = new TaskHelper(DayActivity.this);
        Cursor cursor = taskHelper.getAll();
    }
}

