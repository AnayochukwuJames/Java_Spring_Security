package com.james.springsecurity.service;

import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDetails loadUserByUserNames(String userNames);
    User addUser(UserDto userInfo);
    User findByUserId(Long id);

    Object findByUser(Long id);
}

