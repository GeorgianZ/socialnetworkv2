package repository.database;

import domain.User;
import validate.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.sql.DriverManager.getConnection;

public class UserDBRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;
    public UserDBRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public int size() {
        AtomicInteger n= new AtomicInteger();
        Iterable<User> users = getAll();
        users.forEach(x-> {
            n.getAndIncrement();});
        return n.get();
    }


    @Override
    public User find(Long idd) {
        if (idd==null)
            throw new IllegalArgumentException("id must be not null");
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users WHERE id=?")){
            statement.setDouble(1, idd.doubleValue());
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                user = new User(firstName, lastName);
                user.setId(id);}
            return user;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public Iterable<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                User user = new User(firstName, lastName);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            //e.printStackTrace();
            return users;
        }
    }

    @Override
    public User save(User entity) {
        System.out.println(entity.getLastName());
        String sql = "insert into users (firstname, lastname) values (?, ?)";
        System.out.println(sql);
        try (Connection connection = getConnection(url, username, password)){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.executeQuery();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User delete(Long aLong) {
        String sql = "delete from users where id = ?";
        String sql1 = "delete from friendship where user1 = ?";
        String sql2 = "delete from friendship where user2 = ?";
        User u = find(aLong);
        try(Connection connection = getConnection(url, username, password);
            PreparedStatement ps1 = connection.prepareStatement(sql1)){
            ps1.setLong(1, aLong);
            ps1.executeUpdate();
        }
        catch (SQLException throwables) {
            return null;
        }
        try(Connection connection = getConnection(url, username, password);
            PreparedStatement ps2 = connection.prepareStatement(sql2)){
            ps2.setLong(1, aLong);
            ps2.executeUpdate();
        }
        catch (SQLException throwables) {
            return null;
        }
        try(Connection connection = getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, aLong);
            ps.executeUpdate();
            return u;
        } catch (SQLException throwables) {
            return null;
        }


    }

    @Override
    public User update(User entity) {
        String sql = "update users set firstname = ?, lastname = ? where id = ?";
        try(Connection connection = getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDouble(3, entity.getId().doubleValue());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e){
            return null;
        }
    }

}
