package core.pages.dialogues;

import core.pages.PageBase;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Представляет собой оверлей постинга комментариев (технически не страница, а враппер, но так удобнее обращаться к методам PageBase)
 */
public class CommentPostingOverlay extends PageBase {

  private static final By OVERLAY = By.xpath("//*[@class='dialogWrapper']");
  private static final By INPUT_FIELD_SIMPLE = By.xpath("//*[@id='smplInputId28']");
  private static final By INPUT_FIELD_RICH = By
      .xpath("//*[contains(@class, 'rich_input')]//*[@data-placeholder]");
  private static final By SUBMIT_COMMENT_SIMPLE = By.xpath("//*[@id='smplBtnId27']");
  private static final By SUBMIT_COMMENT_RICH = By.xpath("//*[@uid='sendComment']");
  private static final By COMMENT_TEXT = By.xpath("//*[contains(@class, 'textWrap')]//*[contains(@id, 'hook')]");

  private boolean isSimpleInput;

  /**
   * Создает объект и определяет тип ввода (бывает простой и расширенный с разными локаторами)
   * @param driver драйвер
   */
  public CommentPostingOverlay(WebDriver driver) {
    super(driver);
    if (driver.findElements(INPUT_FIELD_SIMPLE).size() != 0) {
      isSimpleInput = true;
    } else if (driver.findElements(INPUT_FIELD_RICH).size() != 0) {
      isSimpleInput = false;
    } else {
      Assert.fail("Не удалось определить тип ввода для оверлея комментариев");
    }
  }

  @Override
  protected void check() {
    Assert.assertTrue("Не удалось загрузить оверлей комментариев",
        explicitWait(ExpectedConditions.presenceOfElementLocated(OVERLAY), 5, 500));
  }


  /**
   * Возвращает число комментариев в оверлее
   * @return число комментариев
   */
  public int getAmountOfComments(){
    List<WebElement> commentElements = driver.findElements(COMMENT_TEXT);
    return commentElements.size();
  }

  /**
   * Возвращает текст последнего комментария
   * @return текст
   */
  public String getLastCommentText(){
    List<WebElement> commentElements = driver.findElements(COMMENT_TEXT);
    if (commentElements.size()==0){
      return "Нет комментариев";
    }
    return commentElements.get(commentElements.size()-1).getText();
  }

  /**
   * Проверяет, активна ли кнопка отправки комментария
   * @return
   */
  public boolean areCommentsAllowed(){
    WebElement commentButton;
    if (isSimpleInput){
      commentButton = driver.findElement(SUBMIT_COMMENT_SIMPLE);
    }
    else{
      commentButton = driver.findElement(SUBMIT_COMMENT_RICH);
    }
    return (commentButton.isDisplayed() && commentButton.isEnabled());
  }

}
