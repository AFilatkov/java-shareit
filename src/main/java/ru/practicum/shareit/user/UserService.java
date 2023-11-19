package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public User getUser(Integer userId) throws NotFound {
        return userRepository.get(userId);
    }

    public User addUser(User user) {
        return userRepository.add(user);
    }

    public User updateUser(Integer userId, User user) throws NotFound {
        return userRepository.update(userId, user);
    }

    public void deleteUser(Integer userId) throws NotFound {
        userRepository.delete(userId);
    }

    private void validateUser(User user) throws BadDataRequest {
        if (user.getName() == null) {
            throw new BadDataRequest("Не указано имя");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            throw new BadDataRequest("Имя не может быть пустым");
        }

        if (user.getEmail() == null)
            throw new BadDataRequest("Email не может быть пустым");
    }
}
