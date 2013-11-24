package ch.mbruggmann.junit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class JUnitTeamcityReporterTest {

  public static class SomeTest {

    @Test
    public void testMethod() {
      assertTrue(true);
    }

    @Test
    public void testMethodFailing() {
      assertTrue(false);
    }

    @Test
    @Ignore
    public void testIgnored() {
    }

  }

  @Test
  public void testReport() throws UnsupportedEncodingException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    final JUnitTeamcityReporter reporter = new JUnitTeamcityReporter(new PrintStream(os));
    final JUnitCore junit = new JUnitCore();
    junit.addListener(reporter);
    junit.run(SomeTest.class);

    final String expected =
        "##teamcity[testSuiteStarted name='ch.mbruggmann.junit.JUnitTeamcityReporterTest$SomeTest']\n" +
        "##teamcity[testStarted name='testMethod' captureStandardOutput='true']\n" +
        "##teamcity[testFinished name='testMethod']\n" +
        "##teamcity[testStarted name='testMethodFailing' captureStandardOutput='true']\n" +
        "##teamcity[testFailed name='testMethodFailing' message='failed' details='']\n" +
        "##teamcity[testFinished name='testMethodFailing']\n" +
        "##teamcity[testIgnored name='testIgnored' message='']\n" +
        "##teamcity[testSuiteFinished name='ch.mbruggmann.junit.JUnitTeamcityReporterTest$SomeTest']\n";
    final List<String> expectedLines = Arrays.asList(expected.split("\n"));

    final String actual = os.toString("UTF-8");
    final List<String> actualLines = Arrays.asList(actual.split("\n"));

    // contains all expected lines
    assertTrue(actualLines.size() >= expectedLines.size());
    for (String line: expectedLines) {
      assertTrue(line, actualLines.contains(line));
    }

    // suite name (first and last line)
    assertEquals(expectedLines.get(0), actualLines.get(0));
    assertEquals(expectedLines.get(expectedLines.size()-1), actualLines.get(actualLines.size()-1));
  }

}
