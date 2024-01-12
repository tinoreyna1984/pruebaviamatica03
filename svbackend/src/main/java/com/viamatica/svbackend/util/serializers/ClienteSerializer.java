package com.viamatica.svbackend.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.viamatica.svbackend.model.entity.Cliente;

import java.io.IOException;

public class ClienteSerializer extends JsonSerializer<Cliente> {
    @Override
    public void serialize(Cliente cliente, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", cliente.getId());
        jsonGenerator.writeStringField("fullName", cliente.getName() + " " + cliente.getLastName());
        jsonGenerator.writeStringField("docId", cliente.getDocId());
        jsonGenerator.writeStringField("phone", cliente.getPhone());
        jsonGenerator.writeStringField("email", cliente.getEmail());
        jsonGenerator.writeEndObject();
    }
}
