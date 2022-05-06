package ru.msu.cmc.webapp.SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.AdminDao;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Dao.OrderDao;
import ru.msu.cmc.webapp.Models.Admin;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AdminTests {
    String URL = "http://localhost:8080/";
    WebDriver driver;
    WebDriverWait wait;
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    Admin admin = adminDao.getAdminByLogin("admin1");

    @BeforeClass
    public void settings() {
        final String chromeDriverPath = "C:\\Program Files\\chromedriver_win32\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();;
        driver.manage().timeouts().implicitlyWait(15, SECONDS);

        wait = new WebDriverWait(driver,15);
        driver.manage().timeouts().pageLoadTimeout(15, SECONDS);
    }

    @Test(priority = 1)
    public void adminLogInTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_log_in_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("log_in_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Log in page");
        driver.findElement(By.id("log_in_client_login")).sendKeys(admin.getAdmin_login());
        driver.findElement(By.id("log_in_client_password")).sendKeys(admin.getAdmin_password());

        button = wait.until(visibilityOfElementLocated(By.id("log_in_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("books_for_admin_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
    }

    @Test(priority = 2)
    public void booksForAdminRootPageButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_for_admin_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("books_for_admin_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
    }

    @Test(priority = 3)
    public void bookReferenceTest() {
        BookDao bookDao = new BookDaoImpl();
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_admin_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        driver.get(URL + "book?book_id=" + "1");
        wait.until(visibilityOfElementLocated(By.id("book_for_admin_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Book for admin page");
        Book book = bookDao.getBookByID(1);
        String info = driver.findElement(By.id("bookForAdminTable")).getText();
        Assert.assertTrue(info.contains(book.getTitle()));
        Assert.assertTrue(info.contains(book.getAuthors()));
        Assert.assertTrue(info.contains(book.getGenre()));
        Assert.assertTrue(info.contains(book.getPublishing_house()));
        Assert.assertTrue(info.contains(String.valueOf(book.getPublishing_year())));
        Assert.assertTrue(info.contains(String.valueOf(book.getNum_of_pages())));
        Assert.assertTrue(info.contains(book.getCover_type()));
        Assert.assertTrue(info.contains(String.valueOf(book.getPrice())));
        Assert.assertTrue(info.contains(String.valueOf(book.getAmount())));

        WebElement button = wait.until(visibilityOfElementLocated(By.id("update_book_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("update_book_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Update book page");

        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("1000.0");
        button = wait.until(visibilityOfElementLocated(By.id("save_update_book_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("book_for_admin_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Book for admin page");
    }

    @Test(priority = 4)
    public void createBookButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("create_book_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("create_book_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Create book page");

        driver.findElement(By.id("title")).sendKeys("Война и мир");
        driver.findElement(By.id("authors")).sendKeys("Лев Николаевич Толстой");
        driver.findElement(By.id("genre")).sendKeys("Роман-эпопея");
        driver.findElement(By.id("publishing_house")).sendKeys("Эксмо");
        driver.findElement(By.id("publishing_year")).sendKeys("2007");
        driver.findElement(By.id("num_of_pages")).sendKeys("287");
        driver.findElement(By.id("cover_type")).sendKeys("Твёрдый переплёт");
        driver.findElement(By.id("price")).sendKeys("480.0");
        driver.findElement(By.id("amount")).sendKeys("10");

        button = wait.until(visibilityOfElementLocated(By.id("add_book_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("congratulations_on_create_book_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Congratulations on create book page");
    }

    @Test(priority = 5)
    public void createClientButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("add_client_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("add_client_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Add client page");
        String table = driver.findElement(By.id("add_client_table")).getText();
        Book book = new Book("Война и мир", "Лев Николаевич Толстой", "Роман-эпопея", "Эксмо", 2007, 287, "Твёрдый переплёт", 480.0, 10);

        driver.findElement(By.id("name")).sendKeys("Кристина");
        driver.findElement(By.id("surname")).sendKeys("Смирнова");
        driver.findElement(By.id("address")).sendKeys("г. Волгоград");
        driver.findElement(By.id("phone_number")).sendKeys("+7925353859");
        driver.findElement(By.id("email")).sendKeys("niksmirnov@gmail.com");
        driver.findElement(By.id("client_login")).sendKeys("KSmirnova");
        driver.findElement(By.id("client_password")).sendKeys("passwordForNik");

        button = wait.until(visibilityOfElementLocated(By.id("add_client_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("congratulations_on_add_client_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Congratulations on add client page");
    }

    @Test(priority = 6)
    public void orderListPage() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("orders_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("orders_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Orders page");
        Client client = clientDao.getClientByLogin("AErokhin");
        Order order = orderDao.getOrdersByClientId(client).get(0);
        String table = driver.findElement(By.id("orders_table")).getText();
        Assert.assertTrue(table.contains(String.valueOf(order.getClient_id().getClient_id())));
        Assert.assertTrue(table.contains(String.valueOf(order.getOrder_time())));
        Assert.assertTrue(table.contains(String.valueOf(order.getDelivery_time())));
        Assert.assertTrue(table.contains(String.valueOf(order.getOrder_price())));
        Assert.assertTrue(table.contains(order.getStatus()));
    }

    @Test(priority = 7)
    public void clientListPage() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("client_list_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("client_list_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Client list page");
        Client client = clientDao.getClientByLogin("KSmirnova");
        String table = driver.findElement(By.id("clients_table")).getText();
        Assert.assertTrue(table.contains(String.valueOf(client.getClient_id())));
        Assert.assertTrue(table.contains(client.getName()));
        Assert.assertTrue(table.contains(client.getSurname()));
        Assert.assertTrue(table.contains(client.getPhone_number()));
        Assert.assertTrue(table.contains(client.getEmail()));
        Assert.assertTrue(table.contains(client.getClient_login()));
        driver.get(URL + "logged/personal?client_id=" + String.valueOf(client.getClient_id()));
        wait.until(visibilityOfElementLocated(By.id("client_for_admin_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Client for admin page");
        button = wait.until(visibilityOfElementLocated(By.id("delete_client_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("delete_client_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Delete client page");
    }

    @Test(priority = 8)
    public void logOutTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_admin_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for admin page");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("log_out_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("index_bookstore_button")));
    }

    @AfterClass
    public void end() {
        driver.quit();
    }
}
