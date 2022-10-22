package org.example.Harbor.Repository;

import org.example.DataStore.DataStore;
import org.example.Harbor.Harbor;
import org.example.Repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class HarborRepository implements Repository<Harbor, String> {

    private final DataStore store;

    @Inject
    public HarborRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Harbor> find(String code) {
        return store.findHarbor(code);
    }

    @Override
    public List<Harbor> findAll() {
        return store.findAllHarbors();
    }

    @Override
    public void create(Harbor entity) {
        store.createHarbor(entity);
    }

    @Override
    public void delete(Harbor entity) {
        store.deleteHarbor(entity.getCode());
    }

    @Override
    public void update(Harbor entity) {
        store.updateHarbor(entity);
    }
}
