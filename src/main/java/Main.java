import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {

    private static long MIN = 10_000;
    private static long MAX = 50_000;

    static Boolean isPrime(long l) {
        if (l < 2) {
            return false;
        } else if (l == 2) {
            return true;
        } else {
            return LongStream.range(2, l).allMatch((i) -> l % i != 0);
        }
    }

    public static void main(String[] args) {
        LocalDateTime timePoint1;
        LocalDateTime timePoint2;
        timePoint1 = LocalDateTime.now();

        List<Long> ll = LongStream.range(MIN, MAX)
                .filter((long i) -> isPrime(i))
                .boxed()
                .collect(Collectors.toList());
        timePoint2 = LocalDateTime.now();

        long noParaTime = Duration.between(timePoint1, timePoint2).toMillis();
        System.out.println("No parallel: " + noParaTime);

        timePoint1 = LocalDateTime.now();
        List<Long> ll2 = LongStream.range(MIN, MAX)
                .parallel()
                .filter((long i) -> isPrime(i))
                .boxed()
                .collect(Collectors.toList());
        timePoint2 = LocalDateTime.now();

        long paraTime = Duration.between(timePoint1, timePoint2).toMillis();
        System.out.println("parallel: " + noParaTime);

        double speedup = 1 / ((double)paraTime / noParaTime);
        System.out.println("speedup: " + speedup);
        System.out.println("cores: " + Runtime.getRuntime().availableProcessors());

        // be sure to use results to avoid any kind of optimisation
        long s1 = ll.stream()
                .reduce((aLong, aLong2) -> aLong + aLong2)
                .get();
        long s2 = ll2.stream()
                .reduce((aLong, aLong2) -> aLong + aLong2)
                .get();
        System.out.println(s1 + s2);
    }
}
