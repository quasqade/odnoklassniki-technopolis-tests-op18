package core.pages;

import model.TestBot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionPage extends PageBase {

  /**
   * Представляет страницу логина
   * @param driver
   */
  public SessionPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {

  }

  /**
   * Логинится от имени пользователя
   * @param testBot параметры входа
   */
  public void loginAuth(TestBot testBot) {
      type(testBot.getLogin(), By.id("field_email"));
      type(testBot.getPassword(), By.id("field_password"));
      click(By.xpath(".//input[contains(@data-l,'sign_in')]"));
  }
}
