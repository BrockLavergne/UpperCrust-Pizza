package com.techelevator.model.login;

import com.fasterxml.jackson.annotation.JsonProperty;
/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, CredentialsDto represents the data a client must
    pass to the server for a login endpoint, and TokenDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class LoginResponseDto {

    private String token;
    private User user;
    private UserData userData;

    public LoginResponseDto(String token, User user, UserData userData) {
        this.token = token;
        this.user = user;
        this.userData = userData;
    }

    @JsonProperty("token")
    String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
