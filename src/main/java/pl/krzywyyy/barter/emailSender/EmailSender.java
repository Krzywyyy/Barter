package pl.krzywyyy.barter.emailSender;

import pl.krzywyyy.barter.users.RegistrationUser;

public interface EmailSender {
    void createMessage(RegistrationUser user);
}
