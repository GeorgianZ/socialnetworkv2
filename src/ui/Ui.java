package ui;

import domain.Friendship;
import domain.User;
import service.Service;

import java.util.ArrayList;
import java.util.Objects;

public class Ui {
    private Service service;

    /***
     *constructor
     * @param service
     */
    public Ui(Service service) {
        this.service = service;
    }

    /***
     * add user
     * @param lastName
     * @param firstName
     */
    public void addUser(String lastName, String firstName) {
        User user = new User(lastName, firstName);
        long id = 0;
        for (User u : service.getUsers()) {
            id = u.getId();
        }
        id++;
        user.setId(id);
        try {
            User u = service.addUser(user);
            if (u != null)
                System.out.println("User already exists");
            else System.out.println("User added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateUser(String lastName, String firstName, String lastName1, String firstName1) {
        User user = new User(firstName, lastName);
        for (User u : service.getUsers()) {
            //id = u.getId();
            System.out.println(u.getLastName());
            if (Objects.equals(u.getLastName(), firstName) && Objects.equals(u.getFirstName(), lastName)) {

                removeUser(lastName, firstName);
                addUser(lastName1, firstName1);
                break;
            }
        }

    }

    /***
     * delete user
     * @param lastName
     * @param firstName
     */
    public void removeUser(String lastName, String firstName) {
        User ut = null;
        ArrayList<User> list = new ArrayList<User>();
        for (User user : service.getUsers()) {
            if (user.getFirstName().equals(lastName) && user.getLastName().equals(firstName))
                list.add(user);
        }
        try {
            ut = service.removeUser(list.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (ut != null)
            System.out.println("Removed user");
        else System.out.println("Nonexistent user");
    }

    /***
     * add friendship
     * @param id1
     * @param id2
     */
    public void addFrienship(long id1, long id2) {
        User u1 = null;
        User u2 = null;
        for (User u : service.getUsers()) {
            if (u.getId() == id1)
                u1 = u;
            if (u.getId() == id2)
                u2 = u;
        }

        if (u1 == null || u2 == null)
            System.out.println("Incorrect id");
        else {
            Friendship f = service.addFriendship(id1, id2);
            if (f == null)
                System.out.println("Friendship already exists");
            else
                System.out.println(f);
        }

    }

    public void updateFrienship(long id1, long id2) {
        User u1 = null;
        User u2 = null;
        for (User u : service.getUsers()) {
            if (u.getId() == id1)
                u1 = u;
            if (u.getId() == id2)
                u2 = u;
        }

        if (u1 == null || u2 == null)
            System.out.println("Incorrect id");
        else {
            Friendship f = service.updateFriendship(id1, id2);
            if (f == null)
                System.out.println("Friendship already exists");
            else
                System.out.println(f);
        }

    }

    /***
     * remove friendship
     * @param id1
     * @param id2
     */
    public void removeFriendship(long id1, long id2) {
        User u1 = null;
        User u2 = null;
        for (User u : service.getUsers()) {
            if (u.getId() == id1)
                u1 = u;
            if (u.getId() == id2)
                u2 = u;
        }
        if (u1 == null || u2 == null)
            System.out.println("Incorrect id");
        else {
            Friendship f = service.removeFriendship(id1, id2);
            if (f == null)
                System.out.println("Nonexistent friendship");
            else {
                System.out.println(f);
            }
        }
    }

    /***
     * show the number of communities
     */
    public void numberOfCommunities(){
        int nr= service.numberOfCommunities();
        System.out.println("Number of communities = "+nr);
    }

    /***
     * show the most socializable community
     */
    public void socializableCommunity(){

        ArrayList<Integer>  list =service.socializableCommnunity();
        System.out.println("The most socializable community is made by users with following IDs: ");
        for (int i :list) {
            System.out.print(i+" ");

        }
        System.out.println();

    }
}

