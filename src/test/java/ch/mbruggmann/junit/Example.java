package ch.mbruggmann.junit;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static junit.framework.Assert.assertTrue;

public class Example {

  @Test
  public void testName() {
    assertTrue(true);
  }

  @Test
  public void testFailing() {
    assertTrue(false);
  }

  public static void main(String... args) {
    final JUnitCore junit = new JUnitCore();
    junit.addListener(new JUnitTeamcityReporter(System.out));
    final Result result = junit.run(Example.class);
    System.exit(result.wasSuccessful() ? 0 : 1);
  }

}
