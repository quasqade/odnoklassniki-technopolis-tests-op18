package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupPrivacy;
import core.groups.settings.main.GroupSettingsPage;
import core.groups.settings.rights.JoinNotificationFrequency;
import core.groups.settings.rights.RightsSettingsPage;
import core.login.SessionPage;
import core.user.UserMainPage;
import model.BotProvider;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class PrivateGroupJoinTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static String groupId;
  private final TestBot USER_ACCOUNT_MEMBER = BotProvider.requestBot(this);
  private final TestBot USER_ACCOUNT_ADMIN = BotProvider.requestBot(this);

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    groupId = GroupHelper.createPublicPage(driver, GROUP_NAME).getGroupId();
  }


  @Test
  public void testCase() throws Exception {

    //test
    WebDriver firstDriver = driver;
    GroupMainPage gmp = new GroupMainPage(driver);
    GroupSettingsPage gsp = gmp.openGroupSettings();
    gsp.changeType();
    gsp.changePrivacy(GroupPrivacy.BY_MEMBER_INVITATION_AND_REQUEST);
    gsp.confirmSettings();
    RightsSettingsPage rsp = gsp.clickRights();
    rsp.selectJoinNotificationFrequency(JoinNotificationFrequency.IMMEDIATELY);
    rsp.confirmSettings();

    //авторизация от второго пользователя
    init();
    WebDriver secondDriver = driver;
    new SessionPage(driver).loginAuth(USER_ACCOUNT_MEMBER);
    new UserMainPage(driver); //чтобы дождаться загрузки страницы
    gmp = goToGroup(groupId).andGroupOpened();
    gmp.joinGroup();
    Assert.assertTrue(gmp.isInvitationPending());

    //первый пользователь
    switchDriver(firstDriver);
    rsp.toGroupMainPage();
    gmp = new GroupMainPage(driver);
    Assert.assertTrue(gmp.getAmountOfPendingRequests() > 0);
    int members = gmp.getAmountOfMembers();
    gmp.acceptFirstJoinRequest();
    Assert.assertEquals(gmp.getAmountOfMembers(), members + 1);

    //второй пользователь
    switchDriver(secondDriver);
    refresh();

    //conditions
    Assert.assertTrue(new GroupMainPage(driver).isMemberDropdownPresent());

  }
}
