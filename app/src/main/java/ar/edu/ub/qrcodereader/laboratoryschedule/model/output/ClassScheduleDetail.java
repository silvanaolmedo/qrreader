package ar.edu.ub.qrcodereader.laboratoryschedule.model.output;

import java.util.List;

public class ClassScheduleDetail {
    private int dayOfWeek;
    private List<ClassDetail> classesDetails;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ClassDetail> getClassesDetails() {
        return classesDetails;
    }

    public void setClassesDetails(List<ClassDetail> classesDetails) {
        this.classesDetails = classesDetails;
    }
}
