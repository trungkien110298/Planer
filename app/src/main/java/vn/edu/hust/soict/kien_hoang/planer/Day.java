package vn.edu.hust.soict.kien_hoang.planer;

import java.util.ArrayList;
import java.util.Date;

public class Day {

    private Date date;
    private ArrayList<Task> tasks;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
