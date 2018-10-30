package ar.edu.ub.qrcodereader.laboratoryschedule.service;


import android.content.Context;

import ar.edu.ub.qrcodereader.laboratoryschedule.factory.LaboratoryScheduleDetailFactory;
import ar.edu.ub.qrcodereader.laboratoryschedule.factory.LaboratoryScheduleInputFactory;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.input.LaboratoryScheduleInput;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.LaboratoryScheduleDetail;

public class LaboratoryScheduleService {

    private LaboratoryScheduleInputFactory inputFactory;
    private LaboratoryScheduleDetailFactory scheduleDetailFactory;

    public LaboratoryScheduleService(Context context) {
        inputFactory = new LaboratoryScheduleInputFactory();
        scheduleDetailFactory = new LaboratoryScheduleDetailFactory(context);
    }

    public LaboratoryScheduleDetail findSchedule(int id, String date){
        LaboratoryScheduleInput input = this.inputFactory.create(id, date);
        LaboratoryScheduleDetail laboratoryScheduleDetail = scheduleDetailFactory.create(input);

        return laboratoryScheduleDetail;
    }
}
