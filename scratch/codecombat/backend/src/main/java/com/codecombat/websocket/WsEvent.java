package com.codecombat.websocket;

import java.time.LocalDateTime;

public class WsEvent {
    private WsEventType type;
    private String matchId;
    private Long senderId;
    private String senderUsername;
    private Object data;
    private LocalDateTime timestamp;

    public WsEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public WsEvent(WsEventType type, String matchId, Long senderId, String senderUsername, Object data) {
        this.type = type;
        this.matchId = matchId;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static WsEvent of(WsEventType type, String matchId, Object data) {
        return new WsEvent(type, matchId, null, null, data);
    }

    public static WsEvent of(WsEventType type, String matchId, Long senderId, String senderUsername, Object data) {
        return new WsEvent(type, matchId, senderId, senderUsername, data);
    }

    public WsEventType getType() { return type; }
    public void setType(WsEventType type) { this.type = type; }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
