package com.expenshare.event;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;

@Serdeable
public class EventEnvelop<T> {
    private String eventId;
    private Instant occuredAt;
    private T palyload;

    public EventEnvelop() {
    }

    public EventEnvelop(String eventId, Instant occuredAt, T palyload) {
        this.eventId = eventId;
        this.occuredAt = occuredAt;
        this.palyload = palyload;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Instant getOccuredAt() {
        return occuredAt;
    }

    public void setOccuredAt(Instant occuredAt) {
        this.occuredAt = occuredAt;
    }

    public T getPalyload() {
        return palyload;
    }

    public void setPalyload(T palyload) {
        this.palyload = palyload;
    }
}
