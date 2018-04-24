package tests;

import core.groups.GroupHelper;
import core.groups.links.AddLinkOverlay;
import core.groups.links.GroupLinksPage;
import core.groups.main.GroupMainPage;
import core.login.SessionPage;
import model.BotProvider;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

public class AddLinkToOtherGroup extends TestBase {

  private static final String PAGE_NAME_1 = getRandomId();
  private static final String PAGE_NAME_2 = getRandomId();
  private static String firstGroupId, secondGroupId;
  private final TestBot USER_ACCOUNT_ADMIN = BotProvider.requestBot(this);

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    GroupMainPage gmp = GroupHelper.createPublicPage(driver, PAGE_NAME_1);
    firstGroupId = gmp.getGroupId();
    gmp.returnToUserPage();
    gmp = GroupHelper.createPublicPage(driver, PAGE_NAME_2);
    secondGroupId = gmp.getGroupId();
    goToGroup(firstGroupId);
  }

  @Test
  public void testCase() throws Exception {
    GroupMainPage gmp = new GroupMainPage(driver);
    int linkCount = gmp.getLinkCountFromOtherSections();
    GroupLinksPage glp = gmp.goToLinks();
    AddLinkOverlay alo = glp.addLink();
    Assert.assertFalse(alo.getSelectionStateByGroupId(secondGroupId),
        "Вторая группа уже добавлена как ссылка");
    alo.selectByGroupId(secondGroupId);
    Assert.assertTrue(alo.getSelectionStateByGroupId(secondGroupId),
        "Не удалось выбрать вторую группу для добавления");
    glp = alo.confirmAndClose();
    Assert.assertTrue(glp.isGroupCardPresent(secondGroupId),
        "Добавленная ссылка отсутствует в списке");
    gmp = glp.returnToGroupPage();
    Assert.assertEquals(gmp.getLinkCountFromOtherSections(), linkCount + 1,
        "Счетчик ссылок в разделе Ещё не увеличивается");
    gmp = gmp.collectAndGoToFriendlyGroup(secondGroupId);
    Assert.assertEquals(gmp.getGroupId(), secondGroupId, "Переход по ссылке ведет не в ту группу");


  }
}
