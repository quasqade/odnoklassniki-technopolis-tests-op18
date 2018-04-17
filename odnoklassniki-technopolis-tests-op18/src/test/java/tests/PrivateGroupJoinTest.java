package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.pages.groups.GroupMainPage;
import core.pages.groups.GroupPrivacy;
import core.pages.groups.GroupSettingsPage;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class PrivateGroupJoinTest extends TestBase {

  public static final String GROUP_NAME = "PrivateJoinTestGroup";
  public static final TestBot USER_ACCOUNT = new TestBot("QA18testbot78", "QA18testbot");

  @Before
  public void preconditions(){
    new SessionPage(driver).loginAuth(USER_ACCOUNT);
    GroupHelper.createPublicPage(driver, GROUP_NAME);
  }


  @Test
  public void testCase() throws Exception {

    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.openOtherSections();
    Assert.assertTrue("Выпадающее меню не появилось после нажатия Ещё",
        groupMainPage.checkIfOtherSectionsIsOpen());
    GroupSettingsPage gsp = groupMainPage.openGroupSettings();
    gsp.changeType();
    Assert.assertEquals("Тип группы изменен", gsp.getLastTipText());
    gsp.changePrivacy(GroupPrivacy.PRIVATE);
    gsp.confirmSettings();
    Assert.assertEquals("Настройки сохранены", gsp.getLastTipText());

    //conditions

  }
}
