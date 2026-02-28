package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping()
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getbyId(@PathVariable ObjectId userId){
        Optional<User> optionalUser= userService.getUserById(userId);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        User old=userService.findByUsername(username);
        if(old!=null){
            if(old.getPassword()==null||!old.getPassword().equals(newUser.getPassword())) old.setPassword(newUser.getPassword());
        }

        userService.createNewUser(old);
        return new ResponseEntity<>(old,HttpStatus.OK);
    }
}
