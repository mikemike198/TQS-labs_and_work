import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CustumerInfo {

    private WebDriver driver;

    @FindBys(@FindBy(how = How.CSS, using = ".btn-primary"))
    private List<WebElement> numberOfButtons;

    @FindBy(how = How.ID, using = "inputName")
    private WebElement name;

    @FindBy( how = How.ID, using = "address")
    private WebElement address;

    @FindBy(how = How.ID, using = "city")
    private WebElement city;

    @FindBy(how = How.ID, using = "state")
    private WebElement state;

    @FindBy(how = How.ID, using = "zipCode")
    private WebElement zipCode;

    @FindBy(how = How.ID, using = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(how = How.ID, using = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(how = How.CSS, using = ".btn-primary")
    private WebElement confirmButton;

    public CustumerInfo(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void insertName() {
        name.click();
        name.sendKeys("Miguel");
    }

    public void insertAddress() {
        address.click();
        address.sendKeys("Rua Prof Jose Maria da Silva Tavares n81");
    }

    public void insertCity() {
        city.click();
        city.sendKeys("Avanca");
    }

    public void insertState() {
        state.click();
        state.sendKeys("Aveiro");
    }

    public void insertZipCode() {
        zipCode.click();
        zipCode.sendKeys("3860-099");
    }

    public void insertCreditCardNumber() {
        creditCardNumber.click();
        creditCardNumber.sendKeys("123455667788");
    }

    public void insertNameOnCard() {
        nameOnCard.click();
        nameOnCard.sendKeys("Miguel Marques");
    }

    public void confirm() {
        confirmButton.click();
    }

    public int getNumberOfButtons() {
        return numberOfButtons.size();
    }



}
