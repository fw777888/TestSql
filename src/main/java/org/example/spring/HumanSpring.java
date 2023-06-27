package org.example.spring;

public class HumanSpring {

    private String firstname;
    private String lastname;
    private int money;

    public HumanSpring(String firstname, String lastname, int money) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.money = money;
    }

    @Override
    public String toString() {
        return "HumanSpring{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", money=" + money +
                '}';
    }
}
