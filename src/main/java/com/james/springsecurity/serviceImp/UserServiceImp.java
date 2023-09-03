package com.james.springsecurity.serviceImp;

import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.enums.Role;
import com.james.springsecurity.models.User;
import com.james.springsecurity.repository.UserRepository;
import com.james.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
@Autowired
public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUserNames(String userNames)
    throws UsernameNotFoundException{
        return (UserDetails) userRepository.findByUserName(userNames);
    }
    @Override
    public User addUser(UserDto user) {
        return userRepository.save(new User(user));
    }
    @Override
    public User findByUserId(Long id) {
        return null;
    }
    @Override
    public User findByUser(Long id) {
        return userRepository.findByRoleAndId(Role.ROLE_USER, id).orElseThrow(() -> new NullPointerException(String
                .format("No such User with ID: %d", id)));
    }
//    @Override
//    public User findByAdmin(Long id) {
//        return UserRepository.findByRoleId(Role.ROLE_ADMIN, id).orEsleThrow(()->new NullPointerException(String.format("No such Admin with ID:",id)));
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUserName(username);
    }
}
