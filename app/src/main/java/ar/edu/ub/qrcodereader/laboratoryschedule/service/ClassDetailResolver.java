package ar.edu.ub.qrcodereader.laboratoryschedule.service;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassScheduleDetail;

public class ClassDetailResolver {

    public ClassDetail resolve(DateTime dateTime, List<ClassScheduleDetail> classScheduleDetails){
        int dayOfWeek = dateTime.getDayOfWeek();
        LocalTime actualTime = new LocalTime(dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), dateTime.getSecondOfMinute());

        List<ClassDetail> filteredClasses = filterClassesByDay(dayOfWeek, classScheduleDetails);

        for(ClassDetail classDetail: filteredClasses){
            if(!(actualTime.isBefore(classDetail.getStartTime()) || actualTime.isAfter(classDetail.getEndTime()))){
                return classDetail;
            }

        }
        return null;
    }

    private List<ClassDetail> filterClassesByDay(int dayOfWeek, List<ClassScheduleDetail> classScheduleDetails){
        for (ClassScheduleDetail schedule: classScheduleDetails) {
            if(schedule.getDayOfWeek() == dayOfWeek){
                return schedule.getClassesDetails();
            }
        }

        return new ArrayList<>();
    }
}
