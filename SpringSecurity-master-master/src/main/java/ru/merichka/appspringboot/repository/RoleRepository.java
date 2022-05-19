package ru.merichka.appspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.merichka.appspringboot.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findFirstByName(String name);

    List<Role> findAll();
}