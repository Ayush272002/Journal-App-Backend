package me.ayush272002.journalApp.controller;

import lombok.RequiredArgsConstructor;
import me.ayush272002.journalApp.entity.JournalEntry;
import me.ayush272002.journalApp.entity.User;
import me.ayush272002.journalApp.repository.UserRepository;
import me.ayush272002.journalApp.service.JournalEntryService;
import me.ayush272002.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByUserName(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, Authentication authentication){
        try{
            User user = getAuthenticatedUser(authentication);
            String userName = user.getUserName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId, Authentication authentication){
        User user = getAuthenticatedUser(authentication);
        String userName = user.getUserName();
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, Authentication authentication){
        User user = getAuthenticatedUser(authentication);
        String userName = user.getUserName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if(removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, Authentication authentication){
        User user = getAuthenticatedUser(authentication);
        String userName = user.getUserName();
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

            if(journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(old.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
