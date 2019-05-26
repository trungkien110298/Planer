package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class NewTaskActivity extends Activity {
    private EditText etStartTime;
    private EditText etFinishTime;
    private EditText etDate;
    private EditText etName;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private String name;
    private TaskHelper taskHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        taskHelper = new TaskHelper(this);
        Toast.makeText(this, "ONCREATE", Toast.LENGTH_SHORT).show();
        Log.d("","ONCREATE_NEWTASK");
        //Name of task
        etName = findViewById(R.id.et_name);


        //Choose start time
        etStartTime = findViewById(R.id.etStartTime);
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startTime = new Time(hourOfDay, minutes, 0);
                        String tail;
                        if (hourOfDay >= 12) {
                            hourOfDay -= 12;
                            tail = "PM";
                        } else {
                            tail = "AM";
                        }
//                        etStartTime.setCurrentMinute(minutes);
//                        etStartTime.setCurrentHour(hourOfDay);
                        etStartTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + tail);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        //Choose finish time
        etFinishTime = findViewById(R.id.etFinishTime);
        etFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        finishTime = new Time(hourOfDay, minutes, 0);
                        String tail;
                        if (hourOfDay >= 12) {
                            hourOfDay -= 12;
                            tail = "PM";
                        } else {
                            tail = "AM";
                        }
//                        etFinishTime.setCurrentMinute(minutes);
//                        etFinishTime.setCurrentHour(hourOfDay);
                        etFinishTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + tail);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        //Choose date
        etDate = findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(NewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date = new Date(year, month, dayOfMonth);
//                        etDate.updateDate(year,month ,dayOfMonth);
                        etDate.setText(String.format("%02d/%02d/%04d ", dayOfMonth, month, year));
                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });

        //Create new task
        Button btCreate = findViewById(R.id.bt_create);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                taskHelper.insert(name, startTime, finishTime, date, false);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Added new task", Toast.LENGTH_SHORT).show();
        taskHelper.close();
    }
}
