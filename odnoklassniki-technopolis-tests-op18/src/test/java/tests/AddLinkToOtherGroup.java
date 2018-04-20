package tests;

import core.helpers.GroupHelper;
import core.pages.SessionPage;
import model.TestBot;
import org.junit.Before;
import org.junit.Test;

public class AddLinkToOtherGroup extends TestBase {

  private static final String PAGE_NAME = "Тестовая страница";
  private static final TestBot USER_ACCOUNT_ADMIN = new TestBot("QA18testbot78", "QA18testbot");

  @Before
  public void preconditions() {
    new SessionPage(driver).loginAuth(USER_ACCOUNT_ADMIN);
    GroupHelper.createPublicPage(driver, PAGE_NAME);
  }

  @Test
  public void testCase() throws Exception {
  //  String firstGroup =
  }
  }
