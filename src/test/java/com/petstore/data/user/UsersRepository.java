package com.petstore.data.user;

public final class UsersRepository {

    public static IUser getValidUser() {
        return User.get()
                .setId(345678)
                .setUsername("TestUser345")
                .setFirstname("Test")
                .setLastname("User")
                .setEmail("testuser@test.com")
                .setPassword("password")
                .setPhone("12345678")
                .setUserStatus(1)
                .build();
    }
}
