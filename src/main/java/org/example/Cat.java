package org.example;

public class Cat {
    long id;
    String name;
    public Cat(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
