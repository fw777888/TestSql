import org.example.dao.DogDao;
import org.example.model.Dog;
import org.junit.jupiter.api.*;

import java.util.logging.Logger;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDogDao {
    DogDao dogDao = new DogDao();

    Dog[] dogs = {
            new Dog(1L, "Kubik", true),
            new Dog(2L, "Zubastik", true),
            new Dog(3L, "Wolfy", true),
    } ;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Tests are started");
        System.out.println();
    }

    @BeforeEach
    void before() {
        System.out.println("Next test starts");
        System.out.println(this);
        System.out.println();

        dogDao.createTable();
        System.out.println("The dog table is created");
    }

    @Test
    @Order(1)
    @DisplayName("Creating table dog")
    void createTableTest() {
        for (int i = 0; i < 3; i++) {
            dogDao.createTable();
        }
    }

    @Test
    @Order(6)
    @DisplayName("Deleting table")
    void dropTableTest() {

        for (int i = 0; i < 3; i++) {
            dogDao.dropTable();
        }
    }

    @Test
    @Order(3)
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
    @Order(2)
    @DisplayName("Get dog")
    void getDog() {
        dogDao.save(dogs[0]);
        final var returnDog = dogDao.get(1L);

        Assertions.assertEquals(dogs[0], returnDog);
    }

    @Test
    @Order(4)
    @DisplayName(("Update dog"))
    void updateDog() {
        final var dog = dogs[0];

        dogDao.save(dog);
        dog.setName("Sobaka");
        dogDao.update(dog);

        Assertions.assertEquals(dog, dogDao.get(1L));
    }

    @Test
    @Order(5)
    @DisplayName("Deleting dog")
    void deleteDog() {

        dogDao.save(dogs[0]);
        dogDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> dogDao.get(1L));
    }

    @AfterEach
    void after() {
        System.out.println("Next test complete");
        System.out.println();
        dogDao.dropTable();
        System.out.println("The dog table is deleted");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("All tests completed");
    }


}
