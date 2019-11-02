package com.burak.healven.helpful;

import java.io.Serializable;
public class Profile implements Serializable {

    public String name, surname, mail;

    public Profile(String name, String surname, String mail) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
    }

    public Profile(Profile user) {
        this.name = user.name;
        this.surname = user.surname;
        this.mail = user.mail;
    }


}
