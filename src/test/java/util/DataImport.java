package util;

import org.example.dao.*;
import org.example.model.Bear;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Human;

import java.awt.*;
import java.util.List;

public class DataImport {

    private static final Dao<Long, Bear> BEAR_DAO = new BearDao();

    private final static Dao<Long, Cat> CAT_DAO = new CatDao();

    private static final Dao<Long, Dog> DOG_DAO = new DogDao();

    private static final Dao<Long, Human> HUMAN_DAO = new HumanDao();

    public static void dataImport() {
        BEAR_DAO.dropTable();
        BEAR_DAO.createTable();

        CAT_DAO.dropTable();
        CAT_DAO.createTable();

        DOG_DAO.dropTable();
        DOG_DAO.createTable();
        saveDog();

        HUMAN_DAO.dropTable();
        HUMAN_DAO.createTable();
    }

    private static void saveDog() {
        final var dogs = List.of(
                Dog.builder()
                        .id(1L)
                        .name("Sharik")
                        .isHome(true)
                        .build(),

                Dog.builder()
                        .id(2L)
                        .name("Raketa")
                        .isHome(true)
                        .build(),

                Dog.builder()
                        .id(3L)
                        .name("Kubik")
                        .isHome(true)
                        .build(),

                Dog.builder()
                        .id(4L)
                        .name("Volt")
                        .isHome(true)
                        .build(),

                Dog.builder()
                        .id(5L)
                        .name("Sobaka")
                        .isHome(true)
                        .build()
        );// не изменный список

        for (Dog dog : dogs) {
            DOG_DAO.save(dog);
        }
    }
}
