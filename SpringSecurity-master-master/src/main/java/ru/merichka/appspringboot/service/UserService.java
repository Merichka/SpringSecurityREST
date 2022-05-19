package ru.merichka.appspringboot.service;

import ru.merichka.appspringboot.entity.User;

public interface UserService {
    void save(User user);

    void update(User user);
}
