package Model;

import java.util.ArrayList;

public class User {
    public static ArrayList<User> allUsers = new ArrayList<>();
    private String username;
    private int score;
    private int heart;

    public int getHeart() {
        return heart;
    }

    public void decreaseHeart() {
        this.heart = this.heart - 1;
    }

    public void increaseHeart() {
        this.heart = this.heart + 1;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public User(String username) {
        setUsername(username);
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }
}
