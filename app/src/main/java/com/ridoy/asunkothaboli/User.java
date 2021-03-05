package com.ridoy.asunkothaboli;

public class User {
    String name,email,password,profileimageurl,on;

    public User(String name, String email, String password, String profileimageurl, String on) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileimageurl = profileimageurl;
        this.on = on;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public User() {
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
