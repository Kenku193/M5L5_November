package org.example.repository;

public interface Repo<E> {
    E getById(Long id);

    void update(E entity);
}
