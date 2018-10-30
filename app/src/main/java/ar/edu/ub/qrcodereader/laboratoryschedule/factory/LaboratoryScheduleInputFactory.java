package ar.edu.ub.qrcodereader.laboratoryschedule.factory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.ub.qrcodereader.laboratoryschedule.model.input.LaboratoryScheduleInput;

public class LaboratoryScheduleInputFactory {

    public LaboratoryScheduleInput create(int id, String date){
        LaboratoryScheduleInput input = new LaboratoryScheduleInput();
        input.setId(id);
        input.setDateTime(this.transformDate(date));

        return input;
    }

    private DateTime transformDate(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        return formatter.parseDateTime(date);
    }
}
