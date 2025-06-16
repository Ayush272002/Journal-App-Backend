package me.ayush272002.journalApp.controller;

import lombok.AllArgsConstructor;
import me.ayush272002.journalApp.cache.AppCache;
import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.repository.UserRepository;
import me.ayush272002.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private UserService userService;
    private UserRepository userRepository;
    private AppCache appCache;

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

    @GetMapping("clear-app-cache")
    public ResponseEntity<?> clearAppCache(){
        appCache.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
