package core.pages.groups.settings.main;

import core.pages.groups.settings.AbstractSettingsPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет основную страницу настроек группы
 */
public class GroupSettingsPage extends AbstractSettingsPage {

  private static final By CHANGE_TYPE = By.xpath("//*[contains(@hrefattrs, 'GroupChangeType')]");
  private static final By TYPE = By
      .xpath("//*[contains(@hrefattrs, 'GroupChangeType')]//parent::*/span");
  private static final By CHANGE_TYPE_CONFIRM_POPUP = By
      .xpath("//*[contains(@id, 'GroupChangeType')]");
  private static final By FORM_CONFIRM = By.xpath("//*[@data-l='t,confirm']");
  private static final By PRIVACY_DROPDOWN = By.xpath("//*[@id='field_privacy']");
  private static final By CHANGE_NAME_FIELD = By.xpath(".//*[@id='field_name']");
  private static final By BACK_MAIN_GROUP_PAGE = By
      .xpath(".//*[@id='mainTopContentRow']//a[contains(@class,'compact-profile_a ellip-i')]");

  public GroupSettingsPage(WebDriver driver) {
    super(driver);
  }

  /**
   * Меняет название группы в форме имени
   */
  public void changeName(String newName) {
    type(newName, CHANGE_NAME_FIELD);
  }

  /**
   * Получаем название группы из формы имени
   */
  public String getNameFromField() {
    return driver.findElement(CHANGE_NAME_FIELD).getAttribute("value");
  }

  /**
   * Получаем название группы из поля
   */
  public String getNameFromBackLink() {
    return driver.findElement(BACK_MAIN_GROUP_PAGE).getText();
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
    select.selectByValue(type.toString());
  }

  /**
   * Возвращает тип группы
   *
   * @return enum с типом
   */
  public GroupType getType() {
    waitForVisibility(TYPE, "Не удалось получить тип группы на странице настроек");
    GroupType type;
    String typeString = driver.findElement(TYPE).getText();
    switch (typeString){
      case "Страница":
        type = GroupType.PAGE;
        break;
      case "Мероприятие":
        type = GroupType.EVENT;
      case "Группа":
        type = GroupType.GROUP;
        default:
          Assert.fail("Неизвестный тип группы: " + typeString);
          type = null;
    }
    return type;
  }

}
