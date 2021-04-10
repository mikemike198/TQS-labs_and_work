
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ConfirmationPage {

    private WebDriver driver;

    @FindBy(how = How.CSS, using = "h1")
    private WebElement title;

    @FindBy(how = How.CSS, using = "tr:nth-child(6) > td:nth-child(2)")
    private WebElement zipCodeText;

    @FindBys(@FindBy(how = How.CSS, using = ".btn"))
    private List<WebElement> numberOfButtons;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return title.getText();
    }

    public int getNumberOfButtons() {
        return numberOfButtons.size();
    }

    public String getZipCode() {
        return zipCodeText.getText();
    }
}
