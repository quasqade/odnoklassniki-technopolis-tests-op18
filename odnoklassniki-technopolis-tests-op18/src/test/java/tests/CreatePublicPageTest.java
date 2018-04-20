package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import core.pages.groups.main.GroupMainPage;
import core.pages.groups.settings.ModeratorsSettingsPage;
import core.pages.groups.settings.main.GroupSettingsPage;
import core.pages.groups.settings.main.GroupType;
import core.pages.groups.settings.rights.RightsSettingsPage;
import core.pages.groups.settings.rights.ShowPhotosInFeedOption;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

/**
 * Проверяется создание публичной страницы
 */
public class CreatePublicPageTest extends TestBase {

  private static final String PAGE_NAME = "Тестовая страница";
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot78", "QA18testbot");

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
  }


  @Test
  public void testCase() throws Exception {
    GroupMainPage gmp = GroupHelper.createPublicPage(driver, PAGE_NAME);
    Assert.assertEquals(gmp.getGroupName(), PAGE_NAME);
    Assert.assertEquals(gmp.getGroupCategory(), "Страница, Компьютер и интернет");
    Assert.assertEquals(gmp.getUserStatus(), "Администратор");
    GroupSettingsPage gsp = gmp.openGroupSettings();
    Assert.assertEquals(gsp.getNameFromField(), PAGE_NAME);
    Assert.assertEquals(gsp.getType(), GroupType.PAGE);
    RightsSettingsPage rsp = gsp.clickRights();
    Assert.assertFalse(rsp.canMembersSuggestTopics(),
        "Участники не должны предлагать темы по умолчанию");
    Assert.assertEquals(rsp.whatPhotosAreShownInFeed(), ShowPhotosInFeedOption.OF_ONLY_MODERATORS,
        "По умолчанию должны показываться только фото от администрации");
    ModeratorsSettingsPage msp = rsp.clickModerators();
    Assert.assertTrue(msp.areModeratorsHidden());
    Assert.assertTrue(msp.isOwnerHidden());

  }
}
