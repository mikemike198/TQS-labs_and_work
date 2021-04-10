import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ListOfFlightsPage {

    private WebDriver driver;

    @FindBy(how = How.LINK_TEXT, using = "home")
    private WebElement homeText;

    @FindBy(how = How.CSS, using ="tr:nth-child(5) .btn")
    private WebElement chooseButton;

    public ListOfFlightsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getHomeText() {
        return homeText.getText();
    }

    public void chooseFlight() {
        chooseButton.click();
    }
}
