package core.transformers;

import core.factories.FeedPostFactory;
import core.wrappers.feed.AbstractFeedPost;
import core.wrappers.topics.GroupTopicWrapper;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Собирает список тем на странице
 */
public class TopicListTransformer {
  private static final By TOPIC = By.xpath("//*[contains(@data-l, 'topicId') and contains(@class, 'groups_post')]");


  /**
   * Создает список всех тем на странице
   *
   * @return список врапперов
   */
  public static List<GroupTopicWrapper> collectTopics(WebDriver driver) {
    List<WebElement> feedPosts = driver.findElements(TOPIC);
    List<GroupTopicWrapper> topicsWrapped = new ArrayList<>();
    for (WebElement topicElement : feedPosts
        ) {
      topicsWrapped.add(new GroupTopicWrapper(topicElement, driver));
    }
    return topicsWrapped;
  }
}
