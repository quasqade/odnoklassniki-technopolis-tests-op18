package core.groups.settings;

import core.PageBase;
import core.groups.settings.rights.RightsSettingsPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Абстрактный родитель для всех страниц настроек группы
 */
public abstract class AbstractSettingsPage extends PageBase {

  private static final By NAV_PANEL = By.xpath("//*[contains(@id, 'GroupSettingsCatalog')]");
  private static final By NAV_RIGHTS = By.xpath("//*[contains(@hrefattrs, 'GroupRights')]");
  private static final By NAV_MODERATORS = By.xpath("//*[contains(@hrefattrs, 'GroupModerators')]");
  private static final By SAVE_SETTINGS = By.xpath("//*[@name='button_save_settings']");
  private static final By TIP = By
      .xpath("//*[@data-module='NonBlockingTip']//*[@class='tip_cnt']//*");
  private static final By NAME_GROUP_IN_SETTINGS = By
      .xpath(".//*[@id='mainTopContentRow']//a[contains(@class,'compact-profile_a ellip-i')]");
  private String lastTipText = "NO TIP";

  public AbstractSettingsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(NAV_PANEL));
  }

  /**
   * Нажимает подтверждение внесенных изменений и обновляет последнее уведомление
   */
  public void confirmSettings() {
    click(SAVE_SETTINGS);
    updateLastTip();
    Assert.assertEquals("Настройки сохранены", getLastTipText());
  }

  /**
   * Обновляет последнее всплывающее уведомление (используйте getLastTipText() чтобы его получить)
   */
  //TODO вернуть значение
  protected void updateLastTip() {
    try {
      new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(TIP));
      lastTipText = driver.findElement(TIP).getText();
    } catch (TimeoutException te) {
      lastTipText = "NO TIP";
    }

    //ждем, пока исчезнет уведомление, иначе новые уведомления не будут появляться
    try {
      new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(TIP));
    } catch (TimeoutException te) {
      Assert.fail("Settings tip is not disappearing");
    }
  }

  /**
   * Возвращает текст последнего всплывающего уведомления
   *
   * @return текст уведомления
   */
  protected String getLastTipText() {
    return lastTipText;
  }

  /**
   * Переходит на страницу Управление через левую навигацию
   *
   * @return страница Управление
   */
  public RightsSettingsPage clickRights() {
    click(NAV_RIGHTS);
    return new RightsSettingsPage(driver);
  }


  /**
   * Переходит на страницу Администрация через левую навигацию
   *
   * @return страница Администрация
   */
  public ModeratorsSettingsPage clickModerators() {
    click(NAV_MODERATORS);
    return new ModeratorsSettingsPage(driver);
  }

  /**
   * Переходим на главную страницу группы
   */
  public void toGroupMainPage() {
    scrollToElement(NAME_GROUP_IN_SETTINGS);
    click(NAME_GROUP_IN_SETTINGS);
  }

  /**
   * Получаем название группы из поля
   */
  public String getNameFromBackLink() {
    return driver.findElement(NAME_GROUP_IN_SETTINGS).getText();
  }
}
