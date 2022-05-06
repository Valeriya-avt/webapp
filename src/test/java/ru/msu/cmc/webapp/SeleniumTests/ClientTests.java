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
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ShoppingCartDaoImpl;
import ru.msu.cmc.webapp.Dao.OrderDao;
import ru.msu.cmc.webapp.Dao.ShoppingCartDao;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Models.ShoppingCart;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ClientTests {
    String URL = "http://localhost:8080/";
    WebDriver driver;
    WebDriverWait wait;
    ClientDao clientDao = new ClientDaoImpl();
    BookDao bookDao = new BookDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();
    Client client = new Client("Елизавета", "Иванова", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanova", "erbof789uh");

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
    public void indexBookstoreButtonTest() {
        driver.get(URL);
        WebElement button = wait.until(visibilityOfElementLocated(By.id("index_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Index page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("index_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Index page");
    }

    @Test(priority = 2)
    public void registrationButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_registration_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("registration_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Registration page");

        driver.findElement(By.id("clientNameForRegistration")).sendKeys(client.getName());
        driver.findElement(By.id("clientSurnameForRegistration")).sendKeys(client.getSurname());
        driver.findElement(By.id("clientAddressForRegistration")).sendKeys(client.getAddress());
        driver.findElement(By.id("clientPhoneNumberForRegistration")).sendKeys(client.getPhone_number());
        driver.findElement(By.id("clientEmailForRegistration")).sendKeys(client.getEmail());
        driver.findElement(By.id("clientLoginForRegistration")).sendKeys(client.getClient_login());
        driver.findElement(By.id("clientPasswordForRegistration")).sendKeys(client.getClient_password());

        button = wait.until(visibilityOfElementLocated(By.id("registration_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("congratulations_on_registration_catalog_button")));
        Assert.assertEquals(driver.getTitle(), "Congratulations on registration page");

        Assert.assertEquals(client.getClient_login(), clientDao.getClientByLogin(client.getClient_login()).getClient_login());

        button = wait.until(visibilityOfElementLocated(By.id("congratulations_on_registration_catalog_button")));
        button.click();
        wait.until(stalenessOf(button));

        button = wait.until(visibilityOfElementLocated(By.id("books_registration_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");

        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("registration_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Registration page");

        driver.findElement(By.id("clientNameForRegistration")).sendKeys(client.getName());
        driver.findElement(By.id("clientSurnameForRegistration")).sendKeys(client.getSurname());
        driver.findElement(By.id("clientAddressForRegistration")).sendKeys(client.getAddress());
        driver.findElement(By.id("clientPhoneNumberForRegistration")).sendKeys(client.getPhone_number());
        driver.findElement(By.id("clientEmailForRegistration")).sendKeys(client.getEmail());
        driver.findElement(By.id("clientLoginForRegistration")).sendKeys(client.getClient_login());
        driver.findElement(By.id("clientPasswordForRegistration")).sendKeys(client.getClient_password());

        button = wait.until(visibilityOfElementLocated(By.id("registration_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("error_page_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Error page");
    }

    @Test(priority = 3)
    public void bookReferenceTest() {
        BookDao bookDao = new BookDaoImpl();
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");
        driver.get(URL + "book?book_id=" + "1");
        wait.until(visibilityOfElementLocated(By.id("book_info_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Book page");
        Book book = bookDao.getBookByID(1);
        String info = driver.findElement(By.id("book_table")).getText();
        Assert.assertTrue(info.contains(book.getTitle()));
        Assert.assertTrue(info.contains(book.getAuthors()));
        Assert.assertTrue(info.contains(book.getGenre()));
        Assert.assertTrue(info.contains(book.getPublishing_house()));
        Assert.assertTrue(info.contains(String.valueOf(book.getPublishing_year())));
        Assert.assertTrue(info.contains(String.valueOf(book.getNum_of_pages())));
        Assert.assertTrue(info.contains(book.getCover_type()));
        Assert.assertTrue(info.contains(String.valueOf(book.getPrice())));
        Assert.assertTrue(info.contains(String.valueOf(book.getAmount())));
    }

    @Test(priority = 4)
    public void errorLogInTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_log_in_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("log_in_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Log in page");
        driver.findElement(By.id("log_in_client_login")).sendKeys("wrong_login");
        driver.findElement(By.id("log_in_client_password")).sendKeys("wrong_password");

        button = wait.until(visibilityOfElementLocated(By.id("log_in_button")));
        button.click();
        wait.until(stalenessOf(button));

        button = wait.until(visibilityOfElementLocated(By.id("error_page_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Error page");
        button.click();
        wait.until(stalenessOf(button));
    }

    @Test(priority = 5)
    public void logInTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_log_in_button")));
        Assert.assertEquals(driver.getTitle(), "Books page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("log_in_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Log in page");
        driver.findElement(By.id("log_in_client_login")).sendKeys(client.getClient_login());
        driver.findElement(By.id("log_in_client_password")).sendKeys(client.getClient_password());

        button = wait.until(visibilityOfElementLocated(By.id("log_in_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("books_for_client_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
    }

    @Test(priority = 6)
    public void booksForClientRootPageButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
    }

    @Test(priority = 7)
    public void booksForClientPersonalPageButtonTest() {
        driver.get(URL + "books");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("personal_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("personal_page_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Personal page");
        String table = driver.findElement(By.id("client_table")).getText();
        Assert.assertTrue(table.contains(client.getName()));
        Assert.assertTrue(table.contains(client.getSurname()));
        Assert.assertTrue(table.contains(client.getAddress()));
        Assert.assertTrue(table.contains(client.getPhone_number()));
        Assert.assertTrue(table.contains(client.getEmail()));
        Assert.assertTrue(table.contains(client.getClient_login()));

        button = wait.until(visibilityOfElementLocated(By.id("client_edit_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("edit_client_bookstore_page")));
        Assert.assertEquals(driver.getTitle(), "Edit client page");

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("surname")).clear();
        driver.findElement(By.id("address")).clear();
        driver.findElement(By.id("phone_number")).clear();

        driver.findElement(By.id("name")).sendKeys("Мирослава");
        driver.findElement(By.id("surname")).sendKeys("Петрова");
        driver.findElement(By.id("address")).sendKeys("Москва");
        driver.findElement(By.id("phone_number")).sendKeys("+79259255759");

        button = wait.until(visibilityOfElementLocated(By.id("edit_client_save_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("personal_page_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Personal page");
        table = driver.findElement(By.id("client_table")).getText();
        Assert.assertTrue(table.contains("Мирослава"));
        Assert.assertTrue(table.contains("Петрова"));
        Assert.assertTrue(table.contains("Москва"));
        Assert.assertTrue(table.contains("+79259255759"));
        Assert.assertTrue(table.contains(client.getEmail()));
        Assert.assertTrue(table.contains(client.getClient_login()));
    }

    @Test(priority = 8)
    public void bookForClientTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");

        driver.get(URL + "book?book_id=" + "1");
        wait.until(visibilityOfElementLocated(By.id("book_for_client_bookstore_page")));
        Assert.assertEquals(driver.getTitle(), "Book for client page");

        Book book = bookDao.getBookByID(1);
        String table = driver.findElement(By.id("bookForClientTable")).getText();
        Assert.assertTrue(table.contains(book.getTitle()));
        Assert.assertTrue(table.contains(book.getAuthors()));
        Assert.assertTrue(table.contains(book.getGenre()));
        Assert.assertTrue(table.contains(book.getPublishing_house()));
        Assert.assertTrue(table.contains(String.valueOf(book.getPublishing_year())));
        Assert.assertTrue(table.contains(String.valueOf(book.getNum_of_pages())));
        Assert.assertTrue(table.contains(book.getCover_type()));
        Assert.assertTrue(table.contains(String.valueOf(book.getPrice())));
        Assert.assertTrue(table.contains(String.valueOf(book.getAmount())));

        WebElement button = wait.until(visibilityOfElementLocated(By.id("add_book_to_shopping_cart_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("create_shopping_cart_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Create shopping cart page");
        driver.findElement(By.id("amount")).sendKeys("1");
        button = wait.until(visibilityOfElementLocated(By.id("create_shopping_cart_button")));
        button.click();
        wait.until(stalenessOf(button));

        wait.until(visibilityOfElementLocated(By.id("congratulations_on_create_order_button")));
        Assert.assertEquals(driver.getTitle(), "Congratulations on create order page");
    }

    @Test(priority = 9)
    public void clientOrdersButtonTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");

        WebElement button = wait.until(visibilityOfElementLocated(By.id("client_orders_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("client_orders_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Client orders page");

        Order order = orderDao.getOrdersByClientId(clientDao.getClientByLogin(client.getClient_login())).get(0);
        String table = driver.findElement(By.id("client_order")).getText();
        Assert.assertTrue(table.contains(String.valueOf(order.getOrder_id())));
        Assert.assertTrue(table.contains(String.valueOf(order.getOrder_time())));
        Assert.assertTrue(table.contains(String.valueOf(order.getDelivery_time())));
        Assert.assertTrue(table.contains(String.valueOf(order.getOrder_price())));
        Assert.assertTrue(table.contains(order.getStatus()));

        driver.get(URL + "order?order_id=" + String.valueOf(order.getOrder_id()));
        wait.until(visibilityOfElementLocated(By.id("open_order_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Open order page");

        String open_order_table = driver.findElement(By.id("open_order")).getText();
        Assert.assertTrue(open_order_table.contains(String.valueOf(order.getOrder_id())));
        Assert.assertTrue(open_order_table.contains(String.valueOf(order.getOrder_time())));
        Assert.assertTrue(open_order_table.contains(String.valueOf(order.getDelivery_time())));
        Assert.assertTrue(open_order_table.contains(String.valueOf(order.getOrder_price())));
        Assert.assertTrue(open_order_table.contains(order.getStatus()));
    }

    @Test(priority = 10)
    public void shoppingCartButtonTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("shopping_cart_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("shopping_cart_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Shopping cart page");
        String table = driver.findElement(By.id("shopping_cart")).getText();
        Order order = orderDao.getOrdersByClientId(clientDao.getClientByLogin(client.getClient_login())).get(0);
        ShoppingCart shoppingCart = shoppingCartDao.getShoppingCartsByOrderId(order).get(0);
        Assert.assertTrue(table.contains(String.valueOf(shoppingCart.getOrder_id().getOrder_id())));
        Assert.assertTrue(table.contains(shoppingCart.getBook_id().getTitle()));
        Assert.assertTrue(table.contains(String.valueOf(shoppingCart.getPrice())));
        Assert.assertTrue(table.contains(String.valueOf(shoppingCart.getAmount())));
    }

    @Test(priority = 11)
    public void checkoutTest() {
        Order order = orderDao.getOrdersByClientId(clientDao.getClientByLogin(client.getClient_login())).get(0);
        driver.get(URL + "order?order_id=" + String.valueOf(order.getOrder_id()));
        wait.until(visibilityOfElementLocated(By.id("open_order_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Open order page");

        WebElement button = wait.until(visibilityOfElementLocated(By.id("checkout_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("congratulations_on_checkout_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Congratulations on checkout page");
    }

    @Test(priority = 12)
    public void closeOrderPageTest() {
        Order order = orderDao.getOrdersByClientId(clientDao.getClientByLogin(client.getClient_login())).get(0);
        driver.get(URL + "order?order_id=" + String.valueOf(order.getOrder_id()));
        wait.until(visibilityOfElementLocated(By.id("close_order_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Close order page");

        String close_order_table = driver.findElement(By.id("close_order")).getText();
        Assert.assertTrue(close_order_table.contains(String.valueOf(order.getOrder_id())));
        Assert.assertTrue(close_order_table.contains(String.valueOf(order.getOrder_time())));
        Assert.assertTrue(close_order_table.contains(String.valueOf(order.getDelivery_time())));
        Assert.assertTrue(close_order_table.contains(String.valueOf(order.getOrder_price())));
        Assert.assertTrue(close_order_table.contains("Закрыт"));
    }

    @Test(priority = 13)
    public void emptyShoppingCartButtonTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("shopping_cart_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("shopping_cart_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Shopping cart page");
        String table = driver.findElement(By.id("shopping_cart")).getText();
        Assert.assertTrue(table.contains("Каталог пуст"));
    }

    @Test(priority = 14)
    public void searchTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("search_button")));
        driver.findElement(By.id("search_input")).sendKeys("Гарри Поттер и философский камень");
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("books_for_client_bookstore_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        String table = driver.findElement(By.id("books_table")).getText();
        System.out.println("HELLO");
        System.out.println(table);
        Book book = bookDao.getBookByID(4);
        Assert.assertTrue(table.contains(book.getTitle()));
        Assert.assertTrue(table.contains(book.getAuthors()));
        Assert.assertTrue(table.contains(String.valueOf(book.getPrice())));
    }

    @Test(priority = 15)
    public void logOutTest() {
        driver.get(URL + "books");
        wait.until(visibilityOfElementLocated(By.id("books_for_client_root_page_button")));
        Assert.assertEquals(driver.getTitle(), "Books for client page");
        WebElement button = wait.until(visibilityOfElementLocated(By.id("log_out_button")));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.id("index_bookstore_button")));
    }

    @AfterClass
    public void end() {
        clientDao.deleteClient(client);
        driver.quit();
    }
}
