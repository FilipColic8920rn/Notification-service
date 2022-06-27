package edu.rs.raf.emailservice.listener;

import edu.rs.raf.emailservice.dto.MessageDto;
import edu.rs.raf.emailservice.listener.helper.MessageHelper;
import edu.rs.raf.emailservice.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.sendMail}", concurrency = "5-10")
    public void addOrder(Message message) throws JMSException {
        MessageDto messageToSend = messageHelper.getMessage(message, MessageDto.class);
        emailService.sendMessage(messageToSend);
        //emailService.sendMessage(sendMessage.getEmail(), "Account activation", matchesDto.toString());
    }
}
