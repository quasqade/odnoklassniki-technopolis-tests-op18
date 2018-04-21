package core.groups.groupCard;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GroupCardModerate extends AbstractGroupCard {

  private static final By GROUP_CARD_MODERATE_NAME = By
      .xpath("//*[@class='ucard-v']//a[@class='o two-lines']");
  private WebElement titleElement;

  public GroupCardModerate(WebElement rootElement, WebDriver driver) {
    super(rootElement, driver);
    titleElement = rootElement.findElement(GROUP_CARD_MODERATE_NAME);
    (new WebDriverWait(driver, 5))
        .until(ExpectedConditions.visibilityOf(titleElement));
  }

  @Override
  public String getName() {
    try {
      return titleElement.getText();
    } catch (NoSuchElementException nse) {
      return "Can't get name group";
    }
  }

  @Override
  public void clickToGroupCard() {
//        if (ExpectedConditions.elementToBeClickable(GROUP_CARD_MODERATE_NAME)) {
//            Assert.fail("Элемент не кликабелен: " + GROUP_CARD_MODERATE_NAME);
//        }
//        rootElement.click();
  }
}
