package core.pages.groups.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Представляет страницу настроек Администрация
 */
public class ModeratorsSettingsPage extends AbstractSettingsPage {

  private static final By MODERATORS_FORM = By.xpath("//*[contains(@id, 'ModeratorsRBForm')]");
  private static final By HIDE_MODERATORS_CHECKBOX = By
      .xpath("//*[@type='checkbox' and contains(@name, 'HideModerators')]");
  private static final By HIDE_OWNER_CHECKBOX = By
      .xpath("//*[@type='checkbox' and contains(@name, 'HideOwner')]");

  public ModeratorsSettingsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    super.check();
    waitForVisibility(MODERATORS_FORM,
        "Не удалось загрузить страницу настройки администрации в группе");
  }

  /**
   * Проверяет, выставлена ли галочка сокрытия модераторов
   */
  public boolean areModeratorsHidden() {
    waitForVisibility(HIDE_MODERATORS_CHECKBOX,
        "Не удалось обнаружить чекбокс сокрытия модераторов");

    return driver.findElement(HIDE_MODERATORS_CHECKBOX).isSelected();
  }

  /**
   * Проверяет, выставлена ли галочка сокрытия владельца
   */
  public boolean isOwnerHidden() {
    waitForVisibility(HIDE_OWNER_CHECKBOX, "Не удалось обнаружить чекбокс сокрытия модераторов");

    return driver.findElement(HIDE_OWNER_CHECKBOX).isSelected();
  }

}
