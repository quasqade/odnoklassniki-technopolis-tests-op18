package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.UserMainPage;
import core.pages.dialogues.CommentPostingOverlay;
import core.pages.groups.GroupTopicsPage;
import core.pages.groups.main.GroupMainPage;
import core.wrappers.topics.GroupTopicWrapper;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


/**
 * Проверяется запрет комментирования темы для пользователей группы
 */
public class ForbidTopicCommentsTest extends TestBase {

  private static final TestBot USER_ACCOUNT_MEMBER = new TestBot("QA18testbot79", "QA18testbot");
  private static final String GROUP_NAME = getRandomId();
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot78", "QA18testbot");
  private static final String TOPIC_TEXT = "Тест запрета комментариев";
  private static final String COMMENT_TEXT = "Тестовый комментарий";

  private static WebDriver firstDriver, secondDriver;
  private static String groupId;


  @Before
  public void preconditions() {
    firstDriver = driver;
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    GroupMainPage gmp = GroupHelper.createPublicPage(driver, GROUP_NAME);
    groupId = gmp.getGroupId();


    init();
    secondDriver = driver;
    new SessionPage(driver).loginAuth(USER_ACCOUNT_MEMBER);
    new UserMainPage(driver); //дождаться загрузки страницы
    goToGroup(groupId);
    gmp = new GroupMainPage(driver);
    Assert.assertFalse(gmp.isMemberDropdownPresent(), "Пользователь уже состоит в группе");
    gmp.joinGroup();
    Assert.assertTrue(gmp.isMemberDropdownPresent(), "Не удалось вступить в группу");

    switchDriver(firstDriver);
  }

  @Test
  public void forbidCommentsTest() {
    GroupMainPage gmp = new GroupMainPage(driver);
    GroupTopicsPage gtp = gmp.createNewTopic(TOPIC_TEXT);
    gtp.collectTopics();
    GroupTopicWrapper firstTopic = gtp.getGroupTopics().get(0);
    Assert.assertEquals(firstTopic.getText(), TOPIC_TEXT);
    Assert.assertTrue(firstTopic.areCommentsAllowed(), "Комментарии уже запрещены");
    firstTopic.forbidComments();
    refresh(); //можно напихать ожиданий перед сборкой тем но там сложно угадать когда новые а когда старые
    gtp.collectTopics();
    firstTopic = gtp.getGroupTopics().get(0);
    Assert.assertFalse(firstTopic.areCommentsAllowed(), "Не удалось запретить комментарии");

    //второй пользователь
    switchDriver(secondDriver);
    refresh();
    gmp = new GroupMainPage(driver);
    gtp = gmp.goToTopics();
    gtp.collectTopics();
    firstTopic = gtp.getGroupTopics().get(0);
    CommentPostingOverlay cpo = firstTopic.openComments();
    Assert.assertFalse(cpo.areCommentsAllowed(),
        "Комментарии разрешены для пользователя при включенном запрете");

  }

}
