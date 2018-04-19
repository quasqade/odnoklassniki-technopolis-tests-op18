package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.groups.main.GroupMainPage;
import core.pages.groups.settings.main.GroupSettingsPage;
import model.TestBot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется изменение названия созданной группы
 */

public class ChangeGroupNameTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static final TestBot USER_ACCOUNT = new TestBot("QA18testbot80", "QA18testbot");

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT);
    GroupHelper.createPublicPage(driver, GROUP_NAME);
  }

  @Test
  public void testCase() throws Exception {
    //test
    GroupMainPage groupMainPage = new GroupMainPage(driver);
    groupMainPage.openGroupSettings();
    GroupSettingsPage groupSettingsPage = new GroupSettingsPage(driver);
    String newName = "New name";
    groupSettingsPage.changeName(newName);
    groupSettingsPage.confirmSettings();
    groupSettingsPage.refreshWebPage();

    //проверяем, что название соответствует новоуму названию
    Assert.assertEquals(newName, groupSettingsPage.getNameFromField());
    Assert.assertEquals(newName, groupSettingsPage.getNameFromBackLink());

    groupSettingsPage.toGroupMainPage();
    //Нужно скролить до позиции элемента
    Assert.assertEquals(newName, groupMainPage.getGroupName());

  }


}
