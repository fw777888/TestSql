import lombok.extern.slf4j.Slf4j;
import org.example.model.Cat;
import org.example.dao.CatDao;
import org.junit.jupiter.api.*;
import util.DataImport;

@TestMethodOrder(MethodOrderer.MethodName.class)
@Slf4j
public class TestCatDao {

//    @BeforeAll
//    @BeforeEach
//    @AfterEach
//    @AfterAll
    private final Cat cat = new Cat(5L, "testCat");
    private final CatDao catDao = new CatDao();

    Cat[] cats = {
            new Cat(1, "Tom1"),
            new Cat(2, "Tom2"),
            new Cat(3, "Tom3"),
            new Cat(4, "Tom4"),
            new Cat(5, "Tom5"),
    };

    @BeforeAll
    static void beforeAll() {
        log.info("Test start!");
    }

    @BeforeEach
    void before() {
        log.info("Next test start!");
        DataImport.dataImport();
        log.info("The cat table is created");
        //catDao.createTable();
    }

    @Test
    @Order(4)
    @DisplayName("Сохранение сущности")
    void saveCat() {
            catDao.save(cat);
            final var resultCat = catDao.get(cat.getId());
            Assertions.assertEquals(cat, resultCat);
    }

    @Test
    @Order(3)
    @DisplayName("Запрос сущности")
    void getCat() {

        catDao.save(cats[0]);

        final var returnCat = catDao.get(1L);

        Assertions.assertEquals(cats[0], returnCat);
    }

    @Test
    @Order(2)
    @DisplayName("Обновление сущности")
    void updateCat() {

        final var resultCat = catDao.get(1L);
        resultCat.setName("Tom");
        catDao.update(resultCat);
        Assertions.assertEquals(resultCat, catDao.get(1L));
    }

    @Test
    @Order(1)
    @DisplayName("Удаление сущности")
    void deleteCat() {
        catDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> catDao.get(1L));
    }

    @Test
    void findAllTest() {
        for (Cat cat : cats) {
            catDao.save(cat);
        }

        final var allCats = catDao.findAll();

        Assertions.assertEquals(allCats.size(), 8);
    }

    @DisplayName("Тестирование таблицы")
    @Nested
    class TestTable {
        @Test
        @Order(6)
        @DisplayName("Создание таблицы")
        void createTableTest() {
            for (int i = 0; i < 2; i++) {
                catDao.createTable();
            }
        }

        @Test
        @Order(5)
        @DisplayName("Удаление таблицы")
        void dropTableTest() {

            for (int i = 0; i < 2; i++) {
                catDao.dropTable();
            }
        }
    }

    @AfterEach
    void after() {
        log.info("Next test complete!");
        log.info("The cat table is deleted");
    }

    @AfterAll
    static void afterAll() {
        log.info("All tests competed!");
    }
}
