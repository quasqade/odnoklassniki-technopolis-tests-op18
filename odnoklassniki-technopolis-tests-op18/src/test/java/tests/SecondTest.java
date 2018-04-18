package tests;

import core.pages.SessionPage;
import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import model.TestBot;
import org.junit.Test;

public class SecondTest extends TestBase {

  @Test
  public void testCase() throws Exception {

    new SessionPage(driver).loginAuth(new TestBot("QA18testbot80 ", "QA18testbot"));
    new UserMainPage(driver).openGroupsByToolbar();
    UserGroupsPage userGroupsPage = new UserGroupsPage(driver);
    userGroupsPage.createGroupByToolbar();
    userGroupsPage.selectPublicPage();
    userGroupsPage.inputGroupName("Test group");
    userGroupsPage.confirmGroupCreation();
    //todo проверка созданой группы
  }


}
