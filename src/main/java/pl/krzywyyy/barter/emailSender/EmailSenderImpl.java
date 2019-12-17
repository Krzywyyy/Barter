package pl.krzywyyy.barter.emailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.krzywyyy.barter.users.RegistrationUser;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Service
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void createMessage(RegistrationUser user) {
        String subject = "Rejestracja - Barter WAT";
        String message = "Pomyślnie zarejestrowałeś się w aplikacji WAT'owskiego barteru.\n\n" +
                "Twoje dane logowania:\n" +
                "Email: " + user.getEmail() + "\n" +
                "Hasło: " + user.getPassword() + "\n\n" +
                "Życzymy udanych wymian studenckich!! \n" +
                "Zespół studenckiego barteru WAT";
        sendEmail(user.getEmail(), subject, message);
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail);
            helper.setTo(to);
            helper.setFrom(new InternetAddress("twoja.super.przychodnia@gmail.com"));
            helper.setSubject(subject);
            helper.setText(content);
        } catch (MessagingException e) {
            Logger.getAnonymousLogger().warning("Cannot send message to " + to);
        }
        javaMailSender.send(mail);
    }
}
