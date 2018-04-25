package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupSettingsPage;
import core.groups.settings.rights.MenuOfGoodsPopUp;
import core.groups.settings.rights.RightsSettingsPage;
import core.login.SessionPage;
import core.user.UserGroupsPage;
import model.BotProvider;
import model.TestBot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddMenuGoodsToGroupTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
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
    groupSettingsPage.clickRights();
    new RightsSettingsPage(driver).selectDisplayMenuOfGoods(MenuOfGoodsPopUp.SHOW);
    groupSettingsPage.confirmSettings();
    groupSettingsPage.toGroupMainPage();

    //Проверочки
    Assert.assertTrue(groupMainPage
        .isGroupGoodsPresent()); //проверяем, что пункт "Товары" отображается в верхнем меню
    groupMainPage.clickToGroupGoods();
  }

  @After
  public void clearBot(){
    UserGroupsPage userGroupsPage = goToUserGroupsPage();
    userGroupsPage.deleteAllCreatedGroups();
  }
}
