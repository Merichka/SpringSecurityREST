package ru.merichka.appspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.merichka.appspringboot.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findFirstById(Long id);

    User findFirstByFirstName(String firstName);

    User findFirstByEmail(String email);

}