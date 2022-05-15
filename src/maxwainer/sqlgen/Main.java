package maxwainer.sqlgen;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

  private static final Random RANDOM = ThreadLocalRandom.current();

  public static void main(String[] args) throws IOException {
    final var chars = new Character[]{'A', 'B', 'C', 'D'};
    final var prices = new Integer[]{1000, 2000, 3000, 4000, 5000};
    final var types = new String[]{"Плацкат", "Купе", "СВ"};

    final var path = Paths.get("local.txt");

    try (final var writer = new PrintWriter(
        Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
      int c = 0;
      for (int i = 1; i <= 200; i++) {
        for (int j = 1; j <= 50; j++) {
          c++;

          if (c == 1000) {

            writer.println("INSERT INTO sitting ([index], related_carriage_id, sit_type, price)\n"
                + "VALUES ");

            c = 0;
          }

          final var line = String.format("('%s%s', %s, N'%s', %s)%s", randomFromArray(chars), j, i,
              randomFromArray(types), randomFromArray(prices), c == 999 ? ';' : ',');

          writer.println(line);
        }
      }

      writer.flush();
    }
  }

  private static <T> T randomFromArray(final T[] array) {
    final var size = array.length;

    return array[RANDOM.nextInt(0, size - 1)];
  }
}
