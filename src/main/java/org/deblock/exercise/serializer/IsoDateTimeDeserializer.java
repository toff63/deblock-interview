package org.deblock.exercise.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Deserialize ISO_DATE_TIME format to Instant
 */
public class IsoDateTimeDeserializer extends StdScalarDeserializer<Instant> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());

    protected IsoDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        TextNode node = jp.getCodec().readTree(jp);
        return Instant.from((formatter.parse(node.textValue())));
    }
}
