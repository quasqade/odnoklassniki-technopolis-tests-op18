package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupSettingsPage;
import core.login.SessionPage;
import model.BotProvider;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется изменение названия созданной группы
 */

public class ChangeGroupNameTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static final String newName = "New name";
  private static String groupId;
  private final TestBot USER_ACCOUNT = BotProvider.requestBot(this);

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT);
    groupId = GroupHelper.createPublicPage(driver, GROUP_NAME).getGroupId();
  }

  @Test
  public void testCase() throws Exception {
    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.openGroupSettings();
    GroupSettingsPage groupSettingsPage = new GroupSettingsPage(driver);
    groupSettingsPage.changeName(newName);
    groupSettingsPage.confirmSettings();
    groupSettingsPage.refreshWebPage();

    //проверяем, что название соответствует новоуму названию
    Assert.assertEquals(newName, groupSettingsPage.getNameFromField());
    Assert.assertEquals(newName, groupSettingsPage.getNameFromBackLink());
    goToGroup(groupId);
    Assert.assertEquals(newName, groupMainPage.getGroupName());

  }


}
