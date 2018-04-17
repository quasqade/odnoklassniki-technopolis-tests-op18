package tests;

import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.pages.groups.GroupMainPage;
import core.wrappers.feed.AbstractFeedPost;
import model.TestBot;
import org.junit.Assert;
import org.junit.Test;

/**
 * Проверяется создание поста в группе и его появление на в ленте групп пользователя
 */
public class FeedTopicTest extends TestBase {

  @Override
  public void setUp() throws Exception {
    super.setUp();
    new SessionPage(driver).loginAuth(new TestBot("QA18testbot78", "QA18testbot"));
    UserGroupsPage userGroupsPage = new UserMainPage(driver).openGroupsByToolbar();
    userGroupsPage.createGroupByToolbar();
    userGroupsPage.selectPublicPage();
    userGroupsPage.inputGroupName("AddTopicTestGroup");
    userGroupsPage.selectCategoryComputers();
    userGroupsPage.confirmGroupCreation();
  }

  @Test
  public void testCase() throws Exception {
    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.createNewTopic("Тест доставки ленты");
    UserMainPage userMainPage = groupMainPage.returnToUserPage();
    userMainPage.openGroupsFeedCategory();

    //conditions
    Assert.assertTrue(userMainPage.collectFeedPosts()); //проверяем, что лента не пустая
    AbstractFeedPost firstFeedPost = userMainPage.getFirstPostInFeed();
    //проверяем, что пост соответствует ожиданиям
    Assert.assertEquals("AddTopicTestGroup", firstFeedPost.getAuthor());
    Assert.assertEquals("Тест доставки ленты", firstFeedPost.getText());

  }
}
