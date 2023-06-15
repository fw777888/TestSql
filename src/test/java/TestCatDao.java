import org.example.Cat;
import org.example.CatDao;
import org.junit.jupiter.api.Test;

public class TestCatDao {
    @Test
    void createTableTest() {
        CatDao catDao = new CatDao();
        catDao.createTable();
    }

    @Test
    void dropTableTest() {
        CatDao catDao = new CatDao();
        catDao.dropTable();
    }

    @Test
        void saveCat() {
            CatDao catDao = new CatDao();
            Cat[] cats = {
                    new Cat(1, "Tom1"),
                    new Cat(2, "Tom2"),
                    new Cat(3, "Tom3"),
                    new Cat(4, "Tom4"),
                    new Cat(5, "Tom5"),
            };

            catDao.createTable();
            for (Cat cat : cats) {
                catDao.saveCat(cat);
            }
        }
}
