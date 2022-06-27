package edu.rs.raf.emailservice.service;

import edu.rs.raf.emailservice.domain.EmailNotification;
import edu.rs.raf.emailservice.domain.SentEmail;
import edu.rs.raf.emailservice.dto.EmailNotificationDto;
import edu.rs.raf.emailservice.dto.EmailNotificationListDto;
import edu.rs.raf.emailservice.dto.MessageDto;
import edu.rs.raf.emailservice.dto.SentEmailListDto;
import edu.rs.raf.emailservice.emailmapper.EmailMapper;
import edu.rs.raf.emailservice.exception.NotFoundException;
import edu.rs.raf.emailservice.repository.NotificationRepository;
import edu.rs.raf.emailservice.repository.SentEmailRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    private JavaMailSender mailSender;
    private NotificationRepository notificationRepository;
    private SentEmailRepository sentEmailRepository;
    private EmailMapper emailMapper;

    public EmailService(JavaMailSender mailSender, NotificationRepository notificationRepository, SentEmailRepository sentEmailRepository,
                        EmailMapper emailMapper) {
        this.mailSender = mailSender;
        this.notificationRepository = notificationRepository;
        this.sentEmailRepository = sentEmailRepository;
        this.emailMapper = emailMapper;
    }

    public void sendMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    public void sendMessage(MessageDto messageDto){
        EmailNotification en = notificationRepository.findEmailNotificationByType(messageDto.getType()).orElseThrow(() -> new NotFoundException(String
                .format("Notification type not found.")));
        SimpleMailMessage message = new SimpleMailMessage();
        String text = en.getText();
        text = text.replaceAll("%name", messageDto.getName());
        text = text.replaceAll("%lastname", messageDto.getLastname());
        text = text.replaceAll("%hotel", messageDto.getHotelName());
        text = text.replaceAll("%link", messageDto.getLink());
        text = text.replaceAll("%rezStart", messageDto.getRezStart());

        message.setTo(messageDto.getEmail());
        message.setSubject(en.getType());
        message.setText(text);
        mailSender.send(message);
        sentEmailRepository.save(new SentEmail(messageDto.getEmail(), text, en.getType(), Date.valueOf(LocalDate.now())));
        System.out.println("salje se mail!!!!");
    }

    public SentEmailListDto findAll(){
        SentEmailListDto dto = new SentEmailListDto();
        List<SentEmail> all = sentEmailRepository.findAll();
        for(SentEmail s: all){
            dto.getSentEmails().add(emailMapper.entityToDto(s));
        }
        return dto;
    }

    public SentEmailListDto findAllByEmail(String email){
        List<SentEmail> all = sentEmailRepository.findAllByEmail(email);
        SentEmailListDto dto = new SentEmailListDto();
        for(SentEmail s : all){
            if(s.getEmail().equals(email))
                dto.getSentEmails().add(emailMapper.entityToDto(s));
        }
        return dto;
    }

    public SentEmailListDto filterEmails(String email, String type, String date){
        SentEmailListDto dto = new SentEmailListDto();
        System.out.println(email + type + date);
        List<SentEmail> all = sentEmailRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        all.forEach(sentEmail -> {
            boolean add = true;
            if(!email.equals("-1") && !sentEmail.getEmail().equals(email))
                add = false;
            if(!type.equals("-1") && !sentEmail.getType().equals(type))
                add = false;
            if(!date.equals("-1") && !LocalDate.parse(sentEmail.getDateSent().toString(), formatter).isAfter(LocalDate.parse(date, formatter)))
                add = false;

            if(add)
                dto.getSentEmails().add(emailMapper.entityToDto(sentEmail));
        });

        return dto;
    }

    public void editNorificationType(EmailNotificationDto dto){
        EmailNotification en = notificationRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(String
                .format("Notification type with id: %d not found.", dto.getId())));
        en.setText(dto.getText());
        notificationRepository.save(en);
    }


    public void deleteType(Long id) {
        EmailNotification en = notificationRepository.findById(id).orElseThrow(() -> new NotFoundException(String
                .format("Notification type with id: %d not found.", id)));
        notificationRepository.deleteById(en.getId());
    }

    public EmailNotificationListDto getAllNotificationTypes() {

        EmailNotificationListDto dto = new EmailNotificationListDto();
        List<EmailNotification> all = notificationRepository.findAll();
        all.forEach(en->{
            dto.getNotifs().add(emailMapper.entityToDtoType(en));
        });
        return dto;
    }
}
