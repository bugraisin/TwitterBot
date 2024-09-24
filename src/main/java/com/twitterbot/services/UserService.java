package com.twitterbot.services;

import com.twitterbot.entities.User;
import com.twitterbot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User user) {
        return userRepository.save(user);
    }

    public User getOneUser(long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public User updateOneUser(long user_id, User newUser) {
        Optional<User> userOptional = userRepository.findById(user_id);
        if(userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setUserName(newUser.getUserName());
            userToUpdate.setPassword(newUser.getPassword());
            userRepository.save(userToUpdate);
            return userToUpdate;
        }
        return null;
    }

    public void deleteById(long user_id) {
        userRepository.deleteById(user_id);
    }
}
