class MyThread implements Runnable {

    public void run() {
        int ll = Main.getLast();
        while (!Main.finished()) {
            int rl = ll + Main.getStep();
            outer:
            for (int n = ll; n < rl; n++) {
                for(int i = 2; i < n / 2 + 1; i++)
                    if(n % i == 0)
                        continue outer;

                Main.push(n);
            }
            ll = Main.getLast();
        }
    }
}