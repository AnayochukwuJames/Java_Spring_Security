package com.james.springsecurity.repository;

import com.james.springsecurity.enums.Role;
import com.james.springsecurity.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String userName);

//    @Override
    Optional<Users> findByRoleAndId(Role roleUser, Long id);
}
