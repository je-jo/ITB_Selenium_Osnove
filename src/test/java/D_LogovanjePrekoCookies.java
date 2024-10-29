import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class D_LogovanjePrekoCookies {

    /*
    Domaci:
    Koristeci anotacije, ulogujte se na demoqa (https://demoqa.com/ -> Book Store Application) preko cookies-a
    */

    WebDriver driver;
    String homepage = "https://demoqa.com/";
    JavascriptExecutor js;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void pageSetup() {
        driver.navigate().to(homepage);
    }

    @Test
    public void testCookieLogin() throws InterruptedException {

        js.executeScript("window.scrollBy(0,250), ''");

        WebElement card = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div/div[6]"));
        card.click();

        String cookieToken = "token";
        String cookieTokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6Implam8iLCJwYXNzd29yZCI6InNpZnJhRjIyISIsImlhdCI6MTcyODIwNjA2MH0.VXeWVcA-ITs2AIZtJ4dfg-BJ7F-OFTIDervcZhvHJqw";
        Cookie cookie1 = new Cookie(cookieToken, cookieTokenValue);
        driver.manage().addCookie(cookie1);
        String cookieId = "userID";
        String cookieIdValue = "cf30ae9a-9bc7-45b6-9d04-9921f5f9565e";
        Cookie cookie2 = new Cookie(cookieId, cookieIdValue);
        String cookieUsername = "userName";
        String cookieUsernameValue = "jejo";
        Cookie cookie3 = new Cookie(cookieUsername, cookieUsernameValue);
        String cookieExpires = "expires";
        String cookieExpiresValue = "2024-10-23T09%3A12%3A15.851Z";
        Cookie cookie4 = new Cookie(cookieExpires, cookieExpiresValue);
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);
        driver.manage().addCookie(cookie3);
        driver.manage().addCookie(cookie4);

        Thread.sleep(6000);
        driver.navigate().refresh();

        // ------------- asertacije

        WebElement usernameText = driver.findElement(By.id("userName-value"));
        Assert.assertEquals(usernameText.getText(), cookieUsernameValue);

        WebElement btnLogout = driver.findElement(By.id("submit"));
        Assert.assertTrue(btnLogout.isDisplayed());

    }

    @AfterMethod
    public void deleteCookies() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
         driver.quit();
    }

}
