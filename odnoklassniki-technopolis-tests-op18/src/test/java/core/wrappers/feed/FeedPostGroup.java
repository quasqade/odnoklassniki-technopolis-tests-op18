package core.wrappers.feed;

import core.factories.FeedPostListFactory;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает пост группы
 */
public class FeedPostGroup extends AbstractFeedPost {

  public FeedPostGroup(WebElement rootElement){
    super(rootElement);
  }

  @Override
  public String getAuthor() {
    WebElement titleElement = rootElement.findElement(FeedPostListFactory.POST_AUTHOR_GROUP);
    if (titleElement == null){
      return "CANT GET FEED POST TITLE";
    }

    return titleElement.getText();
  }

  @Override
  public FeedPostType getType() {
    return FeedPostType.GROUP;
  }
}
