package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupSettingsPage;
import core.groups.settings.rights.MenuOfGoodsPopUp;
import core.groups.settings.rights.RightsSettingsPage;
import core.login.SessionPage;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddMenuGoodsToGroupTest extends TestBase {
  private static final String GROUP_NAME = getRandomId();
  private static final TestBot USER_ACCOUNT = new TestBot("QA18testbot80", "QA18testbot");
  private static String groupId;

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
    goToGroup(groupId);

    //Проверочки
    Assert.assertTrue(groupMainPage.isGroupGoodsPresent()); //проверяем, что пункт "Товары" отображается в верхнем меню
    groupMainPage.clickToGroupGoods();
  }

}
