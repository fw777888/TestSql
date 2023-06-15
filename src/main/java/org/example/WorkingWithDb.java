package org.example;

import org.h2.Driver;
import java.sql.*;

public class WorkingWithDb {
    public static void main(String[] args) {
        CatDao catDao = new CatDao();
        CatDao.createTable();
        Cat cat1 = new Cat(1, "Tom1");
        Cat cat2 = new Cat(2, "Tom2");
        Cat cat3 = new Cat(3, "Tom3");
        Cat cat4 = new Cat(4, "Tom4");
        Cat cat5 = new Cat(5, "Tom5");

        catDao.saveCat(cat1);
        catDao.saveCat(cat2);
        catDao.saveCat(cat3);
        catDao.saveCat(cat4);
        catDao.saveCat(cat5);


    }
}
