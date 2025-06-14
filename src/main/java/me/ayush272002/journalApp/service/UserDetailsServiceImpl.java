package me.ayush272002.journalApp.service;

import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if(user != null) {
//            System.out.println("Found user: " + user.getUserName());
//            System.out.println("Password hash: " + user.getPassword());
//            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getUserName())
//                    .password(user.getPassword())
//                    .roles(user.getRoles().toArray(new String[0]))
//                    .build();
//
//            System.out.println("Created UserDetails with username: " + userDetails.getUsername());
//            System.out.println("Created UserDetails with encoded password: " + userDetails.getPassword());
//
//            return userDetails;

//            return new org.springframework.security.core.userdetails.User(
//                    user.getUserName(), user.getPassword(), List.of()
//            );

            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    authorities
            );
        }

        throw new UsernameNotFoundException("User not found with username" + username);
    }
}
