package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NowActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now);
        Log.d("Check", "OK");
        Button addTaskButton = (Button) findViewById(R.id.editCurrentTaskBtn);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Check", "OK");
                Intent newTaskIntent = new Intent(NowActivity.this, NewTaskActivity.class);
                startActivity(newTaskIntent);

            }
        });
    }
}
