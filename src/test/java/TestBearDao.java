import lombok.extern.slf4j.Slf4j;
import org.example.dao.BearDao;
import org.example.model.Bear;
import org.junit.jupiter.api.*;

@Slf4j
public class TestBearDao {

    // before -> {class [create/drop table -> 10 bear]} ]
    private final BearDao bearDao = new BearDao();

    Bear[] bears = {
            new Bear(1, "Balu"),
            new Bear(2, "Barney"),
            new Bear(3, "Umka")
    };

    @BeforeAll
    static void beforeAll() {
        log.info("Test starts");
    }

    @BeforeEach
    void beforeEach() {
        log.info("Next test starts");
        bearDao.createTable();
    }

    @Test
    @DisplayName("Saving a bear")
    void saveBear() {
        for (Bear bear : bears) {
            bearDao.save(bear);
        }

        for (int i = 0; i < bears.length; i++) {
            Assertions.assertEquals(bears[i], bearDao.get(Long.valueOf(++i)));
        }
    }

    @Test
    @DisplayName("Get a bear")
    void getBear() {
        bearDao.save(bears[0]);
        final var returnBear = bearDao.get(1L);
        Assertions.assertEquals(bears[0], returnBear);
    }

    @Test
    @DisplayName("Update a bear")
    void updateBear() {
        final var bear = bears[0];

        bearDao.save(bear);
        bear.setName("Grizzly");
        bearDao.update(bear);

        Assertions.assertEquals(bear, bearDao.get(1L));
    }

    @Test
    @DisplayName("Deleting a bear")
    void deleteBear() {
        bearDao.save(bears[0]);
        bearDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> bearDao.get(1L));
    }

    @Test
    void findAllTest() {
        for (Bear bear : bears) {
            bearDao.save(bear);
        }
        final var allBears = bearDao.findAll();

        Assertions.assertEquals(allBears.size(), 3);
    }

    @DisplayName("Testing a table")
    @Nested
    class TestTable {
        @Test
        @DisplayName("Creating a table")
        void createTableTest() {
            for (int i = 0; i < 3; i++) {
                bearDao.createTable();
            }
        }

        @Test
        @DisplayName("Deleting a table")
        void dropTableTest() {
            for (int i = 0; i < 3; i++) {
                bearDao.dropTable();
            }
        }
    }

    @AfterEach
    void after() {
        log.info("Next test completed");
        bearDao.dropTable();
    }

    @AfterAll
    static void afterAll() {
        log.info("Test completed");
    }
}
