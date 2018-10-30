package ar.edu.ub.qrcodereader.laboratoryschedule.factory;

import android.content.Context;

import org.joda.time.DateTime;

import ar.edu.ub.qrcodereader.laboratoryschedule.model.input.LaboratoryScheduleInput;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.LaboratorySchedule;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.LaboratoryScheduleDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.Status;
import ar.edu.ub.qrcodereader.laboratoryschedule.service.ClassDetailResolver;
import ar.edu.ub.qrcodereader.laboratoryschedule.service.LaboratoryScheduleRepository;

import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

public class LaboratoryScheduleDetailFactory {

    private LaboratoryScheduleRepository repository;
    private ClassDetailResolver resolver;

    public LaboratoryScheduleDetailFactory(Context context) {
        repository = new LaboratoryScheduleRepository(context);
        resolver = new ClassDetailResolver();
    }

    public LaboratoryScheduleDetail create(LaboratoryScheduleInput input){
        LaboratorySchedule laboratorySchedule = this.repository.retrieve(input.getId());
        return this.createLaboratorySchedule(input, laboratorySchedule);
    }

    private LaboratoryScheduleDetail createLaboratorySchedule(LaboratoryScheduleInput input, LaboratorySchedule laboratorySchedule) {
        LaboratoryScheduleDetail result = new LaboratoryScheduleDetail();
        result.setLaboratoryName(laboratorySchedule.getLaboratoryName());
        result.setStatus(Status.UNOCCUPIED);

        DateTime dateTime = input.getDateTime();
        int dayOfWeek = dateTime.getDayOfWeek();
        if(dayOfWeek == (SATURDAY) || dayOfWeek == (SUNDAY)){

        }else{

            ClassDetail classDetail = this.resolver.resolve(dateTime, laboratorySchedule.getClassScheduleDetails());

            if(classDetail != null){
                result.setStatus(Status.OCCUPIED);
                result.setClassDetail(classDetail);
            }
        }
        return result;
    }



}
