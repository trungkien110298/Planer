package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class DayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(CalendarActivity.BUNDLE);
        Toast.makeText(this,bundle.getString("date"),Toast.LENGTH_LONG).show();
    }
}

