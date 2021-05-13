package ua.pt.tqs.HomeWorkTQS.WebTests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:8080/";

    @FindBy(how = How.LINK_TEXT, using = "Search")
    private WebElement button;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        driver.manage().window().setSize(new Dimension(1440, 875));
        PageFactory.initElements(driver, this);
    }

    public void clickSearchButton() {
        button.click();
    }


}
