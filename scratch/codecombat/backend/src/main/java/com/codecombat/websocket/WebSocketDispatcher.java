package com.codecombat.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketDispatcher {

    private static final Logger log = LoggerFactory.getLogger(WebSocketDispatcher.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketDispatcher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastMatchEvent(String matchId, WsEvent event) {
        String destination = "/topic/match." + matchId;
        log.info("Broadcasting WS event {} to {}", event.getType(), destination);
        messagingTemplate.convertAndSend(destination, event);
    }

    public void sendToUser(String username, String destination, Object payload) {
        log.info("Sending WS message to user {} at /queue/{}", username, destination);
        messagingTemplate.convertAndSendToUser(username, "/queue/" + destination, payload);
    }

    public void broadcastLobby(WsEvent event) {
        messagingTemplate.convertAndSend("/topic/lobby", event);
    }
}
