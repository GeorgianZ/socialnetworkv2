package repository;

import domain.Entity;

import java.io.IOException;

public interface Repository<ID, E extends Entity<ID>> {

    int size();

    /**
     * return all the list
     */
    Iterable<E> getAll();

    /**
     * @param id - the id of the entity to be returned
     * @return the entity with specified id or null
     * */
    E find(ID id);

    /**
     * @param entity - entity to be added to repository
     * @return null if the given entity is saved otherwise returns the entity
     * @throw ValidationException
     */
    E save(E entity);

    /**
     * @param id - removes the entity with the specified id
     * @return the removed entity or null if there is no entity with the given id
     */
    E delete(ID id) throws IOException;

    /**
     * @param entity - entity to be modified to repository
     * @return null if the id the given entity doesn't exist otherwise returns the entity before the modified action
     * @throw ValidationException
     */
    E update(E entity);

}
