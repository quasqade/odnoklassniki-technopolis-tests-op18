package core.pages.groups.settings;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ModeratorsSettingsPage extends AbstractSettingsPage {

  private static final By MODERATORS_FORM = By.xpath("//*[contains(@id, 'ModeratorsRBForm')]");
  private static final By HIDE_MODERATORS_CHECKBOX = By.xpath("//*[@type='checkbox' and contains(@name, 'HideModerators')]");
  private static final By HIDE_OWNER_CHECKBOX = By.xpath("//*[@type='checkbox' and contains(@name, 'HideOwner')]");

  public ModeratorsSettingsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    super.check();
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(MODERATORS_FORM), 10, 500)) {
      Assert.fail("Не удалось загрузить страницу настройки администрации в группе");
    }
  }

  /**
   * Проверяет, выставлена ли галочка сокрытия модераторов
   * @return
   */
  public boolean areModeratorsHidden(){
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(HIDE_MODERATORS_CHECKBOX), 5, 500))
      Assert.fail("Не удалось обнаружить чекбокс сокрытия модераторов");

    return driver.findElement(HIDE_MODERATORS_CHECKBOX).isSelected();
  }

  /**
   * Проверяет, выставлена ли галочка сокрытия владельца
   * @return
   */
  public boolean isOwnerHidden(){
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(HIDE_OWNER_CHECKBOX), 5, 500))
      Assert.fail("Не удалось обнаружить чекбокс сокрытия модераторов");

    return driver.findElement(HIDE_OWNER_CHECKBOX).isSelected();
  }
}
