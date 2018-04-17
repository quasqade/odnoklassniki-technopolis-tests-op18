package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.groups.GroupMainPage;
import core.pages.groups.GroupPrivacy;
import core.pages.groups.settings.GroupSettingsPage;
import core.pages.groups.settings.RightsSettingsPage;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class PrivateGroupJoinTest extends TestBase {

  private static final String GROUP_NAME = "PrivateJoinTestGroup";
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot78", "QA18testbot");
  public static final TestBot USER_ACCOUNT_MEMBER = new TestBot("QA18testbot79", "QA18testbot");

  @Before
  public void preconditions(){
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    GroupHelper.createPublicPage(driver, GROUP_NAME);
  }


  @Test
  public void testCase() throws Exception {

    //test
    String groupUrl = driver.getCurrentUrl(); //записываем адрес страницы, чтобы потом возвращаться
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.openOtherSections();
    GroupSettingsPage gsp = groupMainPage.openGroupSettings();
    gsp.changeType();
    gsp.changePrivacy(GroupPrivacy.PRIVATE);
    gsp.confirmSettings();
    RightsSettingsPage rsp = gsp.clickRights();
    rsp.selectJoinNotificationFrequency("Сразу");
    rsp.confirmSettings();



    //conditions

  }
}
