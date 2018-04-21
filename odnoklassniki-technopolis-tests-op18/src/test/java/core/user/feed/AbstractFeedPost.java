package core.user.feed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Абстрактная обертка для постов в ленте от групп и пользователей
 */
public abstract class AbstractFeedPost {

  private static final By POST_TEXT = By.xpath("//*[contains(@class, 'textWrap')]");


  protected final WebElement rootElement;

  protected AbstractFeedPost(WebElement rootElement) {
    this.rootElement = rootElement;
  }

  /**
   * Возвращает имя автора поста
   *
   * @return текст имени
   */
  abstract public String getAuthor();

  /**
   * Возвращает тип поста
   *
   * @return enum с типом поста
   */
  abstract public FeedPostType getType();

  /**
   * @return Число комментариев на посте
   */
  public Integer getCommentsCounter() {
    return null;
  }

  /**
   * @return число классов на посте
   */
  public Integer getKlassCounter() {
    return null;
  }

  /**
   * @return Число шар на посте
   */
  public Integer getShareCounter() {
    return null;
  }

  /**
   * @return текст поста
   */
  public String getText() {
    return rootElement.findElement(POST_TEXT).getText();
  }

  /**
   * Открыть комменты на посте
   */
  public void openComments() {

  }

  //TODO заменить на промисы где будут

  /**
   * Решарнуть пост
   */
  public void reshare() {

  }

  /**
   * Поставить класс
   */
  public void doKlass() {

  }

  enum FeedPostType {GROUP, USER}
}
