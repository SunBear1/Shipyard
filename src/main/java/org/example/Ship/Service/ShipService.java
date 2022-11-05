package org.example.Ship.Service;

import lombok.NoArgsConstructor;
import org.example.Harbor.Repository.HarborRepository;
import org.example.Ship.Entity.Ship;
import org.example.Ship.Repository.ShipRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class ShipService {
    private ShipRepository shipRepository;
    private HarborRepository harborRepository;

    @Inject
    public ShipService(ShipRepository shipRepository, HarborRepository harborRepository) {
        this.shipRepository = shipRepository;
        this.harborRepository = harborRepository;
    }

    public Optional<Ship> find(Long id) {
        return shipRepository.find(id);
    }

    public List<Ship> findAll() {
        return shipRepository.findAll();
    }

    public void create(Ship ship) {
        shipRepository.create(ship);
    }

    public void delete(Long id) {
        shipRepository.delete(shipRepository.find(id).orElseThrow());
    }

    public void update(Ship ship) {
        shipRepository.update(ship);
    }

    public List<Ship> findAllForHarbor(String code) {
        return shipRepository.findAllByHarbor(harborRepository.find(code).orElseThrow());
    }

    public Optional<Ship> findForHarbor(Long id, String code) {
        return shipRepository.findByIdAndHarbor(id, harborRepository.find(code));
    }


}
