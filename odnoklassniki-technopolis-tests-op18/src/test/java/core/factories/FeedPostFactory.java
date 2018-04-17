package core.factories;


import core.wrappers.feed.AbstractFeedPost;
import core.wrappers.feed.FeedPostGroup;
import core.wrappers.feed.FeedPostUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class FeedPostFactory {

  public static final By POST_AUTHOR_USER = By.xpath("//*[@data-l='t,uL']");
  public static final By POST_AUTHOR_GROUP = By.xpath("//*[@data-l='t,gL']");

  /**
   * Оборачивает элемент во враппер корректного типа
   *
   * @param element оборачиваемый элемент который содержит пост
   * @return обертка
   */
  public static AbstractFeedPost wrapElement(WebElement element) {
    AbstractFeedPost feedPostWrapped = null;
    if (element.findElements(POST_AUTHOR_USER).size() != 0) {
      feedPostWrapped = new FeedPostUser(element);
    } else if (element.findElements(POST_AUTHOR_GROUP).size() != 0) {
      feedPostWrapped = new FeedPostGroup(element);
    }
    if (feedPostWrapped == null) {
      Assert.fail("Unidentified feed post type");
    }
    return feedPostWrapped;
  }
}
