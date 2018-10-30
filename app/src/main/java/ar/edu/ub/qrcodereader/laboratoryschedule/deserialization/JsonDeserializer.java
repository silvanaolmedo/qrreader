package ar.edu.ub.qrcodereader.laboratoryschedule.deserialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.ub.qrcodereader.laboratoryschedule.model.output.ClassScheduleDetail;

public class JsonDeserializer {

    private ObjectMapper mapper;

    public JsonDeserializer(){
        mapper = new ObjectMapper();
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY));
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JodaModule());
    }

    public Map<Integer, List<ClassScheduleDetail>> toMap(String json){
        try {
            return this.mapper.readValue(json, new TypeReference<HashMap<Integer, List<ClassScheduleDetail>>>(){});
        } catch (IOException e) {
            throw new RuntimeException("There was a problem trying to deserialize the message '" + json + "'", e);
        }
    }
}
