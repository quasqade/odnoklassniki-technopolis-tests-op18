package core.user;

import core.PageBase;
import core.user.feed.AbstractFeedPost;
import core.user.feed.FeedPostListTransformer;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет главную страницу пользователя
 */
public class UserMainPage extends PageBase {

  private static final By FEED_CATEGORY_GROUPS = By
      .xpath("//*[contains(@data-l, 'feedTargetFilterId,205')]");
  private static final By FEED = By.xpath("//*[@class='feed-w']");
  private static final By NAV_TOOLBAR_GROUPS = By.xpath(".//*[contains(@hrefattrs,'NavMenu_User_AltGroups')]");
  private static final By NAV_TOOLBAR = By.xpath("//*[contains(@class, 'nav-side')]");
  private static final By FEED_GROUPS_TAB = By.xpath("//*[contains(@class, 'stub-group')]");
  private List<AbstractFeedPost> feedPosts;


  public UserMainPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {
    waitForVisibility(NAV_TOOLBAR, "Не удалось загрузить страницу пользователя");
  }


  /**
   * Открывает страницу "Группы" в левом навбаре
   */
  public UserGroupsPage openGroupsByToolbar() {
    click(NAV_TOOLBAR_GROUPS);
    return new UserGroupsPage(driver);
  }

  /**
   * Выбирает вкладку "Группы" в ленте
   */
  public void openGroupsFeedCategory() {
    click(FEED_CATEGORY_GROUPS);
    waitForVisibility(FEED_CATEGORY_GROUPS, "Не удалось открыть вкладку Группы в ленте");
  }

  /**
   * Определяет, есть ли на странице пост с таким текстом
   *
   * @param s текст
   */
  public boolean isTopicPresentInFeedByText(String s) {
    return isElementPresent(
        By.xpath("//*[contains(@class, 'media-text') and contains(text(), \'" + s + "\')]"));
  }

  /**
   * Собирает список всех отображаемых на странице постов ленты
   *
   * @return true - есть посты, false - нет
   */
  public boolean collectFeedPosts() {
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(FEED));
    List<AbstractFeedPost> feedPosts = FeedPostListTransformer.collectPosts(driver);
    if (feedPosts.size() == 0) {
      return false;
    }

    this.feedPosts = feedPosts;
    return true;
  }

  /**
   * Возвращает первый пост из списка, собранного collectFeedPosts()
   *
   * @return враппер поста
   */
  public AbstractFeedPost getFirstPostInFeed() {
    return feedPosts.get(0);
  }


}
