package com.james.springsecurity.controller;

import com.james.springsecurity.apis.AuthRequest;
import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.models.User;
import com.james.springsecurity.repository.UserRepository;
import com.james.springsecurity.serviceImp.UserServiceImp;
import com.james.springsecurity.utils.JwtUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class HomeController {
    private UserServiceImp userService;
    private final UserRepository userRepository;
    private final JwtUtils jwtService;
    private final AuthenticationManager authenticationManager;

    public HomeController(UserServiceImp userService, UserRepository userRepository, JwtUtils jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @GetMapping ("/Welcome")
    public ResponseEntity<String> Welcome(){
        return new ResponseEntity<>("welcome this endpoint is not secure", HttpStatusCode.valueOf(200));
    }
    @PostMapping("/addNewUser")
    public  ResponseEntity<User> addNewUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatusCode.valueOf(200));
    }
    @GetMapping("/user/userProfile/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @SecurityRequirement(name="Bearer Authentication")
    public ResponseEntity<User> userProfile(@PathVariable Long id){
        return new ResponseEntity<>(userService.findByUser(id), HttpStatusCode.valueOf(200));
    }
    @GetMapping("/generateToken")
    public ResponseEntity<String> authenticationAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return new ResponseEntity<>(jwtService.generateToken(userService.loadUserByUserNames(authRequest.getUserName())),
                    HttpStatusCode.valueOf(200));
        }else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }


}
