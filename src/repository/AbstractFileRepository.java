package repository;

import domain.Entity;
import validate.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    String filename;

    public AbstractFileRepository(Validator<E> validator, String filename) {
        super(validator);
        this.filename = filename;
        loadFromFile();
    }

    /**
     * load information from file
     */
    private void loadFromFile() {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null){
                List<String> attributes = Arrays.asList(line.split(";"));
                E entity = extractEntity(attributes);
                super.save(entity);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);

    /***
     *
     * @param entity
     * @return
     */
    protected abstract String createEntityAsString(E entity);


    /**
     * @param entity - entity to be added to repository
     * @return
     */
    @Override
    public E save(E entity){
        E e = super.save(entity);
        if(e == null)
            writeToFile(entity);
        return e;
    }

    /**
     * write to file the given entity
     * @param entity
     */
    protected void writeToFile(E entity) {
        String line = createEntityAsString(entity);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))){
            bw.write(line);
            bw.newLine();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}

