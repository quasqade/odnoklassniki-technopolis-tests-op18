package tests;

import core.pages.UserGroupsPage;
import core.pages.SessionPage;
import core.pages.UserMainPage;
import model.TestBot;
import org.junit.Test;

import static org.junit.Assert.fail;

public class SecondTest extends TestBase {

    @Test
    public void testCase() throws Exception {

        new SessionPage(driver).loginAuth(new TestBot("79219213098", "59pckgkC259C"));
        new UserMainPage(driver).openGroupsByToolbar();
        UserGroupsPage userGroupsPage = new UserGroupsPage(driver);
        userGroupsPage.createGroupByToolbar();
        userGroupsPage.selectPublicPage();
        userGroupsPage.inputGroupName("Group");
        userGroupsPage.confirmGroupCreation();
        //todo проверка созданой группы
    }


}
