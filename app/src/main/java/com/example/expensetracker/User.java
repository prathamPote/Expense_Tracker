package com.example.expensetracker;

public class User {
    private String username;
    private String UserID;
    private String Budget;

    public User(String username, String userID, String budget) {
        this.username = username;
        UserID = userID;
        Budget = budget;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }
}




