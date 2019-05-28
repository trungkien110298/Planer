package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayActivity extends Activity {
    private TaskHelper taskHelper;
    List<Task> model = new ArrayList<Task>();
    TaskAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        taskHelper = new TaskHelper(DayActivity.this);
        ListView list = findViewById(R.id.taskList);

        adapter = new TaskAdapter();
        list.setAdapter(adapter);
        updateTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskHelper.close();
    }

    private void updateTask() {
        Task task;
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String sCurrentDate = new Date(currentYear - 1900, currentMonth, currentDay).toString();

        Cursor c = taskHelper.getAll();
        c.moveToFirst();
        do {
            Log.d("Cursor", "" + taskHelper.getName(c));
            String sTaskDate = taskHelper.getDate(c).toString();
            if (sCurrentDate.equals(sTaskDate)) {
                task = new Task();
                task.setName(taskHelper.getName(c));
                task.setStartTime(taskHelper.getStartTime(c).toString());
                task.setFinishTime(taskHelper.getFinishTime(c).toString());
                task.setDate(sTaskDate);
                task.setDone(taskHelper.getIsDone(c));
                adapter.add(task);
            }
        } while (c.moveToNext());
    }


    class TaskAdapter extends ArrayAdapter<Task> {
        TaskAdapter() {
            super(DayActivity.this, R.layout.row, model);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row = convertView;
            TaskHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();

                row = inflater.inflate(R.layout.row, parent, false);
                holder = new TaskHolder(row);
                row.setTag(holder);
            } else {
                holder = (TaskHolder) row.getTag();
            }

            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class TaskHolder {
        private TextView taskName;
        private TextView startTime;
        private TextView finishTime;
        private TextView isDone;

        TaskHolder(View row) {
            taskName = row.findViewById(R.id.taskName);
            startTime = row.findViewById(R.id.startTime);
            finishTime = row.findViewById(R.id.finishTime);
            isDone = row.findViewById(R.id.done);
        }

        void populateFrom(Task r) {
            taskName.setText(r.getName());
            startTime.setText(r.getStartTime());
            finishTime.setText(r.getFinishTime());
            if (r.getDone()) {
                isDone.setText("Completed");
            } else {
                isDone.setText("Not Done");
            }
        }
    }
}


