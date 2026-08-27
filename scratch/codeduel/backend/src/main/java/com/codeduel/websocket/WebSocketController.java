package com.codeduel.websocket;

import com.codeduel.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    private final MatchService matchService;

    public WebSocketController(MatchService matchService) {
        this.matchService = matchService;
    }

    @MessageMapping("/match/{matchId}/typing")
    public void handleTyping(@DestinationVariable String matchId, @Payload Map<String, Object> payload) {
        if (payload != null && payload.containsKey("userId")) {
            Long userId = Long.valueOf(payload.get("userId").toString());
            String username = (String) payload.get("username");
            matchService.recordPlayerCoding(matchId, userId, username);
        }
    }

    @MessageMapping("/match/{matchId}/disconnect")
    public void handleDisconnect(@DestinationVariable String matchId, @Payload Map<String, Object> payload) {
        if (payload != null && payload.containsKey("userId")) {
            Long userId = Long.valueOf(payload.get("userId").toString());
            log.info("Client reported disconnect for user {} in match {}", userId, matchId);
            matchService.handleDisconnection(matchId, userId);
        }
    }

    @MessageMapping("/match/{matchId}/reconnect")
    public void handleReconnect(@DestinationVariable String matchId, @Payload Map<String, Object> payload) {
        if (payload != null && payload.containsKey("userId")) {
            Long userId = Long.valueOf(payload.get("userId").toString());
            log.info("Client reported reconnect for user {} in match {}", userId, matchId);
            matchService.handleReconnection(matchId, userId);
        }
    }
}
