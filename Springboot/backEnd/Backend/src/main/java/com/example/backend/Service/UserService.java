package com.example.backend.Service;

import com.example.backend.Entities.User;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface UserService {


        User register(User user) throws MessagingException, IOException, WriterException;
        String login(String email, String password);
        void verifyUser(String token);

}
