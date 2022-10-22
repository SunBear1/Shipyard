package org.example.Ship.Repository;

import org.example.DataStore.DataStore;
import org.example.Harbor.Harbor;
import org.example.Repository.Repository;
import org.example.Serialization.CloningUtility;
import org.example.Ship.Ship;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class ShipRepository implements Repository<Ship, Long> {

    private final DataStore store;

    @Inject
    public ShipRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Ship> find(Long id) {
        return store.findShip(id);
    }

    @Override
    public List<Ship> findAll() {
        return store.findAllShips();
    }

    @Override
    public void create(Ship entity) {
        store.createShip(entity);
    }

    @Override
    public void delete(Ship entity) {
        store.deleteShip(entity.getId());
    }

    @Override
    public void update(Ship entity) {
        store.updateShip(entity);
    }

    public Optional<Ship> findByIdAndHarbor(Long id, Harbor harbor) {
        return store.findAllShips().stream()
                .filter(ship -> ship.getHarbor().equals(harbor))
                .filter(ship -> ship.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public List<Ship> findAllByHarbor(Harbor harbor) {
        return store.findAllShips().stream()
                .filter(ship -> ship.getUser().equals(harbor))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

}
