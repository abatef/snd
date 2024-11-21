package com.snd.snxdbackend.dtos;

import com.snd.snxdbackend.enums.NotificationStatus;
import com.snd.snxdbackend.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private NotificationType type;
    private NotificationStatus status;
    private String message;
}
