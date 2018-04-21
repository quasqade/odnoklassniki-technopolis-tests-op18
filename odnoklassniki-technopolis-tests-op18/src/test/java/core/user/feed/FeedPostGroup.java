package core.user.feed;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает пост группы
 */
public class FeedPostGroup extends AbstractFeedPost {

  public FeedPostGroup(WebElement rootElement) {
    super(rootElement);
  }

  @Override
  public String getAuthor() {
    try {
      WebElement titleElement = rootElement.findElement(FeedPostFactory.POST_AUTHOR_GROUP);
      return titleElement.getText();
    } catch (NoSuchElementException nse) {
      return "Can't get author for feed post";
    }
  }

  @Override
  public FeedPostType getType() {
    return FeedPostType.GROUP;
  }
}
