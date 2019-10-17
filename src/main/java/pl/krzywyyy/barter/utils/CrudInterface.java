package pl.krzywyyy.barter.utils;

import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;

public interface CrudInterface<T> {
    Iterable<T> findAll();

    T find(int id) throws ObjectNotExistsException;

    void delete(int id) throws ObjectNotExistsException;

    T save(T object);
}
