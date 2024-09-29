import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoggerAvailableTest {
  @Test
  public void loggerAvailable() {
    assertDoesNotThrow(() -> {
      Class.forName("org.apache.logging.log4j.Logger");
    }, "Could not find Log4j 2 Logger class");
  }
}