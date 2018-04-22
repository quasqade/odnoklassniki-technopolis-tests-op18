package core.groups.settings.rights;

import core.groups.settings.AbstractSettingsPage;
import org.junit.Assert;
import org.openqa.selenium.By;
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
  private static final By MENU_OF_GOOODS_DROPDOWN = By
      .xpath("//*[contains(@id, 'field_opt_advertPage')]");
  private static final By SUGGESTED_TOPICS = By.xpath("//*[contains(@id, 'SuggestedTopics')]");
  private static final By SHOW_PHOTOS_IN_FEED = By.xpath("//*[contains(@id, 'ShowPhotosInFeed')]");


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
  public void selectJoinNotificationFrequency(JoinNotificationFrequency frequency) {
    waitForVisibility(NOTIFICATION_FREQUENCY_DROPDOWN,
        "Не удалось получить параметры частоты оповещения");
    Select select = new Select(driver.findElement(NOTIFICATION_FREQUENCY_DROPDOWN));
    select.selectByValue(frequency.toString());
  }


  /**
   * Проверяет, могут ли участники предлагать темы
   *
   * @return значение выпадающего списка
   */
  public boolean canMembersSuggestTopics() {
    waitForVisibility(SUGGESTED_TOPICS, "Не удалось обнаружить настройку предлагаемых тем");
    Select select = new Select(driver.findElement(SUGGESTED_TOPICS));
    return !select.getFirstSelectedOption().getAttribute("value").equals("off");
  }

  /**
   * Проверяет, какие фото показываются в ленте
   *
   * @return строка со значением, выбранным в выпадающем списке
   */
  public ShowPhotosInFeedOption whatPhotosAreShownInFeed() {
    waitForVisibility(SHOW_PHOTOS_IN_FEED, "Не удалось обнаружить настройку показа фото в ленте");
    Select select = new Select(driver.findElement(SHOW_PHOTOS_IN_FEED));
    ShowPhotosInFeedOption option = ShowPhotosInFeedOption
        .valueOf(select.getFirstSelectedOption().getAttribute("value"));
    return option;
  }


  /**
   * Выбирает как отображается меню Товаров на главной странице группы
   */
  public void selectDisplayMenuOfGoods(MenuOfGoodsPopUp selector) {
    waitForVisibility(MENU_OF_GOOODS_DROPDOWN,
        "Не удалось получить параметры частоты оповещения");
    Select select = new Select(driver.findElement(MENU_OF_GOOODS_DROPDOWN));
    select.selectByValue(selector.toString());
  }
}
