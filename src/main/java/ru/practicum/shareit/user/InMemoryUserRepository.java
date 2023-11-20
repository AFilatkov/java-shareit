package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer counter = 0;

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User add(User user) {
        if (isEmailExist(user)) {
            throw new IllegalArgumentException("Email существует");
        } else {
            users.put(++counter, user);
            user.setId(counter);
            return user;
        }
    }

    @Override
    public User update(Integer id, User user) throws NotFound {
        if (users.containsKey(id)) {
            User toUpdate = users.remove(id);
            if (user.getName() != null) {
                toUpdate.setName(user.getName());
            }
            if (user.getEmail() != null) {
                if (!isEmailExist(user)) {
                    toUpdate.setEmail(user.getEmail());
                } else {
                    users.put(toUpdate.getId(), toUpdate);
                    throw new IllegalArgumentException("Укаазный email существует");
                }
            }
            users.put(toUpdate.getId(), toUpdate);
            return toUpdate;
        } else {
            throw new NotFound("Указан неправильный идентификатор пользователя");
        }
    }

    @Override
    public User get(Integer userId) throws NotFound {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new NotFound("Указан неправильный идентификатор пользователя");
        }
    }

    @Override
    public void delete(Integer userId) throws NotFound {
        if (users.containsKey(userId)) {
            users.remove(userId);
        } else {
            throw new NotFound("Указан неправильный идентификатор пользователя");
        }
    }

    private boolean isEmailExist(User user) {
        return users.values().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()));
    }
}
