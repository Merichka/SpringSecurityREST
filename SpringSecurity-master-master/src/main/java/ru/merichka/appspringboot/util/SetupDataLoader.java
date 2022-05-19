package ru.merichka.appspringboot.util;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.merichka.appspringboot.entity.Role;
import ru.merichka.appspringboot.entity.User;
import ru.merichka.appspringboot.repository.RoleRepository;
import ru.merichka.appspringboot.repository.UserRepository;
import ru.merichka.appspringboot.service.UserServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@Component
@AllArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        final Role userRole = createRoleIfNotFound("ROLE_USER");

        createUserIfNotFound(
                "Meriem",
                "Nikitenko",
                (byte) 1,
                "nik-meri@yandex.ru",
                "123",
                new HashSet<>(Collections.singletonList(roleRepository.findFirstByName("ROLE_ADMIN"))));

        createUserIfNotFound(
                "Ivan",
                "Ivanov",
                (byte) 2,
                "w@w.w",
                "w",
                new HashSet<>(Collections.singletonList(roleRepository.findFirstByName("ROLE_USER"))));

        createUserIfNotFound(
                "Petr",
                "Petrov",
                (byte) 2,
                "q@q.q",
                "q",
                new HashSet<>(Collections.singletonList(roleRepository.findFirstByName("ROLE_USER"))));
    }

    protected Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findFirstByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    protected void createUserIfNotFound(String firstName, String lastName, Byte age, String email, String password, Collection<Role> roles) {
        User user = userRepository.findFirstByFirstName(firstName);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            user.setPassword(password);
            user.setEmail(email);
        }
        Objects.requireNonNull(user).setRoles(roles);
        userService.save(user);
    }
}
