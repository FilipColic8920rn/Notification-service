package edu.rs.raf.emailservice.dto;

import java.util.ArrayList;
import java.util.List;

public class SentEmailListDto {

    List<SentEmailDto> sentEmails = new ArrayList<>();

    public SentEmailListDto() {
    }

    public SentEmailListDto(List<SentEmailDto> sentEmails) {
        this.sentEmails = sentEmails;
    }

    public List<SentEmailDto> getSentEmails() {
        return sentEmails;
    }

    public void setSentEmails(List<SentEmailDto> sentEmails) {
        this.sentEmails = sentEmails;
    }
}
