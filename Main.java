import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static int last;
    private static int step;
    private static int max;
    private static boolean finished = false;


    private static int getLast() {
        last += step;
        if (last > max) {
            finished = true;
            return 0;
        }
        else
            return last - step;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("This program requires 3 arguments");
            return;
        }
        try {
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            int threads = Integer.parseInt(args[2]);

            if (a < 2)
                a = 2;

            if (a > b) {
                System.out.println("Interval incorrect");
                return;
            }

            if (threads < 1) {
                System.out.println("We need at least one thread");
                return;
            }

            max = b;
            last = a;
            int diff = b - a;

            step = (diff > threads * 4) ? (diff / threads / 4) : 1;

            ExecutorService executorService = Executors.newFixedThreadPool(threads);
            ArrayList<Integer> list = new ArrayList<Integer>();

            Runnable task = () -> {
                int ll = getLast();
                while (!finished) {
                    int rl = ll + step;

                    outer:
                    for (int n = ll; n < rl; n++) {
                        for(int i = 2; i <= n / 2; i++)
                            if(n % i == 0)
                                continue outer;
                        list.add(n);
                    }
                    ll = getLast();
                }
            };

            for (int i = 0; i < threads; i++)
                executorService.execute(task);

            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }

            Collections.sort(list);
            for (int prime : list)
                System.out.print(prime + " ");

        }
        catch (NumberFormatException exc) {
            System.out.println("This program requires numbers");
        }
    }
}