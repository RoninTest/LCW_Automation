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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;
import java.util.Locale;


public class BaseTest {

    /**********LOCATORS**************/
    static WebDriver driver;
    static WebDriverWait wait;
    static Actions actions;
    static String baseUrl = "https://www.lcwaikiki.com/tr-TR/TR";
    static WebElement manCategory;
    static WebElement manCategorySelecting;
    static WebElement manCategoryControl;
    static WebElement mainProductSelecting;
    static WebElement manProductSelecting;
    static WebElement tailleButton;
    static WebElement tailleButtonControl;
    static WebElement addToCartButton;
    static WebElement addToCartButtonControl;
    static WebElement myCartButton;
    static WebElement backToMainPage;
    static WebElement cokkieButton;
    /**********************************/


    /*********PROJECT SETTING**********/
    @BeforeClass
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
    public void addToCartAproduct()  {
        driver.get(baseUrl);
            Assert.assertEquals(driver.getCurrentUrl(),baseUrl);
        cokkieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='ANLADIM']")));
        cokkieButton.click();
        manCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='ERKEK'])[1]")));
        actions.moveToElement(manCategory).build().perform();
        manCategoryControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Tüm Erkek Ürünleri'])[1]")));
            Assert.assertTrue(manCategoryControl.isDisplayed());
        manCategorySelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Erken Kış Fırsatları'])[2]")));
        manCategorySelecting.click();
        String beklenenErkekKategoriSecimUrl = "https://www.lcwaikiki.com/tr-TR/TR/etiket/erken-kis-firsatlari-erkek";
        String gerceklesenErkekKategoriSecimUrl= driver.getCurrentUrl();
            Assert.assertEquals(gerceklesenErkekKategoriSecimUrl, beklenenErkekKategoriSecimUrl);
        mainProductSelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='Sweatshirt'])[6]")));
        mainProductSelecting.click();
        String actual = driver.findElement(By.xpath("//h3[contains(text(),'Sepette 30%a Varan İndirim!!')]")).getText();
        String expected="Sepette 30%a Varan İndirim!!";
            Assert.assertEquals(actual, expected);
        manProductSelecting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//h5[text()='Kapüşonlu Uzun Kollu Fermuarlı Erkek Sweatshirt'])[1]")));
        manProductSelecting.click();
        String beklenenErkekUrunSecimUrl = "https://www.lcwaikiki.com/tr-TR/TR/urun/LC-WAIKIKI/erkek/Sweatshirt/5105153/1845646";
        String gerceklesenErkekUrunSecimUrl = driver.getCurrentUrl();
            Assert.assertEquals(gerceklesenErkekUrunSecimUrl, beklenenErkekUrunSecimUrl);
        tailleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='XS'])[2]")));
        tailleButton.click();
        addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='SEPETE EKLE'])[2]")));
        addToCartButton.click();
        myCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()='Sepetim'])[1]")));
        actions.moveToElement(myCartButton).build().perform();
        tailleButtonControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//p[text()='XS'])[1]")));
            Assert.assertTrue(tailleButtonControl.isDisplayed());
        addToCartButtonControl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Sepetinize 1 adet ürün eklenmiştir.']")));
            Assert.assertTrue(addToCartButtonControl.isDisplayed());
        actions.click(myCartButton).build().perform();
        String beklenenSepetimButonuKontrolUrl = "https://www.lcwaikiki.com/tr-TR/TR/sepetim";
        String gerceklesenSepetimButonuKontrolUrl = driver.getCurrentUrl();
            Assert.assertEquals(gerceklesenSepetimButonuKontrolUrl, beklenenSepetimButonuKontrolUrl);
        backToMainPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@class='main-header-logo'])[1]")));
        backToMainPage.click();
            Assert.assertEquals(driver.getCurrentUrl(),baseUrl);

    }

    @AfterClass
    public static void cikis()
    {
        driver.quit();
    }
}
