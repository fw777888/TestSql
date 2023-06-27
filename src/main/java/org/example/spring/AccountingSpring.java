package org.example.spring;

import org.example.model.Dog;

public class AccountingSpring {

    private DogSpring dogSpring;
    private HomeSpring homeSpring;
    private HumanSpring humanSpring;

    public AccountingSpring(DogSpring dogSpring, HomeSpring homeSpring, HumanSpring humanSpring) {
        this.dogSpring = dogSpring;
        this.homeSpring = homeSpring;
        this.humanSpring = humanSpring;
    }

    @Override
    public String toString() {
        return "AccountingSpring{" +
                "dogSpring=" + dogSpring +
                ", homeSpring=" + homeSpring +
                ", humanSpring=" + humanSpring +
                '}';
    }
}
