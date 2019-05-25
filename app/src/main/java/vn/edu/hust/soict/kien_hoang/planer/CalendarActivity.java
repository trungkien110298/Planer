package vn.edu.hust.soict.kien_hoang.planer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TabHost;
import android.widget.Toast;

public class CalendarActivity extends Activity {
    CalendarView simpleCalendarView;
    public static final String BUNDLE = "bundel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        Log.d("a","Run Calendar");
        Toast.makeText(this, "Show calendar", Toast.LENGTH_LONG).show();
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // Thiết lập màu đỏ cho các tuần, Từ API 23 trở lên mới hỗ trợ
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // Thiết lập cho đường khoảng cách giữa các tuần là màu xanh
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Intent intent = new Intent(CalendarActivity.this,DayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Date",dayOfMonth + "/" + month + "/" + year);
                intent.putExtra(BUNDLE,bundle);
                startActivity(intent);

                TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
                tabHost.setup();
                tabHost.setCurrentTab(1);
//                Toast.makeText(getApplicationContext(), "Show tabhost" , Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
