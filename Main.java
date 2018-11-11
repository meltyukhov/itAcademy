import java.util.*;

public class Main {
    private static int last;
    private static int step;
    private static int rl;
    private static boolean finished = false;
    private static ArrayList<Integer> list = new ArrayList<Integer>();

    public static int getLast() {
        last += step;
        if (last > rl) {
            finished = true;
            return 0;
        }
        else
            return last - step;
    }

    public static boolean finished() {
        return finished;
    }

    public static int getStep() {
        return step;
    }

    public static void push(int n) {
        list.add(n);
    }

    public static void main(String[] args) {
        //last = 0;
        if (args.length < 3) {
            System.out.println("This program requires 3 arguments");
            return;
        }
        try {
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            int n = Integer.parseInt(args[2]);

            if (a < 2)
                a = 2;

            if (a > b) {
                System.out.println("Interval incorrect");
                return;
            }

            if (n < 1) {
                System.out.println("We need at least one thread");
                return;
            }

            rl = b;
            last = a;
            int diff = b - a;

            step = (diff > n * 4) ? (diff * 4 / n) : 1;

            Thread[] threads = new Thread[n];

            for (int i = 0; i < n; i++)
                threads[i] = new Thread(new MyThread());
            for (Thread thread : threads)
                thread.start();

			for (int i = 0; i < n; i++) {
                try {
                    threads[i].join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Collections.sort(list);
			System.out.println();

            for (int prime : list)
                System.out.print(prime + " ");

        }
        catch (NumberFormatException exc) {
            System.out.println("This program requires numbers");
        }
    }
}