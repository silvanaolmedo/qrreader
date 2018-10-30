package ar.edu.ub.qrcodereader.barcode;

import android.content.Context;

import com.google.android.gms.vision.barcode.Barcode;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.LaboratoryScheduleDetail;
import ar.edu.ub.qrcodereader.laboratoryschedule.service.LaboratoryScheduleService;

public class BarcodeCapturePresenter{
    private LaboratoryScheduleService service;

    public BarcodeCapturePresenter(Context context) {
        service = new LaboratoryScheduleService(context);
    }

    public String onBarcodeDetected(Barcode barcode) {
        String message = null;
        if (barcode != null) {
            message = barcode.displayValue;
            if(NumberUtils.isParsable(barcode.displayValue)){
                int id = Integer.parseInt(barcode.displayValue);
                if(id >= 1 && id <= 7){
                    String actualDateTime = DateTime.now().toString("yyyy-MM-dd HH:mm");
                    LaboratoryScheduleDetail schedule = service.findSchedule(id, actualDateTime);
                    message = this.createMessage(schedule);
                }
            }
        }
        return message;
    }

    private String createMessage(LaboratoryScheduleDetail schedule) {
        String defaultMessageFormat = "%s\nEstado: %s\n";
        String defaultMessage = String.format(defaultMessageFormat, schedule.getLaboratoryName(), schedule.getStatus().id());
        StringBuilder builder = new StringBuilder(defaultMessage);
        if(schedule.getClassDetail() != null){
            ClassDetail classDetail = schedule.getClassDetail();
            builder.append("Materia: ").append(classDetail.getName()).append("\n");
            if(classDetail.getCourseOfStudy() != null){
                builder.append("Carrera(s): ").append(classDetail.getCourseOfStudy()).append("\n");
            }
            if(classDetail.getCourseYear() != null){
                builder.append("Año(s): ").append(classDetail.getCourseYear()).append("\n");
            }
            builder.append("Profesor(es): ").append(classDetail.getInstructors()).append("\n");
            builder.append("Horario de inicio: ").append(classDetail.getStartTime().toString("HH:mm:ss")).append("\n");
            builder.append("Horario de finalización: ").append(classDetail.getEndTime().toString("HH:mm:ss"));
        }
        return builder.toString();
    }

}
