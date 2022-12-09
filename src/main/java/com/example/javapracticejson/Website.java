package com.example.javapracticejson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Website {
    @SerializedName("Website")
    private String website;

    private User[] users;

    public String getWebsite() {
        return website;
    }

    public List<User> getUsers() {
        return Arrays.asList(users);
    }
}
