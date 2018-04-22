package tests;

import core.Page404;
import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupPrivacy;
import core.groups.settings.main.GroupSettingsPage;
import core.groups.settings.rights.JoinNotificationFrequency;
import core.groups.settings.rights.RightsSettingsPage;
import core.login.SessionPage;
import core.user.UserMainPage;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class SecretGroupVisibilityTest extends TestBase {

  private static final TestBot USER_ACCOUNT_MEMBER = new TestBot("QA18testbot80", "QA18testbot");
  private static final String GROUP_NAME = getRandomId();
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot81", "QA18testbot");
  private static String groupId;

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    groupId = GroupHelper.createPublicPage(driver, GROUP_NAME).getGroupId();
  }


  @Test
  public void testCase() throws Exception {

    //test
    GroupMainPage gmp = new GroupMainPage(driver);
    GroupSettingsPage gsp = gmp.openGroupSettings();
    gsp.changeType();
    gsp.changePrivacy(GroupPrivacy.BY_MEMBER_INVITATION);
    gsp.confirmSettings();

    //авторизация от второго пользователя
    init();
    new SessionPage(driver).loginAuth(USER_ACCOUNT_MEMBER);
    new UserMainPage(driver); //чтобы дождаться загрузки страницы
    goToGroup(groupId).and404Opened();
  }
}
