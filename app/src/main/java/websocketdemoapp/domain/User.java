package websocketdemoapp.domain;

public class User {
    private String id;
    private String username;
    private String password;
    private double points;

    //private static long counter = 1;

    public User() {
        //default
    }

    public User(String username) {
        //this.id = id;
        //this.id = Long.toString(counter++);
        this.username = username;
        this.points = 0;
    }

    private void addId() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}