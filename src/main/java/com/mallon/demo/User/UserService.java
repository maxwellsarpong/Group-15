package com.mallon.demo.User;
import com.mallon.demo.Auth.AuthRequest;
import com.mallon.demo.Auth.AuthResponse;
import com.mallon.demo.Auth.JwtUtil;
import com.mallon.demo.Auth.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
            user.setUsername(user.getUsername());
            user.setPassword(user.getPassword());
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



    // method to login
    ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
