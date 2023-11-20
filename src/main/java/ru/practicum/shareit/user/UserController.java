package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) throws NotFound {
        return userService.getUser(userId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public User updateUSer(@PathVariable Integer userId, @RequestBody User user) throws NotFound {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) throws NotFound {
        userService.deleteUser(userId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final NotFound e) {
        return Map.of("errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badData(final BadDataRequest e) {
        return Map.of("errorMessage", e.getMessage());
    }
}
