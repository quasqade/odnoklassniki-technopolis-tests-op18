package tests;

import core.groups.GroupHelper;
import core.login.SessionPage;
import core.user.UserMainPage;
import core.groups.main.GroupMainPage;
import core.user.feed.AbstractFeedPost;
import model.BotProvider;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется создание поста в группе и его появление в ленте групп пользователя
 */
public class FeedTopicTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static final String TOPIC_TEXT = "Тест доставки ленты";
  private final TestBot USER_ACCOUNT = BotProvider.requestBot(this);

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT);
    GroupHelper.createPublicPage(driver, GROUP_NAME);
  }

  @Test
  public void testCase() throws Exception {
    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.createNewTopic(TOPIC_TEXT);
    UserMainPage userMainPage = groupMainPage.returnToUserPage();
    userMainPage.openGroupsFeedCategory();

    //conditions
    Assert.assertTrue(userMainPage.collectFeedPosts()); //проверяем, что лента не пустая
    AbstractFeedPost firstFeedPost = userMainPage.getFirstPostInFeed(); //берем первый пост из ленты
    //проверяем, что пост соответствует ожиданиям
    Assert.assertEquals(GROUP_NAME, firstFeedPost.getAuthor());
    Assert.assertEquals(TOPIC_TEXT, firstFeedPost.getText());

  }
}
