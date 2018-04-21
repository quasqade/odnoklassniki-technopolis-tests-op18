package core.groups.topics;

import core.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Представляет страницу Темы в группе
 */
public class GroupTopicsPage extends PageBase {

  private static final By ACTIVE_TOPICS = org.openqa.selenium.By
      .xpath("//*[contains(@data-l, 'Group_Forum') and contains(@class, 'Active')]");
  private List<GroupTopicWrapper> groupTopics;

  public GroupTopicsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    waitForVisibility(ACTIVE_TOPICS, "Не удалось загрузить раздел Темы");
  }

  /**
   * Собирает список тем с помощью трансформера
   */
  public void collectTopics() {
    groupTopics = TopicListTransformer.collectTopics(driver);
  }

  /**
   * Возвращает список тем
   *
   * @return список врапперов тем
   */
  public List<GroupTopicWrapper> getGroupTopics() {
    return groupTopics;
  }

  /**
   * Запрещает комментарии на переданном топике
   *
   * @param topic враппер топика
   */
  public void forbidCommentsOnTopic(GroupTopicWrapper topic) {
    topic.forbidComments();
  }


}
