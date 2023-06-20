import lombok.extern.slf4j.Slf4j;
import org.example.model.Cat;
import org.example.dao.CatDao;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@Slf4j
public class TestCatDao {

//    @BeforeAll
//    @BeforeEach
//    @AfterEach
//    @AfterAll

    CatDao catDao = new CatDao();

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
        catDao.createTable();
    }

    @Test
    @Order(4)
    @DisplayName("Сохранение сущности")
    void saveCat() {

        for (Cat cat : cats) {
            catDao.save(cat);
        }

        for (int i = 0; i < cats.length; i++) {
            Assertions.assertEquals(cats[i], catDao.get(Long.valueOf(i + 1)));
        }
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

        final var cat = cats[0];

        catDao.save(cat);
        cat.setName("Sonya");
        catDao.update(cat);

        Assertions.assertEquals(cat, catDao.get(1L));
    }

    @Test
    @Order(1)
    @DisplayName("Удаление сущности")
    void deleteCat() {

        catDao.save(cats[0]);
        catDao.deleteId(1L);

        Assertions.assertThrowsExactly(
                RuntimeException.class,
                () -> catDao.get(1L));
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
        catDao.dropTable();
    }

    @AfterAll
    static void afterAll() {
        log.info("Tests complete!");
    }
}
