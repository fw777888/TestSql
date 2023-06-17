import org.example.model.Cat;
import org.example.dao.CatDao;
import org.h2.jdbc.JdbcSQLNonTransientException;
import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Executable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        System.out.println("Tests start!");
        System.out.println();
    }

    @BeforeEach
    void before() {
        System.out.println("Next test start!");
        System.out.println(this);

        System.out.println();

        catDao.createTable();
    }

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

    @Test
    @Order(4)
    @DisplayName("Сохранение сущности")
    void saveCat() {

        for (Cat cat : cats) {
            catDao.saveCat(cat);
        }

        for (int i = 0; i < cats.length; i++) {
            Assertions.assertEquals(cats[i], catDao.getCat(++i));
        }
    }

    @Test
    @Order(3)
    @DisplayName("Запрос сущности")
    void getCat() {

        catDao.saveCat(cats[0]);

        final var returnCat = catDao.getCat(1);

        Assertions.assertEquals(cats[0], returnCat);
    }

    @Test
    @Order(2)
    @DisplayName("Обновление сущности")
    void updateCat() {

        final var cat = cats[0];

        catDao.saveCat(cat);
        cat.setName("Sonya");
        catDao.updateCat(cat);

        Assertions.assertEquals(cat, catDao.getCat(1));
    }

    @Test
    @Order(1)
    @DisplayName("Удаление сущности")
    void deleteCat() {

        catDao.saveCat(cats[0]);
        catDao.deleteCatId(1);

        Assertions.assertThrowsExactly(RuntimeException.class, () -> catDao.getCat(1));
    }

    @AfterEach
    void after() {
        System.out.println("Next test complete!");

        System.out.println();

        catDao.dropTable();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Tests complete!");
    }
}
