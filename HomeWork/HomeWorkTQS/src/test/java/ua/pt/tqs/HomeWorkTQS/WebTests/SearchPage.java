package ua.pt.tqs.HomeWorkTQS.WebTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    private WebDriver driver;

    @FindBy(how = How.ID, using = "city")
    private WebElement cityField;

    @FindBy(how = How.ID, using = "state")
    private WebElement stateField;

    @FindBy(how = How.ID, using = "country")
    private WebElement countryField;

    @FindBy(how = How.CSS, using = "button")
    private WebElement button;

    @FindBy(how = How.CSS, using = ".temperature > div > div:nth-child(1)")
    private WebElement displayedCity;

    @FindBy(how = How.ID, using = "countryName")
    private WebElement displayedCountry;


    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void insertInfoInForm(String city, String state, String country) {
        cityField.click();
        cityField.sendKeys(city);

        stateField.click();
        stateField.sendKeys(state);

        countryField.click();
        countryField.sendKeys(country);
    }

    public void clickButton() {
        button.click();
    }

    public String getCityValue()  {
        return displayedCity.getText();
    }

    public String getCountryValue()  {
        return displayedCountry.getText();
    }

}
