package com.expenshare.event;

import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Singleton
public class KafkaProducer {

    private final Producer<String, String> producer;
    private final ObjectMapper objectMapper;

    @Inject
    public KafkaProducer(Producer<String, String> producer, ObjectMapper objectMapper) {
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<RecordMetadata> publish(String topic, String key, Object payload) {
        EventEnvelop<?> envelope = new EventEnvelop<>(UUID.randomUUID().toString(), Instant.now(), payload);
        String json;
        try { json = objectMapper.writeValueAsString(envelope); }
        catch (IOException e) { return CompletableFuture.failedFuture(e); }
        ProducerRecord<String,String> record = new ProducerRecord<>(topic, key, json);
        CompletableFuture<RecordMetadata> f = new CompletableFuture<>();
        producer.send(record, (metadata, ex) -> {
            if (ex != null) f.completeExceptionally(ex);
            else f.complete(metadata);
        });
        return f;
    }
}
