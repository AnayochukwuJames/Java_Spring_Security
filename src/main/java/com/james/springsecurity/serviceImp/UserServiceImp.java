package com.james.springsecurity.serviceImp;

import com.james.springsecurity.dto.UserDto;
import com.james.springsecurity.enums.Role;
import com.james.springsecurity.models.Users;
import com.james.springsecurity.repository.UserRepository;
import com.james.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
@Autowired
public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUserNames(String userNames)
    throws UsernameNotFoundException{
        return (UserDetails) userRepository.findByUsername(userNames);
    }
    @Override
    public Users addUser(UserDto user) {
        return userRepository.save(new Users(user));
    }
    @Override
    public Users findByUserId(Long id) {
        return null;
    }
    @Override
    public Users findByUser(Long id) {
        return userRepository.findByRoleAndId(Role.ROLE_USER, id).orElseThrow(() -> new NullPointerException(String
                .format("No such Users with ID: %d", id)));
    }
//    @Override
//    public Users findByAdmin(Long id) {
//        return UserRepository.findByRoleId(Role.ROLE_ADMIN, id).orEsleThrow(()->new NullPointerException(String.format("No such Admin with ID:",id)));
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username);
    }
}
