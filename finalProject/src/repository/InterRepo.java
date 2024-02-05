package repository;

import domain.Identifiable;

public interface InterRepo <T extends Identifiable,ID> {
    public void addItem(T item) throws DuplicateEntityException;
    public void removeItem(ID id) throws DuplicateEntityException;
    void update(T entity, ID id) throws DuplicateEntityException;
    public T findItem(ID id);
    public Iterable<T> getAllItems();
}