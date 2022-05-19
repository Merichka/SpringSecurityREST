package ru.merichka.appspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.merichka.appspringboot.entity.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String email);
}