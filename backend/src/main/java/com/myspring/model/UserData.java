package com.myspring.model;

public class UserData {
    private String login;
    private int role;
    private String firstName;
    private String lastName;
    private String groupName;

    public UserData() {
    }

    public UserData(String login, int role, String firstName, String lastName, String groupName) {
        this.login = login;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupName = groupName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
