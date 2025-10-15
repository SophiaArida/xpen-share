package com.expenshare.event;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Singleton;

@Singleton
@KafkaClient
public class KafkaProducer {

    @Topic("${kafka.topics.userCreated}")
    public void publishUserCreatedEvent(String payload) {
        // Publish user.created event
    }

    @Topic("${kafka.topics.groupCreated}")
    public void publishGroupCreatedEvent(String payload) {
        // Publish group.created event
    }

    @Topic("${kafka.topics.notificationWelcome}")
    public void publishNotificationWelcomeEvent(String payload) {
        // Publish notification.welcome event
    }

    @Topic("${kafka.topics.expenseAdded}")
    public void publishExpenseAddedEvent(String payload) {
        // Publish expense.added event
    }

    @Topic("${kafka.topics.settlementConfirmed}")
    public void publishSettlementConfirmedEvent(String payload) {
        // Publish settlement.confirmed event
    }
}
