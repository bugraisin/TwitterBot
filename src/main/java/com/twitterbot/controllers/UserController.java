package com.twitterbot.controllers;

import com.twitterbot.entities.User;
import com.twitterbot.repository.UserRepository;
import com.twitterbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveOneUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@RequestParam("id") long user_id) {
        return userService.getOneUser(user_id);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") long user_id, @RequestBody User newUser) {
        return userService.updateOneUser(user_id, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") long user_id) {
        userService.deleteById(user_id);
    }
}
