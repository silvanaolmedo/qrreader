package ar.edu.ub.qrcodereader.laboratoryschedule.model.output;

public class LaboratoryScheduleDetail {
    private String laboratoryName;
    private Status status;
    private ClassDetail classDetail;

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClassDetail getClassDetail() {
        return classDetail;
    }

    public void setClassDetail(ClassDetail classDetail) {
        this.classDetail = classDetail;
    }
}
