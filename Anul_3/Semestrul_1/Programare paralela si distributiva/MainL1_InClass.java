package app;

import java.util.Arrays;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainL1_InClass {
    public static int[] vector(int n, int upper_bound) {
        int[] v = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            v[i] = rand.nextInt(upper_bound);
        }
        return v;
    }
    public static void main(String[] args) {
        int n = 10000000;
        int[] a = vector(n, 1000);
        int[] b = vector(n, 1000);
        int[] c = new int[n];
        int[] cc = new int[n];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            c[i] = (int)Math.sqrt(a[i]*a[i]*a[i]*a[i]*a[i] + b[i]*b[i]*b[i]*b[i]*b[i]);
        }
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));
//        System.out.println(Arrays.toString(c));

        int p = 4;
        MyThread[] threads = new MyThread[p];

        int start = 0, end = n / p;
        int rest = n % p;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            if (rest > 0) {
                end ++;
                rest --;
            }
            threads[i] = new MyThread(a, b, cc, start, end);
            threads[i].start();

            start = end;
            end = start + n / p;
        }
        endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(Arrays.equals(c, cc));

        int[] ccc = new int[n];
        Thread2[] threads2 = new Thread2[p];

        startTime = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads2[i] = new Thread2(a, b, ccc, i, n, p);
            threads2[i].start();
        }
        endTime = System.currentTimeMillis();

        for (int i = 0; i < p; i++) {
            try {
                threads2[i].join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(Arrays.equals(c, ccc));
        System.out.println(endTime - startTime);

    }

    static class Thread2 extends Thread {
        int[] a,b,c;
        int id, n, p;

        public Thread2(int[] a, int[] b, int[] c, int id, int n, int p) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.id = id;
            this.n = n;
            this.p = p;
        }

        public void run() {
            for (int i = id; i < n; i += p) {
                c[i] = (int)Math.sqrt(a[i]*a[i]*a[i]*a[i]*a[i] + b[i]*b[i]*b[i]*b[i]*b[i]);
            }
        }
    }

    static class MyThread extends Thread {
        int id;
        int[] a, b, c;
        int start, end;
        MyThread(int[] a, int[] b, int[] c, int start, int end) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.start = start;
            this.end = end;

        }
        public void run() {
            for (int i = start; i < end; i++) {
                c[i] = (int)Math.sqrt(a[i]*a[i]*a[i]*a[i]*a[i] + b[i]*b[i]*b[i]*b[i]*b[i]);
            }
        }
    }
}