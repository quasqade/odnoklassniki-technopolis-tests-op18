package core.pages.groups.settings;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


/**
 * Представляет страницу настроек прав в группе (Управление)
 */
public class RightsSettingsPage extends AbstractSettingsPage {

  private static final By RIGHTS_FORM = By.xpath("//*[contains(@id, 'GroupRightsForm')]");
  private static final By NOTIFICATION_FREQUENCY_DROPDOWN = By
      .xpath("//*[contains(@id, 'joinRequestNotification')]");

  public RightsSettingsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    super.check();
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(RIGHTS_FORM), 10, 500)) {
      Assert.fail("Не удалось загрузить страницу настройки прав в группе");
    }
  }


  /**
   * Выбирает частоту получения заявок на вступление
   *
   * @param frequency частота текстом
   */
  public void selectJoinNotificationFrequency(String frequency) {
    if (!explicitWait(
        ExpectedConditions.visibilityOfElementLocated(NOTIFICATION_FREQUENCY_DROPDOWN), 5, 500)) {
      Assert.fail("Не удалось получить параметры частоты оповещения");
    }
    Select select = new Select(driver.findElement(NOTIFICATION_FREQUENCY_DROPDOWN));
    try {
      select.selectByVisibleText(frequency);
    } catch (NoSuchElementException nse) {
      Assert.fail("Неизвестная частота получения оповещения: " + frequency);
    }
  }

}
