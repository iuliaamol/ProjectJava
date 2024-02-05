package repository;
import domain.Identifiable;

public abstract class FileRepo<ID, T extends Identifiable<ID>> extends MemoryRepo<T,ID>{
    protected String filename;
    FileRepo(String filename){
        this.filename = filename;
        //readFromFile();
    }


    protected abstract void readFromFile();
    protected abstract void writeToFile();

    @Override
    public void addItem(T item) throws DuplicateEntityException {
        super.addItem(item);
        writeToFile();
    }

    @Override
    public void removeItem(ID id) throws DuplicateEntityException {
        super.removeItem(id);
        writeToFile();
    }

    @Override
    public void update(T entity, ID id) throws DuplicateEntityException {
        super.update(entity, id);
        writeToFile();
    }

    @Override
    public T findItem(ID id) {

        return super.findItem(id);
    }


//    @Override
//    public Iterable<T> getAllItems() {
//        //super.elements.clear();
//        readFromFile();
//        return super.getAllItems();
//    }
}