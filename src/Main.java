import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.FriendshipFileRepo;
import repository.UserFileRepo;
import repository.database.FriendshipDBRepository;
import repository.database.Repository;
import repository.database.UserDBRepository;
import service.Service;
import ui.Ui;
import validate.FriendshipValidator;
import validate.UserValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        //String fileName=ApplicationContext.getPROPERTIES().getProperty("socialnetwork.users");
        String fileName = "F:\\metAvansate\\socialnetworkv2\\src\\repository\\users.csv";
        String fileName1 = "F:\\metAvansate\\socialnetworkv2\\src\\repository\\friendship.csv";
        //Repository<Long, User> userFileRepository = new UserFileRepo(new UserValidator(), fileName);
        //Repository<Tuple<Long,Long>, Friendship> friendshipFileRepo = new FriendshipFileRepo(new FriendshipValidator(), fileName1);
        Repository<Long, User> repoDb = new UserDBRepository("jdbc:postgresql://localhost:5432/socialNetworkDB", "postgres", "tigancopt64", new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> networkDb = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/socialNetworkDB", "postgres", "tigancopt64", new FriendshipValidator());
        Service service = new Service(repoDb, networkDb);
        Ui ui = new Ui(service);
        while (true) {
            System.out.println("\n1:add user\n" +
                    "2:remove user\n" +
                    "3:add friendship\n" +
                    "4:remove friendship\n"+
                    "5:number of communities\n"+
                    "6:most socializable community\n"+
                    "7:exit\n" +
                    "8:update User\n");
                    //"9.update Friendship");
            System.out.println("Command = ");
            int input = Integer.parseInt(stdin.readLine());
            if(input==0) break;
            switch (input) {
                case 1:
                    System.out.println("last name = ");
                    String lastName = stdin.readLine();
                    System.out.println("first name = ");
                    String firstName = stdin.readLine();
                    ui.addUser(lastName, firstName);
                    break;
                case 2:
                    System.out.println("last name = ");
                    lastName = stdin.readLine();
                    System.out.println("first name = ");
                    firstName = stdin.readLine();
                    ui.removeUser(lastName, firstName);
                    break;
                case 3:
                    System.out.println("id1 = ");
                    long id1 = Long.parseLong(stdin.readLine());
                    System.out.println("id2 = ");
                    long id2 = Long.parseLong(stdin.readLine());
                    ui.addFrienship(id1, id2);
                    break;
                case 4:
                    System.out.println("id1 = ");
                    id1 = Long.parseLong(stdin.readLine());
                    System.out.println("id2 = ");
                    id2 = Long.parseLong(stdin.readLine());
                    ui.removeFriendship(id1, id2);
                    break;
                case 5:
                    ui.numberOfCommunities();
                    break;
                case 6:
                    ui.socializableCommunity();
                    break;
                case 7:
                    return;
                case 8:
                    System.out.println("last name = ");
                    lastName = stdin.readLine();
                    System.out.println("first name = ");
                    firstName = stdin.readLine();
                    System.out.println("new last name = ");
                    String lastName1 = stdin.readLine();
                    System.out.println("new first name = ");
                    String firstName1 = stdin.readLine();
                    ui.updateUser(lastName, firstName, lastName1, firstName1);
                    break;
                case 9:
                    System.out.println("id1 = ");
                    id1 = Long.parseLong(stdin.readLine());
                    System.out.println("id2 = ");
                    id2 = Long.parseLong(stdin.readLine());
                    ui.updateFrienship(id1, id2);
                    break;
                default:
                    System.out.println("Incorrect argument");
                    break;
            }
        }
    }
}