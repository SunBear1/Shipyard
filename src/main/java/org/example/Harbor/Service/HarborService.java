package org.example.Harbor.Service;

import lombok.NoArgsConstructor;
import org.example.Harbor.Entity.Harbor;
import org.example.Harbor.Repository.HarborRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class HarborService {
    private HarborRepository repository;

    @Inject
    public HarborService(HarborRepository repository) {
        this.repository = repository;
    }

    public Optional<Harbor> find(String code) {
        return repository.find(code);
    }

    public List<Harbor> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Harbor harbor) {
        repository.create(harbor);
    }

    @Transactional
    public void delete(String code) {
        repository.delete(repository.find(code).orElseThrow());
    }

    @Transactional
    public void update(Harbor harbor) {
        repository.update(harbor);
    }
}
