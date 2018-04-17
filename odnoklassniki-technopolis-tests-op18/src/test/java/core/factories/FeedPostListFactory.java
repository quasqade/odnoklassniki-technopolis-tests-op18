package core.factories;

import core.wrappers.feed.AbstractFeedPost;
import core.wrappers.feed.FeedPostGroup;
import core.wrappers.feed.FeedPostUser;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class FeedPostListFactory {

  private static final By FEED_POST = By.xpath("//*[@class='feed-w']");
  public static final By POST_AUTHOR_USER = By.xpath("//*[@data-l='t,uL']");
  public static final By POST_AUTHOR_GROUP = By.xpath("//*[@data-l='t,gL']");

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
      AbstractFeedPost feedPostWrapped = null;
      if (feedPost.findElements(POST_AUTHOR_USER).size()!=0){
        feedPostWrapped = new FeedPostUser(feedPost);
      }
      else if (feedPost.findElements(POST_AUTHOR_GROUP).size()!=0){
        feedPostWrapped = new FeedPostGroup(feedPost);
      }
      if (feedPostWrapped == null){
        Assert.assertTrue(false, "Unidentified feed post type");
      }

      feedPostsWrapped.add(feedPostWrapped);
    }
    return feedPostsWrapped;
  }

}
