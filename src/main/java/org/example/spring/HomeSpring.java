package org.example.spring;

public class HomeSpring {

    private String address;

    public HomeSpring(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "HomeSpring{" +
                "address='" + address + '\'' +
                '}';
    }
}
