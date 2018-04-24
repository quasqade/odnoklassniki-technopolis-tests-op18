package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.topics.CommentPostingOverlay;
import core.groups.topics.GroupTopicWrapper;
import core.groups.topics.GroupTopicsPage;
import core.login.SessionPage;
import core.user.UserMainPage;
import model.BotProvider;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


/**
 * Проверяется запрет комментирования темы для пользователей группы
 */
public class ForbidTopicCommentsTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static final String TOPIC_TEXT = "Тест запрета комментариев";
  private static final String COMMENT_TEXT = "Тестовый комментарий";
  private static WebDriver firstDriver, secondDriver;
  private static String groupId;
  private final TestBot USER_ACCOUNT_MEMBER = BotProvider.requestBot(this);
  private final TestBot USER_ACCOUNT_ADMIN = BotProvider.requestBot(this);

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
    gmp = goToGroup(groupId).andGroupOpened();
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
