package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Models.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {
    private final BookDao bookDao = new BookDaoImpl();

    @Test
    public void testCreateBook() {
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        assertThat(bookDao.getBookByID(book.getBook_id())).usingRecursiveComparison().isEqualTo(book);
        bookDao.deleteBook(book);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        assertThat(bookDao.getBookByID(book.getBook_id())).usingRecursiveComparison().isEqualTo(book);
        book.setAmount(10);
        bookDao.updateBook(book);
        assertThat(bookDao.getBookByID(book.getBook_id())).usingRecursiveComparison().isEqualTo(book);
        bookDao.deleteBook(book);
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        assertThat(bookDao.getBookByID(book.getBook_id())).usingRecursiveComparison().isEqualTo(book);
        bookDao.deleteBook(book);
        Assert.assertNull(bookDao.getBookByID(book.getBook_id()));
    }

    @Test
    public void testGetBooksByTitle() {
        List<Book> expectedBooks = List.of(
                new Book(4, "Гарри Поттер и философский камень", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2002, 399, "Твердый переплет", 2290, 15)
        );
        List<Book> actualBooks = bookDao.getBooksByTitle("Гарри Поттер и философский камень");
        Assert.assertEquals(expectedBooks.size(), actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(expectedBooks.get(0));
    }

    @Test
    public void testGetBooksByAuthor() {
        List<Book> expectedBooks = List.of(
                new Book(4, "Гарри Поттер и философский камень", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2002, 399, "Твердый переплет", 2290, 15),
                new Book(5, "Гарри Поттер и Кубок огня", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2007, 627, "Твердый переплет", 2080, 4)
        );
        List<Book> actualBooks = bookDao.getBooksByAuthor("Роулинг Джоан Кэтлин");
        Assert.assertEquals(expectedBooks.size(), actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(expectedBooks.get(0));
        assertThat(actualBooks.get(1)).usingRecursiveComparison().isEqualTo(expectedBooks.get(1));
    }

    @Test
    public void testGetBooksByGenre() {
        List<Book> expectedBooks = List.of(
                new Book(4, "Гарри Поттер и философский камень", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2002, 399, "Твердый переплет", 2290, 15),
                new Book(5, "Гарри Поттер и Кубок огня", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2007, 627, "Твердый переплет", 2080, 4)
        );
        List<Book> actualBooks = bookDao.getBooksByGenre("Роман, фэнтези");
        Assert.assertEquals(expectedBooks.size(), actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(expectedBooks.get(0));
        assertThat(actualBooks.get(1)).usingRecursiveComparison().isEqualTo(expectedBooks.get(1));
    }

    @Test
    public void testGetBooksByPublishingHouse() {
        List<Book> expectedBooks = List.of(
                new Book(4, "Гарри Поттер и философский камень", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2002, 399, "Твердый переплет", 2290, 15),
                new Book(5, "Гарри Поттер и Кубок огня", "Роулинг Джоан Кэтлин", "Роман, фэнтези", "Росмэн", 2007, 627, "Твердый переплет", 2080, 4)
        );
        List<Book> actualBooks = bookDao.getBooksByPublishingHouse("Росмэн");
        Assert.assertEquals(expectedBooks.size(), actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(expectedBooks.get(0));
        assertThat(actualBooks.get(1)).usingRecursiveComparison().isEqualTo(expectedBooks.get(1));
    }

    @Test
    public void testGetBooksByPublishingYear() {
        List<Book> expectedBooks = List.of(
                new Book(6, "Таймлесс. Рубиновая книга", "Керстин Гир", "Фантастика", "Робинс", 2021, 360, "Твердый переплет", 690, 38),
                new Book(9, "Человек из ресторана", "Иван Сергеевич Шмелев", "Повести, рассказы", "Пальмира", 2021, 311, "Мягкая обложка", 840, 18),
                new Book(12, "Самый богатый человек в Вавилоне", "Джордж Сэмюэль Клейсон", "Бизнес-беллетристика", "Попурри", 2021, 160, "Мягкая обложка", 590, 39)
        );
        List<Book> actualBooks = bookDao.getBooksByPublishingYear(2021);
        Assert.assertEquals(expectedBooks.size(), actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(expectedBooks.get(0));
        assertThat(actualBooks.get(1)).usingRecursiveComparison().isEqualTo(expectedBooks.get(1));
    }

    @Test
    public void testGetBookPrice() {
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        Assert.assertEquals(book.getPrice(), bookDao.getBookPrice(book));
        bookDao.deleteBook(book);
    }

    @Test
    public void testGetBookAmount() {
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        Assert.assertEquals(book.getAmount(), bookDao.getBookAmount(book));
        bookDao.deleteBook(book);
    }
}
