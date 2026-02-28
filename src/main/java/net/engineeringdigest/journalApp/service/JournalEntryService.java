package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void createJournalEntry(String username,JournalEntry journalEntry){
        User user= userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntryList().add(saved);
        userService.createUser(user);
    }

    public void updateJournalEntry(JournalEntry entry){
        journalEntryRepository.save(entry);
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(User user,ObjectId id){
        user.getJournalEntryList().removeIf(x-> x.getId().equals(id));
        journalEntryRepository.deleteById(id);
        userService.createUser(user);
    }


}
