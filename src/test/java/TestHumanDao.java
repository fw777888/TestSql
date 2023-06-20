import org.example.dao.HumanDao;
import org.example.model.Human;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestHumanDao {
    HumanDao humanDao = new HumanDao();

    Human[] people = {
            new Human(1, "John", "Walker"),
            new Human(2, "Steve", "Voznyak"),
            new Human(3, "Mike", "Lebovsky")
    } ;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Test starts");
        System.out.println();
    }

    @BeforeEach
    void before() {
        System.out.println("Next test starts");
        System.out.println(this);
        System.out.println();
        humanDao.createTable();
    }

    @Test
    @Order(1)
    @DisplayName("Creating a table")
    void createTableTest() {
        for (int i = 0; i < 3; i++) {
            humanDao.createTable();
        }
    }

    @Test
    @Order(6)
    @DisplayName("Deleting a table")
    void dropTableTest() {
        for (int i = 0; i < 3; i++) {
            humanDao.dropTable();
        }
    }

    @Test
    @Order(3)
    @DisplayName("Saving a person")
    void saveHuman() {
        for (Human human : people) {
            humanDao.saveHuman(human);
        }

        for (int i = 0; i < people.length; i++) {
            Assertions.assertEquals(people[i], humanDao.getHuman(++i));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Get a human")
    void getHuman() {

        humanDao.saveHuman(people[0]);
        final var returnHuman = humanDao.getHuman(1);
        Assertions.assertEquals(people[0], returnHuman);
    }

    @Test
    @Order(4)
    @DisplayName("Update a human")
    void updateHuman() {
        final var human = people[0];

        humanDao.saveHuman(human);
        human.setName("Jim");
        human.setLastName("Davidson");
        humanDao.updateHuman(human);

        Assertions.assertEquals(human, humanDao.getHuman(1));
    }

    @Test
    @Order(5)
    @DisplayName("Deleting a human")
    void deleteHuman() {
        humanDao.saveHuman(people[0]);
        humanDao.deleteHumanId(1);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> humanDao.getHuman(1));
    }

    @AfterEach
    void after() {
        System.out.println("Next test comleted");
        System.out.println();
        humanDao.dropTable();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Test completed");
    }
}

