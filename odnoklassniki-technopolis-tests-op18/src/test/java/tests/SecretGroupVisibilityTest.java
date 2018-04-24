package tests;

import core.groups.GroupHelper;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupPrivacy;
import core.groups.settings.main.GroupSettingsPage;
import core.login.SessionPage;
import core.user.UserMainPage;
import model.BotProvider;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверяется присоединение к закрытой группе путем заявки
 */
public class SecretGroupVisibilityTest extends TestBase {

  private static final String GROUP_NAME = getRandomId();
  private static String groupId;
  private final TestBot USER_ACCOUNT_MEMBER = BotProvider.requestBot(this);
  private final TestBot USER_ACCOUNT_ADMIN = BotProvider.requestBot(this);

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    groupId = GroupHelper.createPublicPage(driver, GROUP_NAME).getGroupId();
  }


  @Test
  public void testCase() throws Exception {

    //test
    GroupMainPage gmp = new GroupMainPage(driver);
    GroupSettingsPage gsp = gmp.openGroupSettings();
    gsp.changeType();
    gsp.changePrivacy(GroupPrivacy.BY_MEMBER_INVITATION);
    gsp.confirmSettings();

    //авторизация от второго пользователя
    init();
    new SessionPage(driver).loginAuth(USER_ACCOUNT_MEMBER);
    new UserMainPage(driver); //чтобы дождаться загрузки страницы
    goToGroup(groupId).and404Opened();
  }
}
