package ar.edu.ub.qrcodereader.laboratoryschedule.model.output;

import java.util.ArrayList;
import java.util.List;

public class LaboratorySchedule {

    private String laboratoryName;
    private List<ClassScheduleDetail> classScheduleDetails;

    private LaboratorySchedule(){}

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public List<ClassScheduleDetail> getClassScheduleDetails() {
        return classScheduleDetails;
    }

    public static class Builder{
        private String laboratoryName;
        private List<ClassScheduleDetail> classScheduleDetails = new ArrayList<>();

        public Builder withLaboratoryName(int id){
            laboratoryName = Laboratory.valueOf(String.format("LABORATORY_%s", id)).id();
            return this;
        }

        public Builder withClassScheduleDetails(List<ClassScheduleDetail> classScheduleDetails){
            this.classScheduleDetails = classScheduleDetails;
            return this;
        }

        public LaboratorySchedule build(){
            LaboratorySchedule laboratorySchedule = new LaboratorySchedule();
            laboratorySchedule.laboratoryName = this.laboratoryName;
            laboratorySchedule.classScheduleDetails = this.classScheduleDetails;
            return laboratorySchedule;
        }
    }
}
