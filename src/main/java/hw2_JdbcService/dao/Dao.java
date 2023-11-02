package hw2_JdbcService.dao;

import hw2_JdbcService.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E> {
    boolean delete(K id);

    E save(Ticket ticket);

    void update(E ticket);

    Optional<E> findById(K id);

    List<E> findAll();

}
