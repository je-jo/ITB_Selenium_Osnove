import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class C_DodavanjeUKorpu {
    public static void main(String[] args) {
        /*
        Zadatak za domaci:
        https://www.amazon.com/Selenium-Framework-Design-Data-Driven-Testing/dp/1788473574/ref=sr_1_2?dchild=1&keywords=selenium+test&qid=1631829742&sr=8-2
        Testirati dodavanje knjige u korpu i da li se knjiga obrise kada obrisete kolacice

        Ne treba vam nalog da biste dodali i brisali iz korpe

        Brisanjem kolacica koristite ovu liniju koda

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
         */

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        String url = "https://www.amazon.com/Selenium-Framework-Design-Data-Driven-Testing/dp/1788473574/ref=sr_1_2?dchild=1&keywords=selenium+test&qid=1631829742&sr=8-2";

        driver.navigate().to(url);

        WebElement btnAddToCart = driver.findElement(By.id("add-to-cart-button"));
        btnAddToCart.click();

        WebElement linkToCart = driver.findElement(By.linkText("Go to Cart"));
        linkToCart.click();

        String bookTitle = "Selenium Framework Design in Data-Driven Testing: Build data-driven test frameworks using Selenium WebDriver, AppiumDriver, Java, and TestNG";

        WebElement itemTitle = driver.findElement(By.className("a-truncate-cut"));

        // ----- asertacija da je knjiga u korpi

        Assert.assertEquals(bookTitle, itemTitle.getText());

        WebElement btnDelete = driver.findElement(By.cssSelector("input[value='Delete']"));

        Assert.assertTrue(btnDelete.isDisplayed());

        // --- brisanje knjige

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        // --- asertacija da je knjiga obrisana

        WebElement msgEmpty = driver.findElement(By.className("sc-your-amazon-cart-is-empty"));

        Assert.assertTrue(msgEmpty.isDisplayed());

        boolean isPresent = false;

        try {
            isPresent = btnDelete.isDisplayed();
        } catch (Exception e) {
        }

        Assert.assertFalse(isPresent);

    }
}
