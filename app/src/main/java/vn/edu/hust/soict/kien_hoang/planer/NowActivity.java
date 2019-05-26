package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;

public class NowActivity extends Activity {
    private TextView taskName;
    private TextView startTime;
    private TextView finishTime;
    private TextView timeLeft;
    private TaskHelper taskHelper;
    private Task task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now);
        taskHelper = new TaskHelper(this);

        //Task's information
        taskName = findViewById(R.id.nowTaskName);
        startTime = findViewById(R.id.nowTaskStartTime);
        finishTime = findViewById(R.id.nowTaskFinishTime);
        timeLeft = findViewById(R.id.nowTaskTimeLeft);


        // Add new task
        Button addTaskButton = findViewById(R.id.editCurrentTaskBtn);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTaskIntent = new Intent(NowActivity.this, NewTaskActivity.class);
                startActivity(newTaskIntent);

            }
        });

        // Update task every minutes
        Runnable runnable = new UpdateTask();
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskHelper.close();
    }

    public void updateTask() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Log.d("Check", "ok in count");
                    long lTimeLeft, lCurrentTime, lFinishTime;
                    if (task == null) {
                        task = getNewTask();
                    } else {
                        lCurrentTime = Calendar.getInstance().getTimeInMillis();
                        lFinishTime = Time.valueOf(task.getFinishTime()).getTime();
                        lTimeLeft = lFinishTime - lCurrentTime;
                        if(lTimeLeft < 0)
                            task = getNewTask();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    private Task getNewTask(){

    }

    /**
     * Class to update task every minute
     */

    class UpdateTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    updateTask();
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }





}
