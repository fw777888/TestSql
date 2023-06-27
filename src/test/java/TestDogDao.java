import lombok.extern.slf4j.Slf4j;
import org.example.dao.DogDao;
import org.example.model.Dog;
import org.junit.jupiter.api.*;
import util.DataImport;

import java.util.logging.Logger;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestDogDao {
    private final Dog dog = new Dog(6L, "test", true);
    private final DogDao dogDao = new DogDao();

    Dog[] dogs = {
            new Dog(1L, "Rex", true),
            new Dog(2L, "Sobaka", true),
            new Dog(3L, "Scooby-Doo", true)
    };

    @BeforeAll
    static void beforeAll() {
        log.info("Tests are started");
    }

    @BeforeEach
    void before() {
        log.info("Next test starts");

        DataImport.dataImport();
        log.info("The dog table is created");
    }


    @Test
    @DisplayName("Save a dog")
    void saveDog() {
        dogDao.save(dog);
        final var resultDog = dogDao.get(dog.getId());

        Assertions.assertEquals(dog, resultDog);
    }


    @Test
    @DisplayName("Get dog")
    void getDog() {
        Assertions.assertDoesNotThrow(() -> dogDao.get(1L));
    }

    @Test
    @DisplayName(("Update dog"))
    void updateDog() {
        final var resultDog = dogDao.get(1L);

        resultDog.setName("dog");

        dogDao.update(resultDog);

        Assertions.assertEquals(resultDog, dogDao.get(1L));
    }

    @Test
    @DisplayName("Deleting dog")
    void deleteDog() {
        dogDao.deleteId(1L);

        Assertions.assertThrowsExactly(RuntimeException.class, () -> dogDao.get(1L));
    }

    @Test
    void findAllTest() {
        for (Dog dog: dogs) {
            dogDao.save(dog);
        }

        final var allDogs = dogDao.findAll();

        Assertions.assertEquals(allDogs.size(), 3);
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
        log.info("The dog table is deleted");
    }

    @AfterAll
    static void afterAll() {
        log.info("All tests completed");
    }
}
