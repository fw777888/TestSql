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
        saveCat();
        saveBear();
        saveDog();
        saveHuman();
    }

    private static void saveDog() {
        DOG_DAO.dropTable();
        DOG_DAO.createTable();

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

    private static void saveHuman() {
        HUMAN_DAO.dropTable();
        HUMAN_DAO.createTable();

        final var people = List.of(
                Human.builder()
                        .id(1)
                        .name("Amos")
                        .lastName("Barton")
                        .build(),

                Human.builder()
                        .id(2)
                        .name("Naomi")
                        .lastName("Nagato")
                        .build(),

                Human.builder()
                        .id(3)
                        .name("Alex")
                        .lastName("Kamal")
                        .build(),

                Human.builder()
                        .id(4)
                        .name("Roberta")
                        .lastName("Drapper")
                        .build(),

                Human.builder()
                        .id(5)
                        .name("James")
                        .lastName("Holden")
                        .build()
        );

        for (Human human : people) {
            HUMAN_DAO.save(human);
        }
    }

    private static void saveBear() {
        BEAR_DAO.dropTable();
        BEAR_DAO.createTable();
        final var bears = List.of(
                Bear.builder()
                        .id(1)
                        .name("Balu")
                        .build(),

                Bear.builder()
                        .id(2)
                        .name("Grizzli")
                        .build(),

                Bear.builder()
                        .id(3)
                        .name("Umka")
                        .build()
        );
        for (Bear bear : bears) {
            BEAR_DAO.save(bear);
        }
    }

    private static void saveCat() {
        CAT_DAO.dropTable();
        CAT_DAO.createTable();

        final var cats = List.of(
                Cat.builder()
                        .id(1)
                        .name("Tom")
                        .build(),

                Cat.builder()
                        .id(2)
                        .name("Bazilio")
                        .build(),

                Cat.builder()
                        .id(3)
                        .name("Begemot")
                        .build()
        );
        for (Cat cat : cats) {
            CAT_DAO.save(cat);
        }
    }
}
