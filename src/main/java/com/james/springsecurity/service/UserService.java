package com.james.springsecurity.service;

import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.models.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDetails loadUserByUserNames(String userNames);
    Users addUser(UserDto userInfo);
    Users findByUserId(Long id);

    Object findByUser(Long id);
}

