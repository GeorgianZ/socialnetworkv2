package repository;

import domain.Friendship;
import domain.Tuple;
import validate.Validator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FriendshipFileRepo extends AbstractFileRepository<Tuple<Long, Long>, Friendship> {
    public FriendshipFileRepo(Validator<Friendship> validator, String filename) {
        super(validator, filename);
    }

    @Override
    public Friendship delete(Tuple<Long, Long> tup) throws IOException{
        Friendship f = super.delete(tup);
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(filename);
            pw.write("");
            pw.close();
            for(Friendship f1 : entities.values()){
                writeToFile(f1);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return f;
    }
    @Override
    public Friendship extractEntity(List<String> attributes) {
        Friendship friendship = new Friendship(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)));
        return friendship;
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getUser1()+";"+entity.getUser2();
    }
}

