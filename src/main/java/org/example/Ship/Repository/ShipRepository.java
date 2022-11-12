package org.example.Ship.Repository;

import org.example.Harbor.Entity.Harbor;
import org.example.Repository.Repository;
import org.example.Ship.Entity.Ship;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ShipRepository implements Repository<Ship, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Ship> find(Long id) {
        return Optional.ofNullable(em.find(Ship.class, id));
    }

    @Override
    public List<Ship> findAll() {
        return em.createQuery("select c from Ship c", Ship.class).getResultList();
    }

    @Override
    public void create(Ship entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Ship entity) {
        em.remove(em.find(Ship.class, entity.getId()));
    }

    @Override
    public void update(Ship entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Ship entity) {
        em.detach(entity);
    }

    public Optional<Ship> findByIdAndHarbor(Long id, Optional<Harbor> harbor) {
        try {
            return Optional.of(em.createQuery("select c from Ships c where c.id = :id and c.harbor = :harbor", Ship.class)
                    .setParameter("harbor", harbor)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    public List<Ship> findAllByHarbor(Harbor harbor) {
        return em.createQuery("select c from Ships c where c.harbor = :harbor", Ship.class)
                .setParameter("harbor", harbor)
                .getResultList();
    }

}
