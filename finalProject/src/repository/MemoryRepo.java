package repository;
import domain.Identifiable;


import java.util.Map;
import java.util.HashMap;

public class MemoryRepo<T extends Identifiable<ID>, ID> implements InterRepo<T, ID> {
    public Map<ID,T> elements= new HashMap<ID,T>();


//    public MemoryRepo()
//    {
//        this.elements = new HashMap<ID,T>();
//    }

    @Override
    public void addItem(T item) throws DuplicateEntityException {
        if (elements.containsKey(item.getId())) {
            throw new DuplicateEntityException("This item already exists");
        }

        elements.put((ID) item.getId(), item);
    }

    @Override
    public void removeItem(ID id) throws DuplicateEntityException {
        if (!elements.containsKey(id)) {//check  whether the map contains the key
            throw new DuplicateEntityException("Entity with ID " + id + " not found");
        }
        elements.remove(id);
    }

    @Override
    public void update(T entity, ID id) throws DuplicateEntityException {
        if (elements.containsKey(id)) {
            // If the entity with the provided ID exists, update it
            elements.put(id, entity);
        } else {
            // Handle the case where the entity doesn't exist
            throw new DuplicateEntityException("Entity with ID " + id + " not found");
        }
    }

    @Override
    public T findItem(ID id) {
        return elements.get(id);
    }


    @Override
    public Iterable<T> getAllItems() {
        return elements.values();
    }
}