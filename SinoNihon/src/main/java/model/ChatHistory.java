package model;

import java.sql.Timestamp;

public class ChatHistory {
    private int chatId;
    private int userId;
    private String feature;
    private String senderType;
    private String message;
    private Timestamp createdAt;

    public ChatHistory() {
    }

    public ChatHistory(int chatId, int userId, String feature, String senderType, String message, Timestamp createdAt) {
        this.chatId = chatId;
        this.userId = userId;
        this.feature = feature;
        this.senderType = senderType;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChatHistory(int userId, String feature, String senderType, String message) {
        this.userId = userId;
        this.feature = feature;
        this.senderType = senderType;
        this.message = message;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
