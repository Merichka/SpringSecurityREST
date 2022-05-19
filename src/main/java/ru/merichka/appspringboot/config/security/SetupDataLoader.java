package ru.merichka.appspringboot.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.merichka.appspringboot.entity.Role;
import ru.merichka.appspringboot.entity.User;
import ru.merichka.appspringboot.repository.RoleRepository;
import ru.merichka.appspringboot.repository.UserRepository;

import java.util.HashSet;

@Configuration
public class SetupDataLoader {

    private static final Logger log = LoggerFactory.getLogger(SetupDataLoader.class);

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");

            log.info("Preloading " + roleRepository.save(roleAdmin));
            log.info("Preloading " + roleRepository.save(roleUser));

            log.info("Preloading " + userRepository.save(new User("Meriem", "Nikitenko", 38, "nik-meri@yandex.ru",
                    passwordEncoder.encode("admin"),
                    new HashSet<>() {{
                        add(roleAdmin);
                        add(roleUser);
                    }})));
            log.info("Preloading " + userRepository.save(new User("Ivan", "Ivanov", 46, "i@mail.com",
                    passwordEncoder.encode("user"),
                    new HashSet<>() {{
                        add(roleUser);
                    }})));
            log.info("Preloading " + userRepository.save(new User("Petr", "Petrov", 20, "p@mail.com",
                    passwordEncoder.encode("user"),
                    new HashSet<>() {{
                        add(roleUser);
                    }})));
        };
    }
}
