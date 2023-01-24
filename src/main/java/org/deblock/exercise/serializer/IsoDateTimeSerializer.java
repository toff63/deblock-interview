package org.deblock.exercise.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IsoDateTimeSerializer extends JsonSerializer<Instant> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String date = formatter.format(value);
        gen.writeString(date);
    }
}
