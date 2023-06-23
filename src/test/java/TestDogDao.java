import lombok.extern.slf4j.Slf4j;
import org.example.dao.DogDao;
import org.example.model.Dog;
import org.junit.jupiter.api.*;

import java.util.logging.Logger;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestDogDao {
    DogDao dogDao = new DogDao();

    Dog[] dogs = {
            new Dog(1L, "Kubik", true),
            new Dog(2L, "Zubastik", true),
            new Dog(3L, "Wolfy", true),
    } ;

    @BeforeAll
    static void beforeAll() {
        log.info("Tests are started");
    }

    @BeforeEach
    void before() {
        log.info("Next test starts");

        dogDao.createTable();
        log.info("The dog table is created");
    }


    @Test
    @DisplayName("Save a dog")
    void saveDog() {

        for (Dog dog : dogs) {
            dogDao.save(dog);
        }
        for (int i = 0; i < dogs.length; i++) {
            Assertions.assertEquals(dogs[i], dogDao.get(Long.valueOf(i) + 1));
        }
    }


    @Test
    @DisplayName("Get dog")
    void getDog() {
        dogDao.save(dogs[0]);
        final var returnDog = dogDao.get(1L);

        Assertions.assertEquals(dogs[0], returnDog);
    }

    @Test
    @DisplayName(("Update dog"))
    void updateDog() {
        final var dog = dogs[0];

        dogDao.save(dog);
        dog.setName("Sobaka");
        dogDao.update(dog);

        Assertions.assertEquals(dog, dogDao.get(1L));
    }

    @Test
    @DisplayName("Deleting dog")
    void deleteDog() {

        dogDao.save(dogs[0]);
        dogDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> dogDao.get(1L));
    }

    @DisplayName("Testing a table")
    @Nested
    class TestTable {
        @Test
        @DisplayName("Creating table dog")
        void createTableTest () {
            for (int i = 0; i < 3; i++) {
                dogDao.createTable();
            }
        }

        @Test
        @DisplayName("Deleting table")
        void dropTableTest () {

            for (int i = 0; i < 3; i++) {
                dogDao.dropTable();
            }
        }
    }

    @AfterEach
    void after() {
        log.info("Next test complete");
        dogDao.dropTable();
        log.info("The dog table is deleted");
    }

    @AfterAll
    static void afterAll() {
        log.info("All tests completed");
    }


}
