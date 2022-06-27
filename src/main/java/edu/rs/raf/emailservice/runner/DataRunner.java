package edu.rs.raf.emailservice.runner;

import edu.rs.raf.emailservice.domain.EmailNotification;
import edu.rs.raf.emailservice.repository.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private NotificationRepository notificationRepository;

    public DataRunner(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        EmailNotification en1 = new EmailNotification();
        en1.setType("account_activation");
        en1.setText("Hello %name %lastname, click the link to activate your account: %link!");
        notificationRepository.save(en1);
        EmailNotification en2 = new EmailNotification();
        en2.setType("change_password");
        en2.setText("Hello %name %lastname, your token is: %link!");
        notificationRepository.save(en2);
        EmailNotification en3 = new EmailNotification();
        en3.setType("reservation_successful");
        en3.setText("Hello %name %lastname, a reservation in %hotel was a success!");
        notificationRepository.save(en3);
        EmailNotification en4 = new EmailNotification();
        en4.setType("reservation_cancellation");
        en4.setText("Hello %name %lastname, a reservation in %hotel was canceled successfully!");
        notificationRepository.save(en4);
        EmailNotification en5 = new EmailNotification();
        en5.setType("reservation_reminder");
        en5.setText("Hello %name %lastname, your reservation in %hotel is on %rezStart!");
        notificationRepository.save(en5);
    }
}
