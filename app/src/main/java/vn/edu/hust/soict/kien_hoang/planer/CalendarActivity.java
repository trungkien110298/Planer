package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CalendarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        Log.d("Check", "Ok");
        Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_LONG).show();
    }
}
