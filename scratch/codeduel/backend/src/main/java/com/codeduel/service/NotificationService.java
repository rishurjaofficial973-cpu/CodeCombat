package com.codeduel.service;

import com.codeduel.model.Notification;
import com.codeduel.model.NotificationType;
import com.codeduel.model.User;
import com.codeduel.repository.NotificationRepository;
import com.codeduel.repository.UserRepository;
import com.codeduel.websocket.WebSocketDispatcher;
import com.codeduel.websocket.WsEvent;
import com.codeduel.websocket.WsEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final WebSocketDispatcher wsDispatcher;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository,
                               WebSocketDispatcher wsDispatcher) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.wsDispatcher = wsDispatcher;
    }

    @Transactional
    public Notification createNotification(Long userId, String title, String message, NotificationType type, String link) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        Notification notif = new Notification(user, title, message, type, link);
        notif = notificationRepository.save(notif);

        // Send via WebSocket
        wsDispatcher.sendToUser(user.getUsername(), "notifications", WsEvent.of(WsEventType.NOTIFICATION, null, notif));
        return notif;
    }

    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(Long userId, int limit) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, limit));
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> list = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, 100));
        list.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(list);
    }
}
