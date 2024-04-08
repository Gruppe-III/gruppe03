package at.aau.serg.websocketdemoapp.repo;

import java.util.HashSet;
import java.util.Set;

import at.aau.serg.websocketdemoapp.domain.User;

public class LocalRepoUser {
    private final Set<User> storage = new HashSet<>();
    //private final Map<String, User> storage = new HashMap<>();
    private static long counter = 1;

    private static LocalRepoUser instance;


    private LocalRepoUser() {
        // Private Konstruktor, um sicherzustellen, dass nur eine Instanz erstellt wird
    }

    public static LocalRepoUser getInstance() {
        if (instance == null) {
            instance = new LocalRepoUser();
        }
        return instance;
    }

    public User findById(String id) {
        for (User user : storage) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        //todo
        storage.add(user);
    }

    public void removeUser(String id) {
        //todo
        User userToRemove = null;
        for (User user : storage) {
            if (user.getId() == id) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            storage.remove(userToRemove);
        }
    }

    public Set<User> findAllUsers() {
        //todo
        return storage;
    }

    public User findByName(String name) {
        for (User user : storage) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserExist(String username) {
        return findByName(username) != null;
    }
}
