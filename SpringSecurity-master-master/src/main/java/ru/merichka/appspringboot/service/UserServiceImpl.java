package ru.merichka.appspringboot.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.merichka.appspringboot.entity.User;
import ru.merichka.appspringboot.repository.RoleRepository;
import ru.merichka.appspringboot.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user) {
        if (!user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {

            user.setPassword(userRepository.getById(user.getId()).getPassword());
        }
        userRepository.save(user);
    }

}
