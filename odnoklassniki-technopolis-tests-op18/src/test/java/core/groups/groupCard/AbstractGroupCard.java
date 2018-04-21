package core.groups.groupCard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Абстрактная обертка для карточек групп
 */

public abstract class AbstractGroupCard {

  protected WebElement rootElement;
  protected WebDriver driver;

  public AbstractGroupCard(WebElement rootElement, WebDriver driver) {
    this.rootElement = rootElement;
    this.driver = driver;
  }

  /**
   * Возвращает название группы
   *
   * @return текст имени
   */
  abstract public String getName();


  /**
   * Клик по карте
   */
  abstract public void clickToGroupCard();

}
