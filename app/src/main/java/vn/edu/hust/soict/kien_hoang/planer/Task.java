package vn.edu.hust.soict.kien_hoang.planer;

import java.sql.Date;
import java.util.Calendar;
public class Task {
    private String name;
    private String startTime;
    private String finishTime;
    private String date ;
    private boolean isDone ;

    public Task()  {
        date = Calendar.getInstance().getTime().toString();
        name = "" ; 
        isDone = false ; 
    }

    public Task(  String taskName, String __startTime, String __finishTime) {
        name = taskName ;
        startTime = __startTime ;
        finishTime = __finishTime;
        isDone = false ;
    }

    public Task(String name, String startTime, String finishTime, String date, boolean done) {
        this.name = name ;
        this.startTime = startTime ;
        this.finishTime = finishTime;
        this.date = date;
        isDone = false ;
    }
    // Lấy tên công việc cần làm
    public String getName() {
        return name;
    }
    // Gán tên công việc cần làm
    public void setName(String _name) {
        name = _name;
    }
    // Lấy thời gian bắt đầu công việc
    public String getStartTime() {
        return startTime;
    }
    // Gán thời gian bắt đầu công việc
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    // Lấy thời gian kết thúc công việc
    public String getFinishTime() {
        return finishTime;
    }
    // Gán thời gian kết thúc công việc
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
    // Lấy ngày thực hiện công việc
    public String getDate() {
        return date;
    }
    // Gán ngày thực hiện công việc
    public void setDate(String date)
    {
        this.date = date;
    }
    // Lấy trạng thái công việc
    public Boolean getDone() {
        return isDone ;
    }
    // Gán trạng thái công việc
    public void setDone(Boolean isDone) {
        this.isDone = isDone ;
    }
    // Chuyển thông tin công việc thành chuỗi để hiển thị
    @Override
    public String toString()  {
        return "Do task: " + name + " from " + startTime + " to " + finishTime + " date:" + date + "is " + isDone;
    }
}
