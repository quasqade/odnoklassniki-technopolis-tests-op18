package core.wrappers.feed;

import core.factories.FeedPostFactory;
import core.transformers.FeedPostListTransformer;
import org.openqa.selenium.NoSuchElementException;
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
    try {
      WebElement titleElement = rootElement.findElement(FeedPostFactory.POST_AUTHOR_USER);
      return titleElement.getText();
    }
    catch (NoSuchElementException nse){
      return "Can't get author for feed post";
    }

  }


  @Override
  public FeedPostType getType() {
    return FeedPostType.USER;
  }
}
