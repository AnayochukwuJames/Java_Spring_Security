package com.james.springsecurity.controller;

import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;

@Controller
public class SignUpController {
    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup/user")
    public ModelAndView signUp() {
        return new ModelAndView("signup").addObject("user", new UserDto());
    }

    @PostMapping("/signup/user")
    public String signUp(@ModelAttribute UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "home";

        }
        try {
            userDto.setRole("ROLE_USER");
            userService.addUser(userDto);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        return "/hello";
    }

}
