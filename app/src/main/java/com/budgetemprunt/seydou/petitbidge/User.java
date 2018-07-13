package com.budgetemprunt.seydou.petitbidge;

public class User {
    private int id;

    private String mail;
    private String pass;

    public User(String mail, String pass) {

        this.mail = mail;
        this.pass = pass;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

