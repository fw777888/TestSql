package org.example.spring;

public class DogSpring {

    private String name;
    private int age;

    public DogSpring(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "DogSpring{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
