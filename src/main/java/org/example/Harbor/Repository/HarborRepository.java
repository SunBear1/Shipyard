package org.example.Harbor.Repository;

import org.example.Harbor.Entity.Harbor;
import org.example.Repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class HarborRepository implements Repository<Harbor, String> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Harbor> find(String code) {
        return Optional.ofNullable(em.find(Harbor.class, code));
    }

    @Override
    public List<Harbor> findAll() {
        return em.createQuery("select p from Harbor p", Harbor.class).getResultList();
    }

    @Override
    public void create(Harbor entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Harbor entity) {
        em.remove(em.find(Harbor.class, entity.getCode()));
    }

    @Override
    public void update(Harbor entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Harbor entity) {
        em.detach(entity);
    }

}
