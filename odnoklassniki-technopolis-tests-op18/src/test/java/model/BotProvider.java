package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;

/**
 * Предоставляет ботов из набора тестам
 */
public class BotProvider {

  private static List<TestBot> bots = Arrays.asList(new TestBot("QA18testbot83", "QA18testbot"),
      new TestBot("QA18testbot84", "QA18testbot"));
  private static Map<Object, List<TestBot>> leases = new HashMap<>(); //список выделенных аренд ботов разным объектам

  public static TestBot requestBot(Object source) {
    if (!leases.containsKey(source)) {
      List<TestBot> objectLeases = new ArrayList<>();
      objectLeases.add(bots.get(0));
      leases.put(source, objectLeases);
      return bots.get(0);
    }
    List<TestBot> objectLeases = leases.get(source);
    for (TestBot bot : bots
        ) {
      if (!objectLeases.contains(bot)) {
        objectLeases.add(bot);
        return bot;
      }
    }
    Assert.fail("У провайдера не хватает ботов");
    return null;
  }
}
