import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SeleniumJupiter.class)
class ChromeAndFirefoxJupiterTest {

    @Test
    void testWithOneChrome(ChromeDriver chromeDriver) {
        // Use Chrome in this test
        chromeDriver.get("https://blazedemo.com/");
        chromeDriver.manage().window().setSize(new Dimension(889, 749));
        assertThat(chromeDriver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));
    }

    @Test
    void testWithFirefox(FirefoxDriver firefoxDriver) {
        // Use Firefox in this test
        firefoxDriver.get("https://blazedemo.com/");
        firefoxDriver.manage().window().setSize(new Dimension(889, 749));
        assertThat(firefoxDriver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));
    }

    @Test
    void testWithChromeAndFirefox(ChromeDriver chromeDriver,
                                  FirefoxDriver firefoxDriver) {
        // Use Chrome and Firefox in this test
        chromeDriver.get("https://blazedemo.com/");
        chromeDriver.manage().window().setSize(new Dimension(889, 749));
        assertThat(chromeDriver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));

        firefoxDriver.get("https://blazedemo.com/");
        firefoxDriver.manage().window().setSize(new Dimension(889, 749));
        assertThat(firefoxDriver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));

    }

    // Test with headless browser
    @Test
    void test(HtmlUnitDriver driver) {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(889, 749));
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));

    }

}
