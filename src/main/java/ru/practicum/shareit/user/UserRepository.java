package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserRepository {
    User add(User user);

    Collection<User> getAll();

    User update(Integer id, User user) throws NotFound;

    User get(Integer userId) throws IllegalArgumentException, NotFound;

    void delete(Integer userId) throws NotFound;
}
