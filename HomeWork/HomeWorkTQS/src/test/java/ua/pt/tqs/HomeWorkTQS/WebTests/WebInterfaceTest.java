package ua.pt.tqs.HomeWorkTQS.WebTests;// Generated by Selenium IDE
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

@ExtendWith(SeleniumJupiter.class)
public class WebInterfaceTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp(FirefoxDriver driver) {
    this.driver = driver;
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void webInterface() {
    HomePage home = new HomePage(driver);
    home.clickSearchButton();

    SearchPage search = new SearchPage(driver);
    search.insertInfoInForm("Aveiro", "Aveiro", "Portugal");
    search.clickButton();

    MatcherAssert.assertThat(search.getCityValue(), is("Aveiro"));
    MatcherAssert.assertThat(search.getCountryValue(), is("Portugal"));

    search.insertInfoInForm("Madrid", "Madrid", "Spain");
    search.clickButton();

    MatcherAssert.assertThat(search.getCityValue(), is("Madrid"));
    MatcherAssert.assertThat(search.getCountryValue(), is("Spain"));
  }
}
