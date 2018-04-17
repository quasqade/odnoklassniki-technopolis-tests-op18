package core.transformers;

import core.factories.FeedPostFactory;
import core.wrappers.feed.AbstractFeedPost;
import core.wrappers.feed.FeedPostGroup;
import core.wrappers.feed.FeedPostUser;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class FeedPostListTransformer {

  private static final By FEED_POST = By.xpath("//*[@class='feed-w']");


  /**
   * Создает список всех постов на странице
   * @param driver
   * @return список абстрактных объектов
   */
  public static List<AbstractFeedPost> collectPosts(WebDriver driver){
    List<WebElement> feedPosts = driver.findElements(FEED_POST);
    List<AbstractFeedPost> feedPostsWrapped = new ArrayList<>();
    for (WebElement feedPost: feedPosts
    ) {
       feedPostsWrapped.add(FeedPostFactory.wrapElement(feedPost));
    }
    return feedPostsWrapped;
  }

}
