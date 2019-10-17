package pl.krzywyyy.barter.service;

import pl.krzywyyy.barter.exception.ObjectNotExistsException;

public interface CrudInterface<T> {
    Iterable<T> findAll();

    T find(int id) throws ObjectNotExistsException;

    void delete(int id) throws ObjectNotExistsException;

    T save(T object);
}
