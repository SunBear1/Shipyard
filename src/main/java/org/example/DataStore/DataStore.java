package org.example.DataStore;

import lombok.extern.java.Log;
import org.example.Harbor.Entity.Harbor;
import org.example.Serialization.CloningUtility;
import org.example.Ship.Entity.Ship;
import org.example.User.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Log
@ApplicationScoped
public class DataStore {

    private final Set<User> users = new HashSet<>();
    private final Set<Ship> ships = new HashSet<>();

    private final Set<Harbor> harbors = new HashSet<>();

    public synchronized List<User> findAllUsers() {
        return users.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<User> findUser(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> users.add(CloningUtility.clone(user)));

    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(CloningUtility.clone(user));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The character with id \"%s\" does not exist", user.getLogin()));
                });
    }

    public synchronized void deleteUser(String login) throws IllegalArgumentException {
        for (Ship ship : findAllShips()) {
            if (ship.getUser().getLogin().equals(login)) {
                deleteShip(ship.getId());
            }
        }

        findUser(login).ifPresentOrElse(
                original -> users.remove(original), () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist.")
                    );
                }
        );
    }

    public synchronized List<Ship> findAllShips() {
        return ships.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<Ship> findShip(Long id) {
        return ships.stream()
                .filter(ship -> ship.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createShip(Ship ship) throws IllegalArgumentException {
        ship.setId(findAllShips().stream()
                .mapToLong(Ship::getId)
                .max().orElse(0) + 1);
        ships.add(CloningUtility.clone(ship));

        Harbor harbor = ship.getHarbor();
        List<Ship> new_ships = harbor.getShips();
        new_ships.add(CloningUtility.clone(ship));
        harbor.setShips(new_ships);
        updateHarbor(harbor);

        updateShip(ship);
    }

    public synchronized void updateShip(Ship ship) throws IllegalArgumentException {
        findShip(ship.getId()).ifPresentOrElse(
                original -> {
                    ships.remove(original);
                    ships.add(CloningUtility.clone(ship));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The ship with id \"%d\" does not exist", ship.getId()));
                });
    }

    public synchronized void deleteShip(Long id) throws IllegalArgumentException {
        findShip(id).ifPresentOrElse(
                original -> ships.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The ship with id \"%d\" does not exist", id));
                });
    }

    public synchronized List<Harbor> findAllHarbors() {
        return harbors.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }


    public synchronized Optional<Harbor> findHarbor(String code) {
        return harbors.stream()
                .filter(harbor -> harbor.getCode().equals(code))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createHarbor(Harbor harbor) throws IllegalArgumentException {
        findHarbor(harbor.getCode()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The harbor domain \"%s\" is not unique", harbor.getCode()));
                },
                () -> harbors.add(CloningUtility.clone(harbor)));
    }

    public synchronized void deleteHarbor(String code) throws IllegalArgumentException {
        for (Ship ship : findAllShips()) {
            if (ship.getHarbor().getCode().equals(code)) {
                deleteShip(ship.getId());
            }
        }

        findHarbor(code).ifPresentOrElse(
                original -> harbors.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The harbor with code \"%s\" does not exist", code));
                });
    }

    public synchronized void updateHarbor(Harbor harbor) throws IllegalArgumentException {
        findHarbor(harbor.getCode()).ifPresentOrElse(
                original -> {
                    harbors.remove(original);
                    harbors.add(CloningUtility.clone(harbor));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The harbor with code \"%s\" does not exist", harbor.getCode()));
                });
    }


}
