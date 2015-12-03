package ru.bpal.launcher.digits;

/**
 * Created by serg on 03.12.15.
 */
public class Fibonacci {

    public static final int MAX_NUMBER = 10;

    public static void main(String[] args) {
        int lo=1;
        int hi = 1;
        int counter = 1;
        System.out.println("" + counter + ": " + lo);
        counter++;
        while (counter  <= MAX_NUMBER) {
            System.out.println("" + counter + ": " + hi);
            hi= lo + hi; // bew value
            lo = hi - lo; // new lo = old hi

            counter++;
        }
    }
}
