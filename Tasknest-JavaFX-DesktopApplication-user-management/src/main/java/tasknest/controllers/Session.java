package tasknest.controllers;

import tasknest.models.users;

public class Session {
    private static Session instance;
    private users currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(users user) {
        this.currentUser = user;
    }

    public users getCurrentUser() {
        return currentUser;
    }

    public void endSession() {
        currentUser = null;
    }
}
