package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder pwdEncoder= new BCryptPasswordEncoder();

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User createNewUser(User user){
        try{
            user.setPassword(pwdEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            return userRepository.save(user);
        }
        catch(Exception e){
            logger.error("User already exists");
            return null;
        }
    }

    public User createNewAdmin(User user){
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepository.save(user);
    }

    public void deleteUser(ObjectId id){
        userRepository.deleteById(id);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
