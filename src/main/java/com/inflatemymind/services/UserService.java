package com.inflatemymind.services;

import com.inflatemymind.models.User;
import com.inflatemymind.repositories.UserRepository;
import com.inflatemymind.utility.HashSalt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByLoginAndPassword(String login, String password) {
        List<User> suitableUsers = getAllUsers().stream()
                .filter(user ->
                        user.getLogin().equals(login)
                        &&
                        user.getPassword().equals(HashSalt.hashPassword(password)))
                .collect(Collectors.toList());
        return suitableUsers.isEmpty() ? null : suitableUsers.get(0);
    }
}
