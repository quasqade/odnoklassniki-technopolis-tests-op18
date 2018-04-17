package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.pages.groups.GroupMainPage;
import core.wrappers.feed.AbstractFeedPost;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется создание поста в группе и его появление на в ленте групп пользователя
 */
public class FeedTopicTest extends TestBase {

  private static final String GROUP_NAME = "AddTopicTestGroup";
  private static final String TOPIC_TEXT = "Тест доставки ленты";
  private static final TestBot USER_ACCOUNT = new TestBot("QA18testbot78", "QA18testbot");

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
