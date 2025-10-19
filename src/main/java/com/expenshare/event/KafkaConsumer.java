package com.expenshare.event;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Singleton;
@Singleton
@KafkaListener(groupId = "notification-group")
public class KafkaConsumer {
    @Topic("notification.welcome")
    public void handleWelcomeNotification(String message) {
        System.out.println("\u001B[32m" + "Received welcome notification: " + message + "\u001B[0m");
    }
    @Topic("user.created")
    public void handleUserCreated(String message) {
        System.out.println("\u001B[32m" + "User created event received: " + message + "\u001B[0m");
    }
    @Topic("group.created")
    public void handleGroupCreated(String message) {
        System.out.println("\u001B[32m" + "Group created event received: " + message + "\u001B[0m");
    }
    @Topic("expense.added")
    public void handleExpenseAdded(String message) {
        System.out.println("\u001B[32m" + "Expense added event received: " + message + "\u001B[0m");
    }
    @Topic("settlement.confirmed")
    public void handleSettlementConfirmed(String message) {
        System.out.println("\u001B[32m" +" Settlement confirmed event received: " + message + "\u001B[0m");
    }

}