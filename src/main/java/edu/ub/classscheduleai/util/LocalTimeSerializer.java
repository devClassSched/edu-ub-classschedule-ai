package edu.ub.classscheduleai.util;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(
            LocalTime time, 
            JsonGenerator gen, 
            SerializerProvider arg2) throws IOException, JsonProcessingException {
    	DateTimeFormatter formatter
        = DateTimeFormatter.ofPattern("HH:mm");
        gen.writeString(formatter.format(time));
    }
}
