package core.wrappers.topics;

import core.helpers.DriverHelper;
import core.pages.PageBase;
import core.pages.dialogues.CommentPostingOverlay;
import javax.annotation.Nullable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Обертка для темы на странице Темы
 */
public class GroupTopicWrapper{

  private static final By TOPIC_TEXT = By.xpath("//*[@class='media-text_a']/parent::*");
  private static final By DROPDOWN_MENU = By.xpath("//*[@class='feed_menu']");
  private static final By FORBID_COMMENTS = By.xpath("//*[contains(@href, 'CloseTopicForComments')]");
  private static final By COMMENT_WIDGET = By.xpath("//*[contains(@data-location, 'CommentWidget')]/parent::*");

  private WebElement wrappedElement;
  private WebDriver driver;

  public GroupTopicWrapper(WebElement wrappedElement, WebDriver driver){
    this.wrappedElement=wrappedElement;
    this.driver=driver;
  }

  /**
   * Возвращает содержимое темы
   * @return текст темы
   */
  public String getText(){
    try {
      return wrappedElement.findElement(TOPIC_TEXT).getText();
    }
    catch (NoSuchElementException nse) {
      Assert.fail("Не удалось получить текст топика");
    }
    return null;
  }

  /**
   * Запрещает комментарии на теме
   */
  public void forbidComments(){
    Actions actions = new Actions(driver);
    actions.moveToElement(wrappedElement).perform();
    WebElement dropdownMenu = wrappedElement.findElement(DROPDOWN_MENU);
    actions.moveToElement(dropdownMenu).perform();
    WebElement forbidComments = wrappedElement.findElement(FORBID_COMMENTS);
    actions.moveToElement(forbidComments);
    actions.click().build().perform();
    }

  /**
   * Проверяет, отображается ли значок запрета комментариев на теме
   * @return состояние значка разрешения
   */
  public boolean areCommentsAllowed(){
    try {
      WebElement commentWidget = wrappedElement.findElement(COMMENT_WIDGET);
      return !commentWidget.getAttribute("class").contains("locked");
    }
    catch (NoSuchElementException nse){
      return false;
    }
  }

  /**
   * Открывает оверлей комментирования
   * @return страница оверлея комментирования
   */
  public CommentPostingOverlay openComments(){
    DriverHelper.click(driver, COMMENT_WIDGET);
    return new CommentPostingOverlay(driver);
  }

}
