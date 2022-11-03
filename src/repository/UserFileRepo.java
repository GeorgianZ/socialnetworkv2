package repository;

import domain.User;
import validate.Validator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserFileRepo extends AbstractFileRepository<Long, User>{

    public UserFileRepo(Validator<User> validator, String filename) {
        super(validator, filename);
    }

    @Override
    public User delete(Long ULong) throws IOException {
        User u = super.delete(ULong);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(filename);
            pw.print("");
            pw.close();
            for (User u1 : entities.values()) {
                writeToFile(u1);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public User extractEntity(List<String> attributes) {
        User u = new User(attributes.get(1), attributes.get(2));
        u.setId(Long.parseLong(attributes.get(0)));

        return u;
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName();
    }
}

