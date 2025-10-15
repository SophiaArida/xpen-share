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
//        sendEmail(message);
//        sendSMS(message);
    }
    @Topic("user.created")
    public void handleUserCreated(String message) {
        System.out.println("\u001B[32m" + "User created event received: " + message + "\u001B[0m");
//        logAnalytics(message);
//        triggerWelcomeNotification(message);
    }
    @Topic("group.created")
    public void handleGroupCreated(String message) {
        System.out.println("\u001B[32m" + "Group created event received: " + message + "\u001B[0m");
//        notifyGroupMembers(message);
    }
    @Topic("expense.added")
    public void handleExpenseAdded(String message) {
        System.out.println("\u001B[32m" + "Expense added event received: " + message + "\u001B[0m");
//        updateExpenseSummary(message);
    }
    @Topic("settlement.confirmed")
    public void handleSettlementConfirmed(String message) {
        System.out.println("\u001B[32m" +" Settlement confirmed event received: " + message + "\u001B[0m");
//        sendSettlementConfirmation(message);
    }
    // ===== Placeholder methods =====
//    private void sendEmail(String msg) {
//        System.out.println("Sending email: " + msg);
//    }
//    private void sendSMS(String msg) {
//        System.out.println("Sending SMS: " + msg);
//    }
//    private void logAnalytics(String msg) {
//        System.out.println("Logging analytics: " + msg);
//    }
//    private void triggerWelcomeNotification(String msg) {
//        System.out.println("Triggering welcome notification: " + msg);
//    }
//    private void notifyGroupMembers(String msg) {
//        System.out.println("Notifying group members: " + msg);
//    }
//    private void updateExpenseSummary(String msg) {
//        System.out.println("Updating expense summary: " + msg);
//    }
//    private void sendSettlementConfirmation(String msg) {
//        System.out.println("Sending settlement confirmation: " + msg);
//    }
}