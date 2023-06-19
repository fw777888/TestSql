import org.example.dao.DogDao;
import org.example.model.Dog;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @DisplayName("Creating table dog")
    void createTableTest() {
        for (int i = 0; i < 3; i++) {
            dogDao.createTable();
        }
    }

    @Test
    @DisplayName("Deleting table")
    void dropTableTest() {

        for (int i = 0; i < 3; i++) {
            dogDao.dropTable();
        }
    }

    @Test
    @DisplayName("Save a dog")
    void saveDog() {

        for (Dog dog : dogs) {
            dogDao.saveDog(dog);
        }
    }

    @Test
    @DisplayName("Get dog")
    void getDog() {
        dogDao.saveDog(dogs[0]);
        final var returnDog = dogDao.getDog(1);
    }

    @Test
    @DisplayName(("Update dog"))
    void updateDog() {
        final var dog = dogs[0];

        dogDao.saveDog(dog);
        dog.setName("Sobaka");
        dogDao.updateDog(dog);
    }

    @Test
    @DisplayName("Deleting dog")
    void deleteDog() {

        dogDao.saveDog(dogs[0]);
        dogDao.deleteDogId(1);
    }

    @AfterEach
    void after() {
        System.out.println("Next test complete");
        System.out.println();
        dogDao.dropTable();
        System.out.println("The dog table is deleted");
    }

    @AfterAll
    void afterAll() {
        System.out.println("All tests completed");
    }


}
