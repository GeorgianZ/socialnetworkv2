package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    private String firstName;
    private String lastName;
    private List<User> friends;

    /***
     * Constructor
     * @param firstName - String
     * @param lastName - String
     * */

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<User>();
    }


    /***
     * Override toString function
     * */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + friends +
                '}';
    }

    /***
     * return firstName
     * */
    public String getFirstName() {
        return firstName;
    }


    /***
     * set a String value for firstName
     * */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /***
     * return lastName
     * */
    public String getLastName() {
        return lastName;
    }


    /***
     * set a String value for lastName
     * */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /***
     * return list of friends
     * */
    public List<User> getFriends() {
        return friends;
    }


    /***
     * set a list for friends
     * */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * add friend in friend list
     * @param u - User
     * @return boolean
     */
    public boolean addFriend(User u) {
        if (friends != null) {
            for (User u1 : friends) {
                if (u1 == u)
                    return false;
            }
        }
        this.friends.add(u);
        return true;
    }

    /***
     * delete a friend in friend list
     * @param u - User
     * @return boolean
     */
    public boolean removeFriend(User u) {
        if (friends != null) {
            for (User u1 : friends) {
                if (u1 == u) {
                    friends.remove(u1);
                    return true;
                }
            }
        }
        return false;
    }


    /***
     * Override Equals function
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(friends, user.friends);
    }


    /***
     * Override HashCode function
     * */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, friends);
    }
}
