import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;

public class B_LogovanjeSaucedemo {
    public static void main(String[] args) throws InterruptedException {
        /*
        Domaci
        Testirati bar 3 razlicita test case-a za logovanje na sledecem linku:
        https://www.saucedemo.com
         */

        // 1. standard user

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        String homepage = "https://www.saucedemo.com/";

        driver.navigate().to(homepage);

        String standardUser = "standard_user";
        String password = "secret_sauce";


        WebElement inputUsername = driver.findElement(By.id("user-name"));
        inputUsername.clear();
        inputUsername.sendKeys(standardUser);

        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.clear();
        inputPassword.sendKeys(password);

        WebElement btnLogin = driver.findElement(By.id("login-button"));
        btnLogin.click();

        // ----asertacija standard user

        String loggedInUrl = homepage + "inventory.html";
        Assert.assertEquals(loggedInUrl, driver.getCurrentUrl());

        WebElement btnMenu = driver.findElement(By.id("react-burger-menu-btn"));
        btnMenu.click();

        WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
        Wait.until(ExpectedConditions.visibilityOf(logoutLink));
        Assert.assertTrue(logoutLink.isDisplayed());

        logoutLink.click();

        // --- 2. locked out user

        Wait.until(ExpectedConditions.urlToBe(homepage));

        inputUsername = driver.findElement(By.id("user-name"));
        inputPassword = driver.findElement(By.id("password"));
        btnLogin = driver.findElement(By.id("login-button"));

        String lockedOutUser = "locked_out_user";

        inputUsername.clear();
        inputUsername.sendKeys(lockedOutUser);

        inputPassword.clear();
        inputPassword.sendKeys(password);

        btnLogin.click();

        // ---- asertacija locked out

        Assert.assertEquals(homepage, driver.getCurrentUrl());

        WebElement error = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(error.isDisplayed());

        String errorText = "Epic sadface: Sorry, this user has been locked out.";
        Assert.assertEquals(error.getText(), errorText);

        // 3. problem user

        String problemUser = "problem_user";

        inputUsername.clear();
        inputUsername.sendKeys(problemUser);

        inputPassword.clear();
        inputPassword.sendKeys(password);

        btnLogin.click();

        // ---- asertacija problem user

        Wait.until(ExpectedConditions.urlToBe(loggedInUrl));
        Assert.assertEquals(loggedInUrl, driver.getCurrentUrl());

        ArrayList<WebElement> images = (ArrayList<WebElement>) driver.findElements(By.cssSelector("img[class='inventory_item_img']"));

        for (int i = 0; i < images.size() - 1; i++) {
            Assert.assertEquals(images.get(i).getAttribute("src"), images.get(i + 1).getAttribute("src"));
        }

    }
}
