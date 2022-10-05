package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;



public class BaseTest {

    /**********LOCATORS**************/
    /**********VARIABLES*************/

    static WebDriver driver;
    static WebDriverWait wait;
    static Actions actions;
    static String baseUrl = "https://www.lcwaikiki.com/tr-TR/TR";
    static WebElement manCategory;
    static String manCategoryXPath="(//a[text()='ERKEK'])[1]";
    static WebElement manCategorySelecting;
    static WebElement manCategoryControl;
    static String manCategoryControlXPath="(//a[text()='Tüm Erkek Ürünleri'])[1]";
    static WebElement mainProductSelecting;
    static String mainProductSelectingXPath="(//a[text()='Sweatshirt'])[6]";
    static WebElement manProductSelecting;
    static String manProductSelectingXPath="(//a[text()='Erken Kış Fırsatları'])[2]";
    static String manProductSelectingXPath_2="(//h5[text()='Kapüşonlu Uzun Kollu Fermuarlı Erkek Sweatshirt'])[1]";
    static WebElement bodyButton;
    static String bodyButtonXPath="(//a[text()='XS'])[2]";
    static WebElement bodyButtonControl;
    static String bodyButtonControlXPath="(//p[text()='XS'])[1]";
    static WebElement addToCartButton;
    static String addToCartButtonXPath="(//a[text()='SEPETE EKLE'])[2]";
    static WebElement addToCartButtonControl;
    static String addToCartButtonControlXpath="//div[text()='Sepetinize 1 adet ürün eklenmiştir.']";
    static WebElement myCartButton;
    static String myCartButtonXpath="(//span[text()='Sepetim'])[1]";
    static WebElement backToMainPage;
    static String backToMainPageXPath="(//a[@class='main-header-logo'])[1]";
    static WebElement cookieButton;
    static String cookieButtonXPath="//button[text()='ANLADIM']";
    static String bodyLinkText_actual1 ="Beden Tablosu";
    static String bodyLinkText_expected1 ="Beden Tablosu";
    static String atCart_30_actual ="//h3[contains(text(),'')]";
    static String atCart_30_expected ="Sepette 30%a Varan İndirim!!";
    static String myCartUrl="https://www.lcwaikiki.com/tr-TR/TR/sepetim";
    static String manCategoryUrl="https://www.lcwaikiki.com/tr-TR/TR/etiket/erken-kis-firsatlari-erkek";

    final static Logger logger = Logger.getLogger(BaseTest.class);

    /*********PROJECT SETTINGS**********/
    @BeforeTest
    public static void setting() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);

    }

    /**************TEST**************/
    @Test(priority = 1)
    @Description("Add to Cart a Product")
    public void addToCartaProduct()  {

        LocalDateTime date=LocalDateTime.now();
        DateTimeFormatter f=DateTimeFormatter.ofPattern("dd/MM/YYYY HH.mm");
        System.out.println(f.format(date));

        driver.get(baseUrl);
            Assert.assertEquals(driver.getCurrentUrl(),baseUrl);
        cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cookieButtonXPath)));
        cookieButton.click();
        manCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manCategoryXPath)));
        actions.moveToElement(manCategory).build().perform();
        manCategoryControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manCategoryControlXPath)));
            Assert.assertTrue(manCategoryControl.isDisplayed());
        manCategorySelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manProductSelectingXPath)));
        manCategorySelecting.click();
        String expectedManCategoryUrl = manCategoryUrl;
        String actualManCategoryUrl= driver.getCurrentUrl();
            Assert.assertEquals(actualManCategoryUrl, expectedManCategoryUrl);
        mainProductSelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(mainProductSelectingXPath)));
        mainProductSelecting.click();
        String actual = driver.findElement(By.xpath(atCart_30_actual)).getText();
        String expected=atCart_30_expected;
            Assert.assertEquals(actual, expected);
        manProductSelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manProductSelectingXPath_2)));
        manProductSelecting.click();
        String actual1 = driver.findElement(By.linkText(bodyLinkText_actual1)).getText();
        String expected1=bodyLinkText_expected1;
            Assert.assertEquals(actual1,expected1);
        bodyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(bodyButtonXPath)));
        bodyButton.click();
        addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(addToCartButtonXPath)));
        addToCartButton.click();
        myCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(myCartButtonXpath)));
        actions.moveToElement(myCartButton).build().perform();
        bodyButtonControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(bodyButtonControlXPath)));
            Assert.assertTrue(bodyButtonControl.isDisplayed());
        addToCartButtonControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(addToCartButtonControlXpath)));
            Assert.assertTrue(addToCartButtonControl.isDisplayed());
        actions.click(myCartButton).build().perform();
        String expectedCartButtonUrl = myCartUrl;
        String actualCartButtonUrl = driver.getCurrentUrl();
            Assert.assertEquals(actualCartButtonUrl, expectedCartButtonUrl);
        backToMainPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(backToMainPageXPath)));
        backToMainPage.click();
            Assert.assertEquals(driver.getCurrentUrl(),baseUrl);

            logger.info("Test is done");
    }


    @AfterClass
    public static void exit() {driver.quit();}
}









