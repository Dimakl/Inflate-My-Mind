package com.inflatemymind.services;

import com.inflatemymind.models.Email;
import com.inflatemymind.models.User;
import com.inflatemymind.repositories.EmailRepository;
import com.inflatemymind.utility.MailgunVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    public boolean registerOrDeclineUserEmail(User user, String email) {
        List<Email> list = emailRepository.findAll();
        for (Email listEmail : list) {
            if (listEmail.getEmail().equals(email)) {
                return false;
            }
        }
        Email newEmail = emailRepository.save(new Email(false, email));
        MailgunVerification.verifyEmail(user, newEmail);
        return true;
    }

    public boolean isEmailVerified(User user) {
        List<Email> list = emailRepository.findAll();
        for (Email listEmail : list) {
            if (listEmail.getEmail().equals(user.getEmail())) {
                return listEmail.getIsValidated();
            }
        }
        return false;
    }

    public boolean verifyEmail(String email, Long code) {
        List<Email> list = emailRepository.findAll();
        for (Email listEmail : list) {
            if (listEmail.getEmail().equals(email)) {
                boolean v =  code == listEmail.getId()*587 + 2953;
                if (v) {
                    listEmail.setIsValidated(true);
                }
                return v;
            }
        }
        return false;
    }

    public List<Email> getAll() {
        return emailRepository.findAll();
    }

}
