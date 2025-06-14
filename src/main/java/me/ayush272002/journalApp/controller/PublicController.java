package me.ayush272002.journalApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.service.UserService;
import me.ayush272002.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private void setJwtCookie(HttpServletResponse response, String email) {
        String jwt = jwtUtil.generateToken(email);
        System.out.println("jwt -> " + jwt);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("None")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUSer(@RequestBody User user, HttpServletResponse response) {
        boolean created = userService.saveNewUser(user);
        setJwtCookie(response, user.getUserName());
        return created ? ResponseEntity.status(HttpStatus.CREATED).body("User Created") : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin (@RequestBody User user, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        try{
            Authentication auth = authenticationManager.authenticate(token);
//            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//            securityContext.setAuthentication(auth);
//            SecurityContextHolder.setContext(securityContext);
//            request.getSession(true).setAttribute(
//                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                    securityContext
//            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            setJwtCookie(response, user.getUserName());
            return ResponseEntity.ok("Login successful");
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
