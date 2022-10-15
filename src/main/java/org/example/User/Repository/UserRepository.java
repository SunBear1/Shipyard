package org.example.User.Repository;

import org.example.DataStore.DataStore;
import org.example.Repository.Repository;
import org.example.User.User;

import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, String> {

    private DataStore store;

    public UserRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<User> find(String login) {
        return store.findUser(login);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}