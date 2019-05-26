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

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Boolean getDone() {
        return isDone ;
    }

    public void setDone(Boolean isDone) {
        this.isDone = isDone ;
    }

    @Override
    public String toString()  {
        return "Do task: " + name + " from " + startTime + " to " + finishTime + " date:" + date + "is " + isDone;
    }
}
