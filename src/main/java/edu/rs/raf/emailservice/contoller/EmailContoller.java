package edu.rs.raf.emailservice.contoller;

import edu.rs.raf.emailservice.dto.EmailNotificationDto;
import edu.rs.raf.emailservice.dto.EmailNotificationListDto;
import edu.rs.raf.emailservice.dto.SentEmailListDto;
import edu.rs.raf.emailservice.security.CheckSecurity;
import edu.rs.raf.emailservice.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailContoller {

    private EmailService emailService;

    public EmailContoller(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/allSentAdmin")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<SentEmailListDto> getAllSentEmailsAdmin(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<SentEmailListDto>(emailService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/allSent/{email}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<SentEmailListDto> getAllSentEmails(@RequestHeader("Authorization") String authorization,@PathVariable("email") String email) {
        return new ResponseEntity<SentEmailListDto>(emailService.findAllByEmail(email), HttpStatus.OK);
    }
    @GetMapping("/filter/{email}/{type}/{date}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_CLIENT"})
    public ResponseEntity<SentEmailListDto> getAllEmailsFiltered(@RequestHeader("Authorization") String authorization,@PathVariable("email") String email
                                                                ,@PathVariable("type") String type, @PathVariable("date") String date) {
        return new ResponseEntity<SentEmailListDto>(emailService.filterEmails(email, type, date), HttpStatus.OK);
    }
    @GetMapping("/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<EmailNotificationListDto> getAllNotificationtypes(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<EmailNotificationListDto>(emailService.getAllNotificationTypes(),HttpStatus.OK);
    }
    @PostMapping("/type")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> changeNotificationType(@RequestHeader("Authorization") String authorization, @RequestBody @Valid EmailNotificationDto dto){
        emailService.editNorificationType(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/type/{typeId}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteNotificationType(@RequestHeader("Authorization") String authorization, @PathVariable("typeId") Long id){
        emailService.deleteType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
