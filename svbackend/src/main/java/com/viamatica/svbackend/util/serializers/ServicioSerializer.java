package com.viamatica.svbackend.util.serializers;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.viamatica.svbackend.model.entity.Dispositivo;
import com.viamatica.svbackend.model.entity.Servicio;

import java.io.IOException;

public class ServicioSerializer extends JsonSerializer<Servicio> {

    @Override
    public void serialize(Servicio servicio, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", servicio.getId());
        jsonGenerator.writeStringField("descripcion", servicio.getDescripcion());
        jsonGenerator.writeNumberField("precio", servicio.getPrecio());
        String dispositivoJson = serializeDispositivo(servicio.getDispositivo());
        jsonGenerator.writeStringField("dispositivo", dispositivoJson);
        jsonGenerator.writeEndObject();
    }

    private String serializeDispositivo(Dispositivo dispositivo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dispositivo);
    }
}
