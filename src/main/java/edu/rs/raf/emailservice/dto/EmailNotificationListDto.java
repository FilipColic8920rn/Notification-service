package edu.rs.raf.emailservice.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailNotificationListDto {

    List<EmailNotificationDto> notifs = new ArrayList<>();

    public EmailNotificationListDto(List<EmailNotificationDto> notifs) {
        this.notifs = notifs;
    }

    public EmailNotificationListDto() {
    }

    public List<EmailNotificationDto> getNotifs() {
        return notifs;
    }

    public void setNotifs(List<EmailNotificationDto> notifs) {
        this.notifs = notifs;
    }
}
