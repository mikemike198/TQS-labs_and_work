
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_URL = "https://blazedemo.com/";

    @FindBy(how = How.CSS, using = "h1")
    private WebElement title;


    @FindAll({@FindBy(how = How.NAME, using = "fromPort"),
            @FindBy(how = How.XPATH, using = "//option[. = 'Boston']")})
    private WebElement departureCity;

    @FindBy(how = How.CSS, using = ".form-inline:nth-child(1) > option:nth-child(3)")
    private WebElement select1;

    @FindAll({@FindBy(how = How.NAME, using = "toPort"),
            @FindBy(how = How.XPATH, using = "//option[. = 'London']")})
    private  WebElement destinationCity;

    @FindBy(how = How.CSS, using = ".form-inline:nth-child(4) > option:nth-child(3)")
    private WebElement select2;

    @FindBy(how = How.CSS , using = ".btn-primary")
    private WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        driver.manage().window().setSize(new Dimension(889, 749));
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return title.getText();
    }

    public void selectDepartureCity() {
        departureCity.click();
        select1.click();
    }

    public void selectDestinationCity() {
        destinationCity.click();
        select2.click();
    }

    public void findFlights() {
        findFlightsButton.click();
    }
}
