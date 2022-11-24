package repository;

import domain.Entity;
import validate.Validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Validator<E> validator;
    public Map<ID, E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap<ID, E>();
    }

    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public Iterable<E> getAll() {
        return entities.values();
    }

    @Override
    public E find(ID id) {
        return entities.get(id);
    }

    @Override
    public E save(E entity) {
        validator.validate(entity);
        if (entities.get(entity.getId()) != null) {
            return entity;
        }
        else{
            entities.put(entity.getId(), entity);
        }
        return null;
    }

    @Override
    public E delete(ID id) throws IOException {
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        E entity = entities.get(id);
        if(entity == null)
            return null;
        else
            return entities.remove(id);
    }

    @Override
    public E update(E entity){
        validator.validate(entity);
        if(entities.get(entity.getId()) != null){
            E entity1 = entities.get(entity.getId());
            try {
                delete(entity.getId());
            }
            catch (IOException e){
                e.printStackTrace();
            }
            entities.put(entity.getId(), entity);
            return entity1;
        }
        return null;
    }


}
