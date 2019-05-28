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
    private EditText etStartTime; // Ô nhập thời gian bắt đầu
    private EditText etFinishTime; // Ô nhập thời gian kết thúc
    private EditText etDate; // Ô nhập ngày thực hiện công việc
    private EditText etName; // Ô nhập tên công việc
    private DatePickerDialog datePickerDialog; // Hiển thị chọn ngày
    private TimePickerDialog timePickerDialog; // Hiển thị chọn thời gian
    private Date date; // Ngày thực hiện công việc
    private Time startTime; // Thời gian bắt đầu công việc
    private Time finishTime; // Thời gian kết thúc công việc
    private String name; // Tên công việc
    private TaskHelper taskHelper; // Đối tượng hỗ trợ tao tác với cơ sở dữ liệu bảng tasks


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        taskHelper = new TaskHelper(this);
        //Name of task
        etName = findViewById(R.id.et_name);


        // Chọn thời gian bắt đầu
        etStartTime = findViewById(R.id.etStartTime);
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                // Lấy thời gian
                timePickerDialog = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        startTime = new Time(hourOfDay, minutes, 0);
                        String tail;
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            tail = "PM";
                        } else {
                            tail = "AM";
                        }
                        // Gán lại thời gian đã nhập theo đúng định dạng vào ô thời gian bắt đầu
                        etStartTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + tail);
                    }
                }, currentHour, currentMinute, false);
                // Hiển thị thời gian đã chọn
                timePickerDialog.show();
            }
        });

        //Tương tự như thời gian bắt đầu
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
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            tail = "PM";
                        } else {
                            tail = "AM";
                        }
                        etFinishTime.setText(String.format("%02d:%02d ", hourOfDay, minutes) + tail);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        //Chọn ngày thực hiện công việc
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
                        date = new Date(year-1900, month, dayOfMonth);
                        // Gán lại ngày đã nhập theo đúng định dạng vào ô thời gian bắt đầu
                        etDate.setText(String.format("%02d/%02d/%04d ", dayOfMonth, month+1, year));
                    }
                }, currentYear, currentMonth, currentDay);
                // Hiển thị ngày đã chọn
                datePickerDialog.show();
            }
        });

        //Tạo mới công việc
        Button btCreate = findViewById(R.id.bt_create);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                // Thêm công việc đó vào cơ sở dữ liệu bằng hàm insert
                taskHelper.insert(name, startTime, finishTime, date, false);
                Toast.makeText(getApplicationContext(), "Added new task", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        taskHelper.close();
    }
}
