package me.ayush272002.journalApp.controller;

import lombok.RequiredArgsConstructor;
import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.repository.UserRepository;
import me.ayush272002.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByUserName(authentication.getName());
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        System.out.println(user.getUserName());
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user) {
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
