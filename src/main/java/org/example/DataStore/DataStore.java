package org.example.DataStore;

import lombok.extern.java.Log;
import org.example.Harbor.Harbor;
import org.example.Serialization.CloningUtility;
import org.example.Ship.Ship;
import org.example.User.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Log
public class DataStore {

    private Set<User> users = new HashSet<>();

    public synchronized List<User> findAllUsers(){
        return users.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<User> findUser(String login){
//        System.out.println("DATASTORE");
//        System.out.println(login);
//        System.out.println(users.stream().filter(user -> user.getName().equals(login)).findFirst().map(CloningUtility::clone));
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException{
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> users.add(CloningUtility.clone(user)));

    }

}
