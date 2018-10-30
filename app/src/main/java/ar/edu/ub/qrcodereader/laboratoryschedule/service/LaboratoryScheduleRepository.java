package ar.edu.ub.qrcodereader.laboratoryschedule.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.ub.qrcodereader.R;
import ar.edu.ub.qrcodereader.laboratoryschedule.deserialization.JsonDeserializer;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassScheduleDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.LaboratorySchedule;
import ar.edu.ub.qrcodereader.laboratoryschedule.util.FileReaderUtil;

public class LaboratoryScheduleRepository {

    private Map<Integer, List<ClassScheduleDetail>> classesScheduleByLaboratory = new HashMap<>();

    public LaboratoryScheduleRepository(Context context){
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        String json = FileReaderUtil.readFileAsString(context, R.raw.horarios_laboratorios);
        classesScheduleByLaboratory = jsonDeserializer.toMap(json);
    }

    public LaboratorySchedule retrieve(int id){

        List<ClassScheduleDetail> classScheduleDetails = new ArrayList<>();

        if(classesScheduleByLaboratory.containsKey(id)){
            classScheduleDetails = classesScheduleByLaboratory.get(id);
        }

        return new LaboratorySchedule.Builder()
                                    .withLaboratoryName(id)
                                    .withClassScheduleDetails(classScheduleDetails)
                                    .build();
    }


}
