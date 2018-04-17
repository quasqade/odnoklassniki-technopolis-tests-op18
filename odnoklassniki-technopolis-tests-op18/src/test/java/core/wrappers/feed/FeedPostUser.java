package core.wrappers.feed;

import core.factories.FeedPostListFactory;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает пост пользователя
 */
public class FeedPostUser extends AbstractFeedPost {

  public FeedPostUser(WebElement rootElement){
    super(rootElement);
  }

  @Override
  public String getAuthor() {
    WebElement titleElement = rootElement.findElement(FeedPostListFactory.POST_AUTHOR_USER);
    if (titleElement == null){
      return "CANT GET FEED POST TITLE";
    }

    return titleElement.getText();
  }


  @Override
  public FeedPostType getType() {
    return FeedPostType.USER;
  }
}
