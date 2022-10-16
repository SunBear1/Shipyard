package org.example.User.Service;

import org.example.User.Repository.UserRepository;
import org.example.User.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public Optional<User> find(String login){
        return repository.find(login);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void create(User user) {
        repository.create(user);
    }

    public void updateAvatar(String login, InputStream is) {
        repository.find(login).ifPresent(user -> {
            try {
                user.setAvatar(is.readAllBytes());
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(String login) {
        repository.find(login).ifPresent(user -> {
            user.setAvatar(null);
            repository.update(user);
        });
    }


}
