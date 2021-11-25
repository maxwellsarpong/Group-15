package com.mallon.demo.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // method to create a user
    User addUser(@RequestBody User user) {
        Optional<User> email = userRepository.findByEmail(user.getEmail());
        if(email.isPresent()){
            throw new IllegalStateException("User already exists");
        }
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // method to get all the users from the repository
    List<User> getUsers(){
        return userRepository.findAll();
    }


    // method to get a single user by id
    User getUserById(@PathVariable Long id){
        Optional<User> userById = userRepository.findById(id);
        if(!userById.isPresent()){
            throw new IllegalStateException("User with id" +" " + id +" "+ "does not exist");
        }
        return userRepository.findById(id).orElseThrow();
    }


    // method to update a single user
    User updateUser(@RequestBody User user, @PathVariable Long id){
        return userRepository.findById(id).map(user1 -> {
            user.setFullname(user.getFullname());
            user.setEmail(user.getEmail());
            user.setPassword(user.getPassword());
            user.setAccount(user.getAccount());
            return userRepository.save(user);
        }).orElseGet(() -> {
            user.setId(id);
            return userRepository.save(user);
        });
    }


    // method to delete a single user
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
