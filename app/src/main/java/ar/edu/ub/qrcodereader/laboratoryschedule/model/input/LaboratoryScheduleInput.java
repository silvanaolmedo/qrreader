package ar.edu.ub.qrcodereader.laboratoryschedule.model.input;

import org.joda.time.DateTime;

public class LaboratoryScheduleInput {

    private int id;
    private DateTime dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
