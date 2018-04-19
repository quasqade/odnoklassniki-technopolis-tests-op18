package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.UserMainPage;
import core.pages.groups.main.GroupMainPage;
import core.pages.groups.settings.main.GroupPrivacy;
import core.pages.groups.settings.main.GroupSettingsPage;
import core.pages.groups.settings.rights.JoinNotificationFrequency;
import core.pages.groups.settings.rights.RightsSettingsPage;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class PrivateGroupJoinTest extends TestBase {

  private static final TestBot USER_ACCOUNT_MEMBER = new TestBot("QA18testbot79", "QA18testbot");
  private static final String GROUP_NAME = "PrivateJoinTestGroup";
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot78", "QA18testbot");

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    GroupHelper.createPublicPage(driver, GROUP_NAME);
  }


  @Test
  public void testCase() throws Exception {

    //test
    rememberUrl(); //записываем адрес страницы, чтобы потом возвращаться
    WebDriver firstDriver = driver;
    GroupMainPage gmp = new GroupMainPage(driver);
    gmp.openOtherSections();
    GroupSettingsPage gsp = gmp.openGroupSettings();
    gsp.changeType();
    gsp.changePrivacy(GroupPrivacy.PRIVATE);
    gsp.confirmSettings();
    RightsSettingsPage rsp = gsp.clickRights();
    rsp.selectJoinNotificationFrequency(JoinNotificationFrequency.IMMEDIATELY);
    rsp.confirmSettings();

    //авторизация от второго пользователя
    init();
    WebDriver secondDriver = driver;
    new SessionPage(driver).loginAuth(USER_ACCOUNT_MEMBER);
    new UserMainPage(driver); //чтобы дождаться загрузки страницы
    goToRememberedUrl();
    gmp = new GroupMainPage(driver);
    gmp.joinGroup();
    Assert.assertTrue(gmp.isInvitationPending());

    //первый пользователь
    switchDriver(firstDriver);
    goToRememberedUrl(); //TODO заменить на переход по клику
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
