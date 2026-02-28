package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAll(){
        List<User> list = userService.getAllUsers();
        if(list!=null || list.isEmpty()) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-new-admin")
    public ResponseEntity<?> getAll(@RequestBody User user){
        userService.createNewAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
