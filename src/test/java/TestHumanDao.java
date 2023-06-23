import lombok.extern.slf4j.Slf4j;
import org.example.dao.HumanDao;
import org.example.model.Human;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestHumanDao {
    HumanDao humanDao = new HumanDao();

    Human[] people = {
            new Human(1, "John", "Walker"),
            new Human(2, "Steve", "Voznyak"),
            new Human(3, "Mike", "Lebovsky")
    } ;

    @BeforeAll
    static void beforeAll() { log.info("Test starts");}

    @BeforeEach
    void before() {log.info("Next test starts");
        humanDao.createTable();
    }



    @Test
    @Order(4)
    @DisplayName("Saving a person")
    void saveHuman() {
        for (Human human : people) {
            humanDao.save(human);
        }

        for (int i = 0; i < people.length; i++) {
            Assertions.assertEquals(people[i], humanDao.get(Long.valueOf(++i)));
        }
    }

    @Test
    @Order(3)
    @DisplayName("Get a human")
    void getHuman() {

        humanDao.save(people[0]);
        final var returnHuman = humanDao.get(1L);
        Assertions.assertEquals(people[0], returnHuman);
    }

    @Test
    @Order(2)
    @DisplayName("Update a human")
    void updateHuman() {
        final var human = people[0];

        humanDao.save(human);
        human.setName("Jim");
        human.setLastName("Davidson");
        humanDao.update(human);

        Assertions.assertEquals(human, humanDao.get(1L));
    }

    @Test
    @Order(1)
    @DisplayName("Deleting a human")
    void deleteHuman() {
        humanDao.save(people[0]);
        humanDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> humanDao.get(1L));
    }

    @DisplayName("Testing a table")
    @Nested
    class  TestTable {
        @Test
        @Order(6)
        @DisplayName("Creating a table")
        void createTableTest() {
            for (int i = 0; i < 3; i++) {
                humanDao.createTable();
            }
        }

        @Test
        @Order(5)
        @DisplayName("Deleting a table")
        void dropTableTest() {
            for (int i = 0; i < 3; i++) {
                humanDao.dropTable();
            }
        }
    }
    @AfterEach
    void after() {
        log.info("Next test comleted");
        humanDao.dropTable();
    }

    @AfterAll
    static void afterAll() {
        log.info("Test completed");
    }
}

