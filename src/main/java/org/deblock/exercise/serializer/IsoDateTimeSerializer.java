package org.deblock.exercise.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

//TODO add documentation
public class IsoDateTimeSerializer extends StdScalarSerializer<Instant> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());

    public IsoDateTimeSerializer() {
        this(Instant.class);
    }

    protected IsoDateTimeSerializer(Class<Instant> t) {
        super(t);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String date = formatter.format(value);
        gen.writeString(date);
    }
}
