package ua.pt.tqs.HomeWorkTQS.WebTests;// Generated by Selenium IDE
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;


@ExtendWith(SeleniumJupiter.class)
public class ErrorTest {
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
  public void error() {
    HomePage home = new HomePage(driver);
    home.clickSearchButton();

    SearchPage search = new SearchPage(driver);
    search.insertInfoInForm("Lisboa", "Aveiro", "Portugal");
    search.clickButton();

    driver.findElement(By.id("goHome")).click();
  }
}
