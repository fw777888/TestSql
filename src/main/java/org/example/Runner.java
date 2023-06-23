package org.example;

import org.example.dao.CatDao;
import org.example.dao.HumanDao;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Human;

public class Runner {
    public static void main(String[] args) {

//        CatDao catDao = new CatDao();
//
//        CatDao.createTable();
//
//        Cat cat1 = new Cat(1, "Tom1");
//        Cat cat2 = new Cat(2, "Tom2");
//        Cat cat3 = new Cat(3, "Tom3");
//        Cat cat4 = new Cat(4, "Tom4");
//        Cat cat5 = new Cat(5, "Tom5");
//
//        catDao.saveCat(cat1);
//        catDao.saveCat(cat2);
//        catDao.saveCat(cat3);
//        catDao.saveCat(cat4);
//        catDao.saveCat(cat5);

//        Dog dog = new Dog(1L, "Shar", true);
//
//        final var pushok = Dog.builder()
//                .id(2L)
//                .name("Pushok")
//                .isHome(true)
//                .build();
//
//        dog.setName("Tuzik");
//        dog.setId(2L);
//
//        System.out.println(dog.getName());
//        System.out.println(dog.getId());

        final var max = Human.builder()
                .id(1L)
                .name("Max")
                .lastName("Smirnov")
                .build();

        HumanDao humanDao = new HumanDao();
        humanDao.createTable();

//        humanDao.save(max);
        System.out.println(humanDao.get(1L));
    }
}
