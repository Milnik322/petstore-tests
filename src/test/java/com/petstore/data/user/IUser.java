package com.petstore.data.user;

public interface IUser {

    // Getters
    int getId();

    String getUsername();

    String getFirstname();

    String getLastname();

    String getEmail();

    String getPassword();

    String getPhone();

    int getUserStatus();

    // Setters
    IUser setId(int id);

    IUser setUsername(String username);

    IUser setFirstname(String firstname);

    IUser setLastname(String lastname);

    IUser setEmail(String email);

    IUser setPassword(String password);

    IUser setPhone(String phone);

    IUser setUserStatus(int userStatus);

    IUser build();
}
