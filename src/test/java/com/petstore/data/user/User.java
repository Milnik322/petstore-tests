package com.petstore.data.user;

import com.petstore.data.user.IUser;

public class User implements IUser {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public static IUser get() {
        return new User();
    }

    // Getters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public int getUserStatus() {
        return userStatus;
    }

    // Setters
    public IUser setId(int id) {
        this.id = id;
        return this;
    }

    public IUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public IUser setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public IUser setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public IUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public IUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public IUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public IUser setUserStatus(int userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public IUser build() {
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
