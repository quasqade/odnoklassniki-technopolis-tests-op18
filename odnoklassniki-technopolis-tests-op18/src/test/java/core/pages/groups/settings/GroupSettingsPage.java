package core.pages.groups.settings;

import core.pages.PageBase;
import core.pages.groups.GroupPrivacy;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет основную страницу настроек группы
 */
public class GroupSettingsPage extends AbstractSettingsPage {

  private static final By CHANGE_TYPE = By.xpath("//*[contains(@hrefattrs, 'GroupChangeType')]");
  private static final By CHANGE_TYPE_CONFIRM_POPUP = By
      .xpath("//*[contains(@id, 'GroupChangeType')]");
  private static final By FORM_CONFIRM = By.xpath("//*[@data-l='t,confirm']");
  private static final By PRIVACY_DROPDOWN = By.xpath("//*[@id='field_privacy']");


  public GroupSettingsPage(WebDriver driver) {
    super(driver);
  }

    /**
   * Меняет тип группы на страницу и обратно, подтверждая в попапе, обновляет уведомление
   */
  public void changeType() {
    click(CHANGE_TYPE);
    new WebDriverWait(driver, 5)
        .until(ExpectedConditions.visibilityOfElementLocated(CHANGE_TYPE_CONFIRM_POPUP));
    click(FORM_CONFIRM);
    updateLastTip();
    Assert.assertEquals("Тип группы изменен", getLastTipText());
  }

  /**
   * Выбирает в списке типов группы (не страницы) тип, отвечающий за доступ
   *
   * @param type тип группы
   */
  public void changePrivacy(GroupPrivacy type) {
    Select select = new Select(driver.findElement(PRIVACY_DROPDOWN));
    switch (type) {
      case OPEN:
        select.selectByValue("OPEN");
        break;
      case PRIVATE:
        select.selectByValue("BY_MEMBER_INVITATION_AND_REQUEST");
        break;
      case SECRET:
        select.selectByValue("BY_MEMBER_INVITATION");
        break;
    }
  }

}
