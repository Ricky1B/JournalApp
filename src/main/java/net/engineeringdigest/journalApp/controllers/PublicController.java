package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.createNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
