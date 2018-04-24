package core.login;

import core.PageBase;
import model.TestBot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionPage extends PageBase {
  private static final By LOGIN_FIELD = By.id("field_email");
  private static final By PASSWORD_FIELD = By.id("field_password");
  private static final By LOGIN_BUTTON = By.xpath(".//input[contains(@data-l,'sign_in')]");

  /**
   * Представляет страницу логина
   */
  public SessionPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {
  waitForVisibility(LOGIN_BUTTON, "Не удалось загрузить страницу логина");
  }

  /**
   * Логинится от имени пользователя
   *
   * @param testBot параметры входа
   */
  public void loginAuth(TestBot testBot) {
    type(testBot.getLogin(), LOGIN_FIELD);
    type(testBot.getPassword(), PASSWORD_FIELD);
    click(LOGIN_BUTTON);
  }
}
