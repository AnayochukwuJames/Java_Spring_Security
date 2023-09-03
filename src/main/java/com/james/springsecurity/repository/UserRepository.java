package com.james.springsecurity.repository;

import com.james.springsecurity.enums.Role;
import com.james.springsecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

//    @Override
    Optional<User> findByRoleAndId(Role roleUser, Long id);
}
