package vn.edu.hust.soict.kien_hoang.planer;

import java.util.ArrayList;

public class Plan {
    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Week> weeks) {
        this.weeks = weeks;
    }

    private ArrayList<Week> weeks;
}
