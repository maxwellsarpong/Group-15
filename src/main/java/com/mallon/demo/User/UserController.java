package com.mallon.demo.User;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user/")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // rest api to get all the users
    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

}
