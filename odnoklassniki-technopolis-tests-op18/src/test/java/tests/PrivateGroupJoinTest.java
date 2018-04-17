package tests;

import core.pages.groups.GroupMainPage;
import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.pages.groups.GroupSettingsPage;
import core.pages.groups.GroupPrivacy;
import model.TestBot;
import org.junit.Assert;
import org.junit.Test;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class PrivateGroupJoinTest extends TestBase {

  @Test
  public void testCase() throws Exception {

    //preconditions
    new SessionPage(driver).loginAuth(new TestBot("QA18testbot78", "QA18testbot"));
    new UserMainPage(driver).openGroupsByToolbar();
    UserGroupsPage userGroupsPage = new UserGroupsPage(driver);
    userGroupsPage.createGroupByToolbar();
    userGroupsPage.selectPublicPage();
    userGroupsPage.inputGroupName("PrivateJoinTestGroup");
    userGroupsPage.selectCategoryComputers();
    userGroupsPage.confirmGroupCreation();

    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.openOtherSections();
    Assert.assertTrue("Выпадающее меню не появилось после нажатия Ещё", groupMainPage.checkIfOtherSectionsIsOpen());
    groupMainPage.openGroupSettings();
    GroupSettingsPage gsp = new GroupSettingsPage(driver);
    gsp.changeType();
    Assert.assertEquals("Тип группы изменен", gsp.getLastTipText());
    gsp.changePrivacy(GroupPrivacy.PRIVATE);
    gsp.confirmSettings();
    Assert.assertEquals("Настройки сохранены", gsp.getLastTipText());


    //conditions

  }
}
