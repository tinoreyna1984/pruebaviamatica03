package com.viamatica.svbackend.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.viamatica.svbackend.model.entity.Cliente;
import com.viamatica.svbackend.model.entity.Pago;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteConPagoSerializer extends JsonSerializer<Cliente> {
    @Override
    public void serialize(Cliente cliente, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", cliente.getId());
        jsonGenerator.writeStringField("fullName", cliente.getName() + " " + cliente.getLastName());
        jsonGenerator.writeStringField("docId", cliente.getDocId());
        jsonGenerator.writeStringField("phone", cliente.getPhone());
        jsonGenerator.writeStringField("email", cliente.getEmail());
        // pagos - ini
        jsonGenerator.writeArrayFieldStart("pagos");
        for (Pago pago : cliente.getPagos()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", pago.getId());
            jsonGenerator.writeStringField("fechaPago", formatDate(pago.getFechaPago()));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        // pagos - fin
        jsonGenerator.writeEndObject();
    }

    private String formatDate(Date date) {
        // Aquí utilizas un formato SimpleDateFormat para formatear la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }
}
