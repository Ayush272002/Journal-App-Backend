package me.ayush272002.journalApp.controller;

import lombok.AllArgsConstructor;
import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.repository.UserRepository;
import me.ayush272002.journalApp.service.UserService;
import me.ayush272002.journalApp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private UserRepository userRepository;

    private WeatherService weatherService;

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByUserName(authentication.getName());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
        User oldUser = getAuthenticatedUser(authentication);
        String userName = oldUser.getUserName();
        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        boolean created = userService.saveNewUser(userInDB);

        return created ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT) ;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        String userName = user.getUserName();
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        String userName = user.getUserName();
        int feelsLike = weatherService.getWeather("Birmingham").getCurrent().getFeelslike();
        String message = "hi " + userName + ", weather feels like " + feelsLike + "°C";
        return new ResponseEntity<>(message , HttpStatus.OK);
    }
}
