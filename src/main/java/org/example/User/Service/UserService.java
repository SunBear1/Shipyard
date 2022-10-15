package org.example.User.Service;

import org.example.User.Repository.UserRepository;
import org.example.User.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public Optional<User> find(String login){
        return repository.find(login);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void create(User user) {
        repository.create(user);
    }

}
