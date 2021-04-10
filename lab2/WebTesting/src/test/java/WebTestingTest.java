
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class WebTestingTest {

    WebDriver browser;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/Users/miguelmarques/Downloads/geckodriver");
        browser = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        browser.close();
    }

    @Test
    public void site_header_is_on_home_page() {
        browser.get("https://www.saucelabs.com");
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));
        assertTrue((href.isDisplayed()));
    }

    @Test
    public void test_navigate_to_Page() {
        browser.get("https://www.saucelabs.com");
        browser.navigate().to("https://saucelabs.com/pricing");
        String url = browser.getCurrentUrl();
        assertEquals("https://saucelabs.com/pricing", url);
    }

    @Test
    public void test_if_image_exists() {
        browser.get("https://www.saucelabs.com");
        WebElement imageDiv = browser.findElement(By.className("image"));
        assertTrue((imageDiv.isDisplayed()));
    }
}
