package com.mallon.demo.User;


import com.mallon.demo.Auth.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // rest api to get all the users
    @GetMapping("/user")
    List<User> getUsers(){
        return userService.getUsers();
    }

    // rest api to get a user by id
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    // rest api to add a user
    @PostMapping("/user")
    User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    // rest api to update an instance of a user
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User user, @PathVariable Long id){
        return userService.updateUser(user, id);
    }

    // rest api to delete a user
    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


    //rest to log in
    @PostMapping("/user/login")
    ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception{
        System.out.println(userService.createAuthToken(authRequest));
        return userService.createAuthToken(authRequest);
    }

}
