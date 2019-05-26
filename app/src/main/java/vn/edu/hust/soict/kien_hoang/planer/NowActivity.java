package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
<<<<<<< HEAD
=======
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
>>>>>>> parent of 21447d4... Change UpdateJob
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
        scheduleJob();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskHelper.close();
    }

<<<<<<< HEAD
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
=======
    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, UpdateJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setPersisted(true)
                .setPeriodic(60 * 1000)  // 60*1000 milliseconds = 1 minute
                .build();

        Log.d("Check","OK in schedulerJob()");
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Check","True");
        } else {
            Log.d("Check","Fail");
        }
    }




    /** Class to update task every minute
     * Use Job Scheduler API
>>>>>>> parent of 21447d4... Change UpdateJob
     */

    public class UpdateJobService extends JobService {

        @Override
        public boolean onStartJob(JobParameters params) {
            Log.d("Check","OK");
            doBackgroundWork(params);
            return true;
        }

        private void doBackgroundWork(final JobParameters params) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String stringTimeLeft = (String) timeLeft.getText();
                    boolean haveTask = false;

                    if (!stringTimeLeft.equals("")){
                        Log.d("Check","OK");
                        haveTask = true;
                        long longTimeLeft = Time.valueOf(stringTimeLeft).getTime();
                        longTimeLeft -= 1000;
                        if (longTimeLeft > 0) {
                            stringTimeLeft = new Time(longTimeLeft).toString();
                            timeLeft.setText(stringTimeLeft);
                        }
                        else {
                            haveTask = false;
                        }
                    }

//                    if (!haveTask) {
//                        Cursor c = taskHelper.getAll();
//                        c.moveToFirst();
//                        do {
//
//                        } while (c.moveToNext());
//                    }
                    jobFinished(params, false);
                }
            }).start();
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            return true;
        }
    }
}
