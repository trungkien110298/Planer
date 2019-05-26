package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DayActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                String routes = bundle.getString("routes");
                if(routes.equals("Day"))
                {
                    // Chuyen huong intent sang
                    Intent redirectIntent = new Intent(this,DayActivity.class);
                    Bundle redirectBundle = bundle;
                    intent.putExtra("bundle",bundle);
                    startActivity(redirectIntent);
                }
            } else {
                Toast.makeText(this,intent.getStringExtra("date"),Toast.LENGTH_LONG).show();
            }
        }

    }
}

