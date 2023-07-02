import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class Homework4 {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("http://shop.pragmatic.bg/admin");
    }

    @Test
    public void testDropDown() {
        WebElement username = driver.findElement(By.xpath("//*[@id=\"input-username\"]"));
        username.sendKeys("admin");
        WebElement password = driver.findElement(By.cssSelector("#input-password"));
        password.sendKeys("parola123!");
        WebElement login = driver.findElement(By.cssSelector("#content > div > div > div > div > div.panel-body > form > div.text-right > button"));
        login.click();

        WebElement loggedUser = driver.findElement(By.cssSelector("span[class='hidden-xs hidden-sm hidden-md']"));
        String messageText = loggedUser.getText();
        Assert.assertEquals(messageText, "Logout");

        WebElement elementSales = driver.findElement(By.cssSelector("#menu-sale > a"));
        elementSales.click();
        WebElement elementOrders = driver.findElement(By.xpath("//*[@id=\"collapse4\"]/li[1]/a"));
        elementOrders.click();

        WebElement filterElement = driver.findElement(By.cssSelector("#filter-order > div > div.panel-heading > h3"));
        String text = filterElement.getText();
        Assert.assertEquals(text, "Filter");

        WebElement dropDown = driver.findElement(By.id("input-order-status"));
        Select orderStatusElement = new Select(dropDown);
        assertFalse(orderStatusElement.isMultiple());
        assertEquals(orderStatusElement.getOptions().size(), 16);

        ArrayList<String> actualOptions = new ArrayList<>();

        List<WebElement> options = orderStatusElement.getOptions();
        for (WebElement option : options) {
//            actualOptions.add(option.getAttribute("value"));
//            actualOptions.add(option.getText());
            String optionText = option.getText();
            String index = option.getAttribute("index");
            System.out.print("Index " + index + ",");
            System.out.println("Text: " + optionText);
        }
        // System.out.println(actualOptions);
    }

    @Test
    public void ActionTest() {
        driver.get("http://pragmatic.bg/automation/lecture13/Config.html");

        WebElement airBagsCheckbox = driver.findElement(By.name("airbags"));
        WebElement parkingSensorCheckbox = driver.findElement(By.name("parksensor"));

        Actions builder = new Actions(driver);

        if (airBagsCheckbox.isSelected()) {
            airBagsCheckbox.click();
        }

        if (parkingSensorCheckbox.isSelected()) {
            parkingSensorCheckbox.click();
        }
        assertFalse(airBagsCheckbox.isSelected());
        assertFalse(parkingSensorCheckbox.isSelected());

        builder.click(airBagsCheckbox).perform();
        builder.click(parkingSensorCheckbox).perform();

        assertTrue(airBagsCheckbox.isSelected());
        assertTrue(parkingSensorCheckbox.isSelected());

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

