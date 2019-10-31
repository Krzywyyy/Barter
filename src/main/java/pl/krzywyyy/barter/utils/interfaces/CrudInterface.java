package pl.krzywyyy.barter.utils.interfaces;

import pl.krzywyyy.barter.utils.exceptions.ObjectNotExistsException;

public interface CrudInterface<T> {
    Iterable<T> findAll(int page);

    T find(int id) throws ObjectNotExistsException;

    void delete(int id) throws ObjectNotExistsException;

    T save(T object);
}
