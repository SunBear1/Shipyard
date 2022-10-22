package org.example.User.Service;

import lombok.NoArgsConstructor;
import org.example.User.Repository.UserRepository;
import org.example.User.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {
    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(String login) {
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

    public void delete(User user) {
        repository.delete(user);
    }


}
